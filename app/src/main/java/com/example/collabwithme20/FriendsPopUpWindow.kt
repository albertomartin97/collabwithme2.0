package com.example.collabwithme20

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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


class FriendsPopUpWindow : AppCompatActivity(){
    companion object {
        private const val TAG = "FriendsPopUpWindow"

    }

    private var imageName = ""
    private var userName = ""
    private var userUID = ""
    private var city = ""
    private var email = ""
    private var description = ""

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
        description = bundle?.getString("description", "Description") ?: ""

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
        setDescription(userUID)

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

        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                when {
                    document.getString("music_production") == "true" -> {
                        musicProductionPopUpFriends.setBackgroundResource(R.drawable.style17)
                        musicProductionPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("singing") == "true" -> {
                        singingPopUpFriends.setBackgroundResource(R.drawable.style17)
                        singingPopUpFriends.setTextColor(Color.WHITE)
                    }

                }
                when {
                    document.getString("video_production") == "true" -> {
                        videoProductionPopUpFriends.setBackgroundResource(R.drawable.style17)
                        videoProductionPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("graphic_design") == "true" -> {
                        graphicDesignerPopUpFriends.setBackgroundResource(R.drawable.style17)
                        graphicDesignerPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("clothing_design") == "true" -> {
                        clothingDesignPopUpFriends.setBackgroundResource(R.drawable.style17)
                        clothingDesignPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("sound_engineer") == "true" -> {
                        soundEngineerPopUpFriends.setBackgroundResource(R.drawable.style17)
                        soundEngineerPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("instrumentalist") == "true" -> {
                        instrumentalistPopUpFriends.setBackgroundResource(R.drawable.style17)
                        instrumentalistPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("rapping") == "true" -> {
                        rapperPopUpFriends.setBackgroundResource(R.drawable.style17)
                        rapperPopUpFriends.setTextColor(Color.WHITE)
                    }
                }
            }


        }
    }

    private fun setDescription(friendUid: String){

        val docRef = db.collection("users").document(friendUid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                descriptionFriend.text = document.getString("description")

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

    }
    private fun removeFriendFromDB(friendUid: String){

        val currentUserFriends = db.collection("users").
            document(uid).collection("friends")
            .document(friendUid)

        val receiverFriendsRef  = db.collection("users").document(friendUid)
            .collection("friends").document(uid)

        currentUserFriends.delete()
        receiverFriendsRef.delete()
    }

}
