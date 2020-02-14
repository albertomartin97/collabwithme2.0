package com.example.collabwithme20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_profile.*
import androidx.core.view.get


class ProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProfileActivity"

    }


    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

        getUserData() //Get user data from DB

        saveCitySelection() //Save selected city

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

        val documentRef = db.collection("users").document(uid).
            collection("skills").document(skill)

        documentRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                if (document.getString("skill") == "true"){

                    val skillTrue = "false"
                    val user = hashMapOf(
                        "skill" to skillTrue
                    )

                    documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                        Log.d(TAG, "User profile created for $uid")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                } else if(document.getString("skill") == "false"){

                    val skillTrue = "true"
                    val user = hashMapOf(
                        "skill" to skillTrue
                    )

                    documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                        Log.d(TAG, "User profile created for $uid")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                } else if(document.get(skill) == null){

                    val skillTrue = "true"
                    val user = hashMapOf(
                        "skill" to skillTrue
                    )

                    documentRef.set(user).addOnSuccessListener {
                        Log.d(TAG, "User profile created for $uid")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }

            } else {
                Log.d("doesn't exist", "No such document")

            }
        }

    }

    private fun saveCitySelection() {


        val city = findViewById<Spinner>(R.id.citiesSpinner)
        val cities = arrayOf("London", "Birmingham", "Bristol", "Cardiff", "Swansea")

        city.adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, cities)

        city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val docRef = db.collection("users").document(uid)
                val result = cities.get(index = p2)
                val user = hashMapOf(
                    "city" to result
                )


                docRef.set(user, SetOptions.merge()).addOnSuccessListener {
                    Log.d(TAG, "User profile created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }

    }
}
