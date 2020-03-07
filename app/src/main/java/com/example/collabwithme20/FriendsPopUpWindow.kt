package com.example.collabwithme20

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friends_pop_up_window.*
import kotlinx.android.synthetic.main.activity_pop_up_window.*
import kotlinx.android.synthetic.main.activity_pop_up_window.cityPopUp
import kotlinx.android.synthetic.main.activity_pop_up_window.closeBtn
import kotlinx.android.synthetic.main.activity_pop_up_window.clothingDesignPopUp
import kotlinx.android.synthetic.main.activity_pop_up_window.graphicDesignerPopUp
import kotlinx.android.synthetic.main.activity_pop_up_window.musicProductionPopUp
import kotlinx.android.synthetic.main.activity_pop_up_window.pop_up_window_background
import kotlinx.android.synthetic.main.activity_pop_up_window.profileImageView
import kotlinx.android.synthetic.main.activity_pop_up_window.rapperPopUp
import kotlinx.android.synthetic.main.activity_pop_up_window.singingPopUp
import kotlinx.android.synthetic.main.activity_pop_up_window.usernameTextView
import kotlinx.android.synthetic.main.activity_pop_up_window.videoProductionPopUp
import kotlinx.android.synthetic.main.friends_row.*

class FriendsPopUpWindow : AppCompatActivity(){
    companion object {
        private const val TAG = "PopUpWindow"

    }

    private var imageName = ""
    private var userName = ""
    private var userUID = ""
    private var city = ""
    private var email = ""

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_pop_up_window)

        closeBtn.setOnClickListener {
            onBackPressed()
        }


        //Get data from FriendsActivity
        val bundle = intent.extras
        imageName = bundle?.getString("profileImage", "Image") ?: ""
        userName = bundle?.getString("username", "Name") ?: ""
        userUID = bundle?.getString("uid", "User UID") ?: ""
        city = bundle?.getString("city", "City") ?: ""
        email = bundle?.getString("email", "Email") ?: ""

        val emailWithStr = "Email : $email"

        //Set data
        if(imageName == "") {
            Glide.with(profileImageView).load(R.drawable.default_profile_pic).transform(CircleCrop())
                .into(profileImageView)
        }else{
            Glide.with(profileImageView).load(imageName).transform(CircleCrop())
                .into(profileImageView)
        }
        usernameTextView.text = userName
        cityPopUp.text = city
        emailTextViewFriend.text = emailWithStr
        showSkills(userUID)

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 300 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            pop_up_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()


        removeFriendPopUp.setOnClickListener {
            Toast.makeText(this, "Friend removed", Toast.LENGTH_SHORT).show()
            removeFriendFromDB(userUID)
            onBackPressed()
        }

    }


    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 300 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            pop_up_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }


        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

    private fun showSkills(uid: String) {

        val musicProductionDoc = db.collection("users").document(uid)
            .collection("skills").document("music_production")

        val singingDoc = db.collection("users").document(uid)
            .collection("skills").document("singing")

        val rapperDoc = db.collection("users").document(uid)
            .collection("skills").document("rapping")

        val videoProductionDoc = db.collection("users").document(uid)
            .collection("skills").document("video_production")

        val graphicDesignerDoc = db.collection("users").document(uid)
            .collection("skills").document("graphic_design")

        val clothingDesignDoc = db.collection("users").document(uid)
            .collection("skills").document("clothing_design")

        val soundEngineerDoc = db.collection("users").document(uid)
            .collection("skills").document("sound_engineer")

        val instrumentalistDoc = db.collection("users").document(uid)
            .collection("skills").document("instrumentalist")


        musicProductionDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        musicProductionPopUp.setBackgroundResource(R.drawable.style17)
                        musicProductionPopUp.setTextColor(Color.WHITE)
                    }
                }

            }

        }

        singingDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        singingPopUp.setBackgroundResource(R.drawable.style17)
                        singingPopUp.setTextColor(Color.WHITE)
                    }
                }

            }

        }

        rapperDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        rapperPopUp.setBackgroundResource(R.drawable.style17)
                        rapperPopUp.setTextColor(Color.WHITE)
                    }
                }
            }
        }

        videoProductionDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        videoProductionPopUp.setBackgroundResource(R.drawable.style17)
                        videoProductionPopUp.setTextColor(Color.WHITE)
                    }
                }
            }
        }

        graphicDesignerDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        graphicDesignerPopUp.setBackgroundResource(R.drawable.style17)
                        graphicDesignerPopUp.setTextColor(Color.WHITE)
                    }
                }
            }
        }

        clothingDesignDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        clothingDesignPopUp.setBackgroundResource(R.drawable.style17)
                        clothingDesignPopUp.setTextColor(Color.WHITE)
                    }
                }
            }
        }

        soundEngineerDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        soundEngineerPopUpFriends.setBackgroundResource(R.drawable.style17)
                        soundEngineerPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
            }
        }

        instrumentalistDoc.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("skill") == "true" -> {
                        instrumentalistPopUpFriends.setBackgroundResource(R.drawable.style17)
                        instrumentalistPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
            }
        }

    }

    private fun removeFriendFromDB(friendUid: String){

        db.collection("users").document(uid).collection("friends")
            .document(friendUid).delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }

}
