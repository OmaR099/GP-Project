package com.example.loginscreen.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.example.loginscreen.BaseActivity
import com.example.loginscreen.R
import com.example.loginscreen.SignInActivity
import com.example.loginscreen.chatBot.ChatBot
import com.example.loginscreen.databinding.ActivityProfileBinding
import com.example.loginscreen.profile.Feedback
import com.example.loginscreen.profile.NotificationSettingFragment
import com.example.loginscreen.profile.Settings
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var fbImg: ImageView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        if (AccessToken.getCurrentAccessToken() != null) {
            // Logged in with Facebook
            fetchDataFromFacebook()
        } else {
            // Logged in with Firebase
            fetchDataFromFirebase()
        }

        fbImg = findViewById(R.id.profile_img)

        // Functions Calls
        logout()
        backBtn()
        notification()
        feedback()
        uploadProfilePic()
        makeStatusBarTransparent()
        chatBot()
        settings()
    }

    private fun makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    //get data from facebook if Logged in with Facebook
    private fun fetchDataFromFacebook() {
        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken()
        ) { obj, _ ->
            val name = obj?.getString("name")
            val email = obj?.getString("email")
            // Update UI with Facebook data
            binding.profileName.text = name
            binding.profileEmail.text = email
        }
        val parameters = Bundle()

        parameters.putString("fields", "id,name,link,picture.type(large),email")

        request.parameters = parameters
        request.executeAsync()
    }

    //get data from Firebase if Logged in with Firebase
    private fun fetchDataFromFirebase() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            databaseReference.child(uid).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                if (user != null) {
                    // Update UI with Firebase data
                    binding.profileName.text = user.name
                    binding.profileEmail.text = user.email
                } else {
                    // Handle the case where user data is not found
                }
            }.addOnFailureListener {
                // Handle database errors
            }
        }
    }

    // Back Button Function
    private fun backBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun feedback() {
        binding.feedbackBtn.setOnClickListener {
            startActivity(Intent(this, Feedback::class.java))
        }
    }

    //Logout Function
    private fun logout() {
        binding.logoutBtn.setOnClickListener {
            LoginManager.getInstance().logOut()
            finish()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    // Uploading Image from gallery
    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    private fun uploadProfilePic() {
        binding.profileImg.setOnClickListener { pickImageGallery() }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {

            binding.profileImg.setImageURI(data?.data)
            imageUri = data?.data!!
            storageReference =
                FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
            storageReference.putFile(imageUri).addOnSuccessListener {

                showToast(this, "Profile Picture Updated")

            }.addOnFailureListener {

                showToast(this, "Failed to Upload Image!! ")
            }
        }
    }

    private fun notification() {
        binding.notificationBtn.setOnClickListener {
            startActivity(Intent(this, NotificationSettingFragment::class.java)) }
    }

    private fun chatBot() {
        binding.chatBotCv.setOnClickListener {
            startActivity(Intent(this, ChatBot::class.java)) }
    }

    private fun settings() {
        binding.settingBtn.setOnClickListener {
            startActivity(Intent(this, Settings::class.java)) }
    }
}