package com.example.loginscreen.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.loginscreen.R
import com.example.loginscreen.SignInActivity
import com.example.loginscreen.databinding.ActivitySettingBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager


class SettingActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}