package com.example.loginscreen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.example.loginscreen.home.HomeActivity
import com.example.loginscreen.Home.User
import com.example.loginscreen.databinding.ActivitySignUpPhoneNumberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpPhoneNumber : BaseActivity() {

    private lateinit var binding: ActivitySignUpPhoneNumberBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.BackBt.setOnClickListener {
            startActivity(Intent(this,SignInPhoneNumber::class.java))
        }

        binding.SignUpBt.setOnClickListener { registerUser() }

    }

    private fun registerUser()
    {
        val name = binding.NameET.text.toString()
        val email = binding.EmailET.text.toString()
        val password = binding.PassET.text.toString()

        val user = User(name, email, password)

        if (validateForm(name,email,password)){
            showProgressBar()


            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    val uid = task.result.user?.uid
                    if (uid != null){
                        databaseReference.child(uid).setValue(user)
                    }

                    showToast(this,"Account Created Successfully")
                    hideProgressBar()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                else{
                   showToast(this,"Account not created, Try Again")
                    hideProgressBar()
                }
            }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                binding.nameLayout.error = "Enter name"
                false
            }
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding.emailLayout.error = "Enter valid email address"
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