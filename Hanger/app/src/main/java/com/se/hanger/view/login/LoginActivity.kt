package com.se.hanger.view.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.se.hanger.R
import com.se.hanger.databinding.ActivityLoginBinding
import com.se.hanger.setStatusBarTransparent
import com.se.hanger.view.base.BaseActivity

class LoginActivity(
    override val layoutResId: Int = R.layout.activity_login,
) :
    BaseActivity<ActivityLoginBinding, LoginViewModel>(), View.OnClickListener {

    override val viewModel: LoginViewModel
        get() = ViewModelProvider(this)[LoginViewModel::class.java]

    override fun initActivity() {
        setStatusBarTransparent()

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