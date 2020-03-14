package com.example.collabwithme20

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
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
import com.google.firebase.firestore.SetOptions
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
import kotlinx.android.synthetic.main.activity_profile.*


class PopUpWindow : AppCompatActivity() {
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
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window)

        closeBtn.setOnClickListener {
            onBackPressed()
        }

        //Get data from FindPeopleActivity
        val bundle = intent.extras
        imageName = bundle?.getString("profileImage", "Image") ?: ""
        userName = bundle?.getString("username", "Name") ?: ""
        userUID = bundle?.getString("uid", "User UID") ?: ""
        city = bundle?.getString("city", "City") ?: ""
        email = bundle?.getString("email", "Email") ?: ""

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
        showSkills(userUID)

        //Add friend to DB
        addFriendPopUp.setOnClickListener{
            //addFriendToDB(userUID, userName, imageName, city, email)
            checkIfUsersAreFriends(userUID, userName, imageName, city, email)
            onBackPressed()

        }

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 300 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            pop_up_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()


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
                        musicProductionPopUp.setBackgroundResource(R.drawable.style17)
                        musicProductionPopUp.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("singing") == "true" -> {
                        singingPopUp.setBackgroundResource(R.drawable.style17)
                        singingPopUp.setTextColor(Color.WHITE)
                    }

                }
                when {
                    document.getString("video_production") == "true" -> {
                        videoProductionPopUp.setBackgroundResource(R.drawable.style17)
                        videoProductionPopUp.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("graphic_design") == "true" -> {
                        graphicDesignerPopUp.setBackgroundResource(R.drawable.style17)
                        graphicDesignerPopUp.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("clothing_design") == "true" -> {
                        clothingDesignPopUp.setBackgroundResource(R.drawable.style17)
                        clothingDesignPopUp.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("sound_engineer") == "true" -> {
                        soundEngineerPopUp.setBackgroundResource(R.drawable.style17)
                        soundEngineerPopUp.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("instrumentalist") == "true" -> {
                        instrumentalistPopUp.setBackgroundResource(R.drawable.style17)
                        instrumentalistPopUp.setTextColor(Color.WHITE)
                    }
                }
                when {
                    document.getString("rapping") == "true" -> {
                        rapperPopUp.setBackgroundResource(R.drawable.style17)
                        rapperPopUp.setTextColor(Color.WHITE)
                    }
                }
            }


        }
    }


    private fun checkIfUsersAreFriends(friendUID: String, friendName: String, profileImage: String,
                                       friendCity: String, friendEmail: String){

        val docRef = db.collection("users").document(uid).
            collection("friends").document(friendUID)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val name = document.getString("fullName")

                if(name == friendName){
                    Toast.makeText(this, "You are already friends" , Toast.LENGTH_SHORT).show()
                }else{
                    sendFriendRequest(friendUID, friendName, profileImage, friendCity, friendEmail)
                }

            } else {
                Log.d("doesn't exist", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

    }

    private fun sendFriendRequest(friendUID: String, friendName: String, profileImage: String, friendCity: String, friendEmail: String){

        val currentUserRequestRef = db.collection("users").document(uid)
            .collection("friend_requests").document(friendUID)

        val currentUserFriendsRef = db.collection("users").document(uid)
            .collection("friends").document(friendUID)

        val friendUser = hashMapOf(
            "uid" to friendUID,
            "fullName" to friendName,
            "profile_image" to profileImage,
            "city" to friendCity,
            "email" to friendEmail,
            "chat" to "false"
        )


        currentUserRequestRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val trueOrFalse = document.getString("request_state")

                //If friend has sent you a request add to friends
                if (trueOrFalse == "true"){
                    addCurrentUserToFriendsDB(friendUID)

                    currentUserFriendsRef.set(friendUser).addOnSuccessListener {
                        Log.d(TAG, "Friend created for $uid")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                    Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show()
                }else{

                    sendRequestToFriend(friendUID)

                    Toast.makeText(this, "Friend request sent", Toast.LENGTH_SHORT).show()
                }

            } else {
                Log.d("doesn't exist", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }




    }

    private fun sendRequestToFriend(friendUID: String){

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String

        val receiverRequestRef = db.collection("users").document(friendUID)
            .collection("friend_requests").document(uid)

        val currentUserInfoRef = db.collection("users").document(uid)

        currentUserInfoRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()
                val name  = "$firstName $lastName"
                profileImage = document.getString("profile_image").toString()
                city = document.getString("city").toString()
                email = document.getString("email").toString()

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to name,
                    "profile_image" to profileImage,
                    "city" to city,
                    "email" to email,
                    "request_state" to "true"
                )

                receiverRequestRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }




    }

    private fun addCurrentUserToFriendsDB(friendUID: String){
        val receiverFriendsRef  = db.collection("users").document(friendUID)
            .collection("friends").document(uid)

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String



        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()
                val name  = "$firstName $lastName"
                profileImage = document.getString("profile_image").toString()
                city = document.getString("city").toString()
                email = document.getString("email").toString()

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to name,
                    "profile_image" to profileImage,
                    "city" to city,
                    "email" to email,
                    "chat" to "false"
                )

                receiverFriendsRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                deleteFriendRequests(friendUID)
            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }


    }

    private fun deleteFriendRequests(friendUID: String){

        val currentUserRequestRef = db.collection("users").document(uid)
            .collection("friend_requests").document(friendUID)

        val receiverRequestRef = db.collection("users").document(friendUID)
            .collection("friend_requests").document(uid)

        currentUserRequestRef.delete()
        receiverRequestRef.delete()
    }

}
