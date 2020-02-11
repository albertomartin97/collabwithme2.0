package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    companion object {
        private val TAG = "ProfileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

        getUserData() //Get user data from DB


        musicProductionBtn.setOnClickListener {

            updateSkills("music_production")

        }

        singerBtn.setOnClickListener {

            updateSkills("singing")
        }

        rapperBtn.setOnClickListener {

            updateSkills("rapping")

        }

        videoProductionBtn.setOnClickListener {

            updateSkills("video_production")

        }

        graphicDesignerBtn.setOnClickListener {

            updateSkills("graphic_design")

        }

        clothingDesignBtn.setOnClickListener {

            updateSkills("clothing_design")

        }

        othersBtn.setOnClickListener {

            updateSkills("others")

        }
    }

    private fun getUserData(){
        val userName = usernameTextView
        val firstName = firstNameTextView
        val lastName = lastNameTextView
        val email = emailTextView

        val db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()
        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                userName.text = document.getString("first_name")
                firstName.text = document.getString("first_name")
                lastName.text = document.getString("last_name")
                email.text = document.getString("email")
            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }
    }

    private fun updateSkills(skill : String){

            val db = FirebaseFirestore.getInstance()
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()


            val documentRef = db.collection("users").document(uid).
                collection("skills").document(skill)

            val skillBool = "true"
            val user = hashMapOf(
                "skill" to skillBool
            )

            documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                Log.d(TAG, "User profile created for $uid")
            }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }




    }
}
