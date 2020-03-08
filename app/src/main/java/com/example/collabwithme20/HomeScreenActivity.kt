package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        //Intent to go to homescreen of the phone
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
