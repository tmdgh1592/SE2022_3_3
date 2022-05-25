package com.se.hanger.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.se.hanger.R
import com.se.hanger.databinding.ActivityLoginBinding
import com.se.hanger.setStatusBarTransparent

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setStatusBarTransparent()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        with(binding) {
            registerBtn.setOnClickListener(this@LoginActivity)
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
        }
    }
}