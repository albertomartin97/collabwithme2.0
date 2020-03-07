package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity() {
    companion object {
        private val TAG = "HomeScreenActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        profileBtn.setOnClickListener{
            val intentGoToProfileActivity = Intent(this, ProfileActivity::class.java)
            startActivity(intentGoToProfileActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        friendsBtn.setOnClickListener{
            val intentGoToFriendsActivity = Intent(this, FriendsActivity::class.java)
            startActivity(intentGoToFriendsActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        findPeopleBtn.setOnClickListener{
            val intentGoToFindPeopleActivity = Intent(this, FindPeopleActivity::class.java)
            startActivity(intentGoToFindPeopleActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        messagesBtn.setOnClickListener{
            val intentGoToMessagesActivity = Intent(this, MessagesActivity::class.java)
            startActivity(intentGoToMessagesActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        settingsBtn.setOnClickListener{
            val intentGoToSettingsActivity = Intent(this, SettingsActivity::class.java)
            startActivity(intentGoToSettingsActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }


    }

    override fun onBackPressed() {
        // do something
    }
}
