package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_screen.*



class HomeScreenActivity : AppCompatActivity() {

    //Initialize Firebase
    private val db = FirebaseFirestore.getInstance()

    //Get current user id
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //Set notification icon if friend requests exist
        updateNotificationIcon()

        profileBtn.setOnClickListener{
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        friendsBtn.setOnClickListener{
            val intent = Intent(this, FriendsActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        findPeopleBtn.setOnClickListener{
            val intent = Intent(this, FindPeopleActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        messagesBtn.setOnClickListener{
            val intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        settingsBtn.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }


    }

    /**
     * Checks number of friend requests in db and updates notification icon
     */
    private fun updateNotificationIcon(){
        val notification = notificationHomeScreen

        val docRef = db.collection("users").document(uid)
            .collection("friend_requests")


        docRef.get().addOnSuccessListener { document ->
            if (document.size() > 0)  {
                notification.setBackgroundResource(R.drawable.circle_32)
            }
            if (document.size() == 1){
                notificationNumberTextView.text = "1"
            }
            if (document.size() == 2){
                notificationNumberTextView.text = "2"
            }
            if (document.size() == 3){
                notificationNumberTextView.text = "3"
            }
            if (document.size() == 4){
                notificationNumberTextView.text = "4"
            }
            if (document.size() == 5){
                notificationNumberTextView.text = "5"
            }
            if (document.size() == 6){
                notificationNumberTextView.text = "6"
            }
            if (document.size() == 7){
                notificationNumberTextView.text = "7"
            }
            if (document.size() == 8){
                notificationNumberTextView.text = "8"
            }
            if (document.size() == 9){
                notificationNumberTextView.text = "9"
            }
            if (document.size() > 9){
                notificationNumberTextView.text = "9+"
                notificationNumberTextView.x = 1050F
            }

        }

    }

    /**
     * Leaves the app when back button is pressed
     */
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
