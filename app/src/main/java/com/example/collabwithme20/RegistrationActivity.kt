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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationBtn.setOnClickListener {
            performRegistration()
        }

    }

    private fun performRegistration() {

        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val firstName = nameInput.text.toString()
        val lastName = lastNameInput.text.toString()

        var userID : String
        val user = hashMapOf(
            "first_name" to firstName,
            "last_name" to lastName,
            "email" to email
        )

        val db = FirebaseFirestore.getInstance()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Passwords must have at least 7 characters", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email is: " + email)
        Log.d("MainActivity", "Paswword: $password")

        //Auth registration
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) {
                Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            } else {
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid} ")

                //Saving profile into Database
                userID = FirebaseAuth.getInstance().currentUser?.uid ?: String()

                val docRef =  db . collection ("users").document(userID)
                docRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "User profile created for $userID")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
            }
        }


        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)

    }

}