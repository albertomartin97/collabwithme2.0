package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationBtn.setOnClickListener {
            performRegistration()
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

        if(password.length < 7){
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

        lateinit var currentUserDb: DatabaseReference

        val name = nameInput.text.toString()
        //val surname = surname_registration_input.text.toString()



        val userId  = FirebaseAuth.getInstance().currentUser?.uid

        currentUserDb = FirebaseDatabase.getInstance().reference.child("users").child(userId.toString())

        val newUser:HashMap<String,String> = HashMap()
        newUser.put("name",name)
        //newUser.put("surname",surname)

        currentUserDb.push().updateChildren(newUser as Map<String, Any>)





    }
}
