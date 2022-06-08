package com.se.hanger.view.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.se.hanger.R
import com.se.hanger.data.db.ClothDatabase
import com.se.hanger.data.db.MemberDao
import com.se.hanger.data.model.Member
import com.se.hanger.databinding.ActivityLoginBinding
import com.se.hanger.setStatusBarTransparent
import com.se.hanger.view.base.BaseActivity
import com.se.hanger.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity(
    override val layoutResId: Int = R.layout.activity_login,
) :
    BaseActivity<ActivityLoginBinding, LoginViewModel>(), View.OnClickListener {

    override val viewModel: LoginViewModel
        get() = ViewModelProvider(this)[LoginViewModel::class.java]

    private lateinit var memberDao: MemberDao

    override fun initActivity() {
        setStatusBarTransparent()
        memberDao = ClothDatabase.getInstance(this)!!.userDao()
        with(binding) {
            registerBtn.setOnClickListener(this@LoginActivity)
            loginCardView.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.register_btn -> {
                startActivity(
                    Intent(
                        this,
                        RegisterActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            }
            R.id.login_card_view -> {
                val username = binding.idEditText.text
                val password = binding.pwEditText.text
                if (username.isNotBlank() and password.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val member: Member? =
                            memberDao.findByUsernameAndPw(username.toString(), password.toString())

                        member?.let {
                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        } ?: showSnackBar("아이디와 패스워드를 다시 확인해주세요!")
                    }
                } else {
                    showSnackBar("아이디와 패스워드를 입력해주세요!")
                }
            }
        }
    }
}