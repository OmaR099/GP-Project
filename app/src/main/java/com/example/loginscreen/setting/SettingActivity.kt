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
    private lateinit var fbImg: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbImg = findViewById(R.id.fbImg)

        val accessToken = AccessToken.getCurrentAccessToken()

        val request = GraphRequest.newMeRequest(accessToken) { obj, _ ->

            val id = obj?.getString("id")
            val fullName = obj?.getString("name")
            val email = obj?.getString("email")
            val profileUrl = obj?.getJSONObject("picture")
                ?.getJSONObject("data")?.getString("url")

            binding.fbName.text = fullName
            binding.fbEmail.text = email
            binding.fbId.text = id

            Glide.with(applicationContext).load(profileUrl).into(fbImg)
        }

        val parameters = Bundle()

        parameters.putString("fields","id,name,link,picture.type(large),email")

        request.parameters = parameters
        request.executeAsync()

        binding.fbLogOut.setOnClickListener {

            LoginManager.getInstance().logOut()

            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
    }
}