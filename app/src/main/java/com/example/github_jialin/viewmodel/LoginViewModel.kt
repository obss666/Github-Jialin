package com.example.github_jialin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.github_jialin.data.Repository

class LoginViewModel : ViewModel() {
    private var mAccount: String = ""

    fun updateAccount(account: String) {
        this.mAccount = account
    }

    fun getAccount() = mAccount

    private var mPassword: String = ""

    fun updatePassword(password: String) {
        this.mPassword = password
    }

    fun getPassword() = mPassword



    fun getAutoLoginStatus () = Repository.getAutoLoginStatus()

    fun getRememberPasswordStatus() = Repository.getRememberPasswordStatus()

    fun getRememberAccount() = Repository.getAccount()

    fun getRememberPassword() = Repository.getPassword()

    fun saveLoginInfo(account: String, password: String, autoLogin: Boolean, rememberPassword: Boolean) =
        Repository.saveLoginInfo(account, password, autoLogin, rememberPassword)

}