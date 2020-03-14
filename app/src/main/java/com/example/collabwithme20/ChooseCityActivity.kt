package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_choose_city.*
import kotlinx.android.synthetic.main.activity_profile.*

class ChooseCityActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChooseCityActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)

        londonBtn.setOnClickListener {
            //Save city selection
            changeCity("London")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }

        bristolbtn.setOnClickListener {
            //Save city selection
            changeCity("Bristol")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }
        manchesterBtn.setOnClickListener {
            //Save city selection
            changeCity("Manchester")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }
        birminghamBtn.setOnClickListener {
            //Save city selection
            changeCity("Birmingham")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }
        swanseaBtn.setOnClickListener {
            //Save city selection
            changeCity("Swansea")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }
        cardiffBtn.setOnClickListener {
            //Save city selection
            changeCity("Cardiff")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }
        edinburghBtn.setOnClickListener {
            //Save city selection
            changeCity("Edinburgh")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

        }
        dublinBtn.setOnClickListener {
            //Save city selection
            changeCity("Dublin")

            //Check caller activity
            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfilePictureActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show()
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }else if(caller == "ProfileActivity"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                }
            }, 1000)

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

    override fun onBackPressed() {
        // do something
    }
}
