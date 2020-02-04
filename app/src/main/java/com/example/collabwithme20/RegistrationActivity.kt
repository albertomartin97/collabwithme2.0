package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    companion object {
        private val TAG = "RegistrationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationBtn.setOnClickListener {
            performRegistration()
            saveNewProfile()
        }

    }

    private fun performRegistration(){

        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        //val name = name_registration_input.text.toString()
        //val surname = surname_registration_input.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        if(password.length < 6){
            Toast.makeText(this, "Passwords must have at least 7 characters", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email is: " + email)
        Log.d("MainActivity", "Paswword: $password")


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(!it.isSuccessful) {
                Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
            else {
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid} ")
                Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
            }
        }


        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)

    }

    private fun saveNewProfile() {


        val name = nameInput.text.toString()

        val db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "name" to name
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }



    }
}
