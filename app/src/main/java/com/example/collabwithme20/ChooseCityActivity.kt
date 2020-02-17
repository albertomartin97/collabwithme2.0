package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_choose_city.*
import kotlinx.android.synthetic.main.activity_profile.*

class ChooseCityActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProfileActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)

        londonBtn.setOnClickListener {
            changeCity("London")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        bristolbtn.setOnClickListener {
            changeCity("Bristol")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        manchesterBtn.setOnClickListener {
            changeCity("Manchester")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        birminghamBtn.setOnClickListener {
            changeCity("Birmingham")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        swanseaBtn.setOnClickListener {
            changeCity("Swansea")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        cardiffBtn.setOnClickListener {
            changeCity("Cardiff")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        edinburghBtn.setOnClickListener {
            changeCity("Edinburgh")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        dublinBtn.setOnClickListener {
            changeCity("Dublin")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun changeCity(citySelection: String) {
        val db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

        val docRef = db.collection("users").document(uid)
        val user = hashMapOf(
            "city" to citySelection
        )

        docRef.set(user, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}
