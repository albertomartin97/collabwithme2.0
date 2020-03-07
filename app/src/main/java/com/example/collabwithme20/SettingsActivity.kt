package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.backBtn

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

        logoutTextView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "You have logged out", Toast.LENGTH_SHORT).show()
            val intentGoToMainActivity = Intent(this, MainActivity::class.java)
            intent.putExtra("caller", "SettingsActivity")
            startActivity(intentGoToMainActivity)
        }
    }

    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}
