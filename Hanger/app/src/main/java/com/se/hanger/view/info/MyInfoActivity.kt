package com.se.hanger.view.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.se.hanger.R
import com.se.hanger.data.db.ClothDatabase
import com.se.hanger.databinding.ActivityMyInfoBinding
import com.se.hanger.view.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyInfoActivity : AppCompatActivity() {
    private lateinit var clothDatabase: ClothDatabase
    private lateinit var binding: ActivityMyInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clothDatabase = ClothDatabase.getInstance(this)!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info)

        CoroutineScope(Dispatchers.IO).launch {
            val uid = getSharedPreferences("hanger", Context.MODE_PRIVATE).getInt("uid", -1)
            if (uid != -1) {
                binding.member = clothDatabase.userDao().findById(uid)
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.doneBtn.setOnClickListener {
            val username = binding.usernameTv.text.trim().toString()
            val password = binding.passwordEt.text?.trim().toString()

            CoroutineScope(Dispatchers.IO).launch {
                val isDuplicatedUsername =
                    clothDatabase.userDao().findByUsername(username = username) != null

                if (isDuplicatedUsername && (binding.member?.username != username)) { // 유저네임이 중복되는 경우 (단, 본인 닉네임은 예외)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MyInfoActivity, "동일한 닉네임이 존재합니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    // 업데이트할 Member 객체
                    val updatedMember = binding.member!!.apply {
                        this.username = username
                        this.password = password
                    }

                    clothDatabase.userDao().update(updatedMember)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MyInfoActivity, "정보 수정이 완료되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                }
            }
        }

        binding.unregisterBtn.setOnClickListener {
            AlertDialog.Builder(this).setTitle("회원탈퇴").setMessage("회원탈퇴 하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        clothDatabase.userDao().delete(binding.member!!)
                        withContext(Dispatchers.Main) {
                            finishAffinity() // 이전 부모 액티비티(Main Activity)까지 종료
                            startActivity(Intent(this@MyInfoActivity, LoginActivity::class.java))
                        }
                    }
                }.setNegativeButton("취소") { _, _ ->

                }.create().show()
        }


        binding.logoutBtn.setOnClickListener {
            AlertDialog.Builder(this).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    getSharedPreferences("hanger", Context.MODE_PRIVATE).edit().putInt("uid", -1)
                        .commit()
                    finishAffinity() // 이전 부모 액티비티(Main Activity)까지 종료
                    startActivity(Intent(this, LoginActivity::class.java))
                }.setNegativeButton("취소") { _, _ ->

                }.create().show()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}