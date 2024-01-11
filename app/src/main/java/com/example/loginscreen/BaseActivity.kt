package com.example.loginscreen

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    lateinit var pb:Dialog

    fun showProgressBar(){
        pb = Dialog(this)
        pb.setContentView(R.layout.progress_bar)
        pb.setCancelable(false)
        pb.show()
    }

    fun hideProgressBar(){
        pb.hide()
    }

    fun showToast(activity: Activity, msg: String){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }

}