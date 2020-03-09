package com.example.collabwithme20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity() {
    companion object{
        val TAG = "Login Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            performLogin()
        }


    }

    private fun performLogin(){
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()


        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email is: $email")
        Log.d("MainActivity", "Paswword: $password")

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(!it.isSuccessful){
                Toast.makeText(this, "You have entered an invalid username or password", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener}
            else  {
                val intentGoToHomeScreenActivity = Intent(this, HomeScreenActivity::class.java)
                Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
                startActivity(intentGoToHomeScreenActivity)
            }


        }



    }


}
