package com.example.github_jialin.data.dao

import android.content.Context
import com.example.github_jialin.ClientApplication

object CacheDao {
    private fun sharedPreferences() =
       ClientApplication.context.getSharedPreferences("Github_jialin", Context.MODE_PRIVATE)

    fun getAutoLoginStatus() = sharedPreferences().getBoolean("auto_login", false)

    fun getRememberPasswordStatus() = sharedPreferences().getBoolean("remember_password", false)

    fun getAccount() = sharedPreferences().getString("account", "")

    fun getPassword() = sharedPreferences().getString("password", "")

    fun saveLoginInfo(account: String, password: String, autoLogin: Boolean, rememberPassword: Boolean) {
        val editor = sharedPreferences().edit()
        editor.putString("account", account)
        editor.putString("password", password)
        editor.putBoolean("auto_login", autoLogin)
        editor.putBoolean("remember_password", rememberPassword)
        editor.apply()
    }
}