package com.example.github_jialin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class ClientApplication : Application() {
    companion object {
        const val OAUTH2_URL = "https://github.com/login/oauth/authorize"

        const val clientID = "Ov23liGTMImNgAHekSAh"

        const val clientSecret = "9487bd110d13fbdb5c04ca413250d257223ef758"

        const val TOKEN : String = "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"

        var USERNAME : String = "dogdie233"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}



