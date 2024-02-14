package com.example.loginscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.loginscreen.home.HomeActivity
import com.example.loginscreen.databinding.ActivitySignInBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser


class SignInActivity : BaseActivity() {

    private lateinit var binding:ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        //Facebook Sign In

        val accessToken = AccessToken.getCurrentAccessToken()

        if (accessToken != null && !accessToken.isExpired)
        {
            startActivity(Intent(this, HomeActivity::class.java)) //this will changed to home screen
            finish()

        }

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult>{
                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }

                override fun onSuccess(result: LoginResult) {
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java)) //this will changed to home screen
                    finish()
                    
                }

            })

        binding.fbLoginButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile,email"))

        }


        //Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.SignInPhoneNumberBt.setOnClickListener {
            startActivity(Intent(this,SignInPhoneNumber::class.java))
        }

        binding.GoogleBt.setOnClickListener { signInWithGoogle() }
    }

    //Google SignIn method and configuration
    private fun signInWithGoogle()
    {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result->
        if (result.resultCode == Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }

    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful)
        {
            val account: GoogleSignInAccount? = task.result
            if (account != null)
                updateUI(account)
        }
        else
        {
            showToast(this, "SignIn Failed, Try again later")
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        showProgressBar()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful)
            {
                startActivity(Intent(this, HomeActivity::class.java)) //this will changed to home screen
                finish()
                hideProgressBar()
            }
            else
            {
                showToast(this, "Can't Login currently. Try again later")
                hideProgressBar()
            }
        }
    }

    /* override fun onStart() {
         super.onStart()

         if (firebaseAuth.currentUser != null){
             startActivity(Intent(this,HomeActivity::class.java)) // this will be changed to home Activity
             finish()
         }
     }*/


    // facebook LoginIn

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    updateUIFb(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUIFb(null)
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateUIFb(user: FirebaseUser?) {
        showProgressBar()
        if (user != null) {
            // Signed in successfully
//            val welcomeText = "Welcome, ${user.displayName}!"
//            val profileImageUrl = user.photoUrl


            // Update UI elements
//            binding.welcomeTextView.text = welcomeText
//            binding.profileImageView.loadProfileImage(profileImageUrl)
//            binding.signInButton.visibility = View.GONE
//            binding.logOutButton.visibility = View.VISIBLE
            startActivity(Intent(this, HomeActivity::class.java)) //this will changed to home screen
            finish()
            hideProgressBar()
        } else {
            // Signed out or sign-in failed
//            binding.welcomeTextView.text = "Please sign in"
//            binding.profileImageView.setImageResource(R.drawable.default_profile_image)
//            binding.signInButton.visibility = View.VISIBLE
//            binding.SignInPhoneNumberBt.visibility = View.GONE
            hideProgressBar()
        }
    }

    companion object {
        private const val TAG = "FacebookLogin"
    }

}