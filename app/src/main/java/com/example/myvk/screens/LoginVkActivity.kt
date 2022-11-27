package com.example.myvk.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.data.repository.CommonRepository
import com.example.data.sources.preference.MainSharedPreferences
import com.example.myvk.R
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class LoginVkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_vk)
        supportActionBar?.hide()

        val commonRepository = CommonRepository(MainSharedPreferences(this))

        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnReturn = findViewById<Button>(R.id.btnReturn)
        val txtErr = findViewById<TextView>(R.id.txtErr)
        txtErr.text = ""

        val authLauncher = VK.login(this) {
            when (it) {
                is VKAuthenticationResult.Success -> {
                    val token = it.token.accessToken
                    commonRepository.saveVkAuthToken(it.token.accessToken)

                    Log.e("NEBUR", "SUC, token: $token")
                    finish()
                }
                is VKAuthenticationResult.Failed -> {
                    txtErr.text = it.exception.message.toString()

                    Log.e("NEBUR", "ERR")
                }
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
        btnReturn.setOnClickListener {
            authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }

        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
    }
}