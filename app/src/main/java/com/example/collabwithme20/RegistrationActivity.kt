package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    companion object {
        private val TAG = "RegistrationActivity"
    }

    //Initialize Firebase
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationBtn.setOnClickListener {
            performRegistration()

        }

    }

    /**
     * Reads user's input and creates an account in firebase authentication as well as saving the
     * data into db
     */
    private fun performRegistration() {

        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val firstName = nameInput.text.toString()
        val lastName = lastNameInput.text.toString()
        var userID : String

        //Check edit texts are not empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        //Check password has more than 6 characters
        if (password.length < 6) {
            Toast.makeText(this, "Passwords must have at least 7 characters", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email is: $email")
        Log.d("MainActivity", "Paswword: $password")

        //Auth registration and save into DB
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) {
                Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            } else {
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid} ")

                //Get user uid
                userID = FirebaseAuth.getInstance().currentUser?.uid ?: String()

                val user = hashMapOf(
                    "first_name" to firstName,
                    "last_name" to lastName,
                    "email" to email,
                    "uid" to userID
                )

                //Save user to database
                val docRef =  db . collection ("users").document(userID)
                docRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "User profile created for $userID")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            }

            //Go to Profile picture
            val intent = Intent(this, ProfilePictureActivity::class.java)
            intent.putExtra("caller", "RegistrationActivity")
            startActivity(intent)


        }

    }

}