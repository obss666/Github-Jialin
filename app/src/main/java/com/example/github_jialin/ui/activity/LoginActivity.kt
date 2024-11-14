package com.example.github_jialin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.utils.showToast
import com.example.github_jialin.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

//    private lateinit var startForResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var mLoginButton: Button
    private lateinit var mAccountEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mAutoLoginCheckBox: CheckBox
    private lateinit var mRememberPasswordCheckBox: CheckBox

    private fun checkLogin() : Boolean {
//        mViewModel.getPassword() == "123456"
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mLoginButton = findViewById(R.id.loginButton)
        mAccountEditText = findViewById(R.id.accountEditText)
        mPasswordEditText = findViewById(R.id.passwordEditText)
        mAutoLoginCheckBox = findViewById(R.id.autoLoginCheckBox)
        mRememberPasswordCheckBox = findViewById(R.id.rememberPassCheckBox)

        mAccountEditText.addTextChangedListener {
            mViewModel.updateAccount(it.toString())
        }

        mPasswordEditText.addTextChangedListener {
            mViewModel.updatePassword(it.toString())
        }

        mLoginButton.setOnClickListener {
//            val getOAuth2Url = ClientApplication.OAUTH2_URL + "?client_id=" + ClientApplication.clientID + "&state=" + UUID.randomUUID().toString();
//            LogUtil.v("getOAuth2Url",getOAuth2Url)
//            val intent = Intent(this, LoginWebActivity::class.java);
//            intent.putExtra("url", getOAuth2Url);
//            startForResultLauncher.launch(intent)
            if(checkLogin()) {
                mViewModel.saveLoginInfo(mAccountEditText.text.toString(), mPasswordEditText.text.toString(),
                    mAutoLoginCheckBox.isChecked, mRememberPasswordCheckBox.isChecked)
                ClientApplication.USERNAME = mAccountEditText.text.toString()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                "用户不存在".showToast(this)
            }
        }

        if(mViewModel.getRememberPasswordStatus()) {
            mRememberPasswordCheckBox.isChecked = true
            mAccountEditText.setText(mViewModel.getRememberAccount())
            mPasswordEditText.setText(mViewModel.getRememberPassword())
        }

        if(mViewModel.getAutoLoginStatus()) {
            mAutoLoginCheckBox.isChecked = true
            if(!intent.getBooleanExtra("isSignOut", false)) {
                mLoginButton.performClick()
            }
        }

//        startForResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val data = result.data
//                if (data != null) {
//                    val intent = Intent(this, MainActivity::class.java)
//                    LogUtil.v("code", data.toString())
//                    startActivity(intent)
//                }
//            }
//        }
    }
}