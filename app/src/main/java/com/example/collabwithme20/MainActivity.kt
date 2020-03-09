package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import android.R.anim
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    companion object{
        val TAG = "MainActivity"
    }


    public override fun onResume() {
        super.onResume()
        videoView.start()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkSignedInUser()

        loginTextView.setOnClickListener {
            val intentGoToRegistrationActivity = Intent(this, RegistrationActivity::class.java)
            startActivity(intentGoToRegistrationActivity)

        }

        loginBtn.setOnClickListener {
            val intentGoToLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentGoToLoginActivity)

        }


        val videoView = findViewById<VideoView>(R.id.videoView)
        val path = "android.resource://" + packageName + "/" + R.raw.djstudio_vertical_zoom
        videoView?.setVideoURI(Uri.parse(path))
        videoView.start()

    }

    private fun checkSignedInUser(){

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) { // User is signed in

            //Get user's name
            val db = FirebaseFirestore.getInstance()
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

            val docRef = db.collection("users").document(uid)

            docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val username = document.getString("first_name").toString()

                Toast.makeText(this, "Welcome back $username", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

            Handler().postDelayed({
            //Start homescreen activity
            val intent = Intent(this, HomeScreenActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, anim.fade_in, anim.fade_out)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent, options.toBundle())
            }, 270)
        }else { // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out")

        }

    }



    override fun onBackPressed() {
            finish()
    }


}
