package com.example.collabwithme20

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
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
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("caller", "SettingsActivity")
            startActivity(intent)
        }

        deleteAccountBtn.setOnClickListener {
            val intent = Intent(this,  DeleteAccountPopUpWindow::class.java)
            startActivity(intent)
        }


    }

    /**
     * Go to home screen when back button is pressed
     */
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
    
}
