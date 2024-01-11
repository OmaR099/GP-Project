package com.example.loginscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.loginscreen.Home.HomeActivity
import com.example.loginscreen.databinding.ActivitySignInPhoneNumberBinding
import com.google.firebase.auth.FirebaseAuth

class SignInPhoneNumber : BaseActivity() {

    private lateinit var binding: ActivitySignInPhoneNumberBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.Create.setOnClickListener {
            startActivity(Intent(this, SignUpPhoneNumber::class.java))

        }
            binding.SignInBt.setOnClickListener { signInUser() }
    }

    private fun signInUser(){

        val email = binding.EmailET.text.toString()
        val password = binding.PassET.text.toString()

        if (validateForm(email,password)){
            showProgressBar()
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    startActivity(Intent(this, HomeActivity::class.java)) //this will be Home screen
                    finish()
                    hideProgressBar()
                }else{
                    showToast(this,"Wrong Email or Password")
                    hideProgressBar()
                }
            }
        }

    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding.emailLayout.error = "Enter vaild email address"
                false
            }
            TextUtils.isEmpty(password)->{
                binding.passwordLayout.error = "Enter password"
                false
            }
            else -> {true}
        }
    }
}
