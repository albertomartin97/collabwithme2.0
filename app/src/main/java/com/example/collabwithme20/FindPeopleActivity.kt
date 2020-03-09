package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collabwithme20.Adapters.SearchUsersAdapter
import com.example.collabwithme20.Models.UserModel
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_choose_city.*
import kotlinx.android.synthetic.main.activity_find_people.*
import kotlinx.android.synthetic.main.activity_profile.backBtn


class FindPeopleActivity : AppCompatActivity(), SearchUsersAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "FindPeopleActivity"

    }
    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_people)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        createRecyclerView("All")

        allCitiesBtn.setOnClickListener {
            createRecyclerView("All")
            allCitiesBtn.setBackgroundResource(R.drawable.style22)
        }

        searchLondonBtn.setOnClickListener {
            createRecyclerView("London")
        }
        searchBristolBtn.setOnClickListener {
            createRecyclerView("Bristol")
        }
        searchBirminghamBtn.setOnClickListener {
            createRecyclerView("Birmingham")
        }
        searchCardiffBtn.setOnClickListener {
            createRecyclerView("Cardiff")
        }
        searchDublinBtn.setOnClickListener {
            createRecyclerView("Dublin")
        }
        searchEdinburghBtn.setOnClickListener {
            createRecyclerView("Edinburgh")
        }
        searchManchesterBtn.setOnClickListener {
            createRecyclerView("Manchester")
        }
        searchSwanseaBtn.setOnClickListener {
            createRecyclerView("Swansea")
        }


    }


    private fun createRecyclerView(city: String){

        val query = db.collection("users")


        val array = FirestoreArray(query, ClassSnapshotParser(UserModel::class.java))



        val options = FirestoreRecyclerOptions.Builder<UserModel>()
          .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.search_recyclerView)


        val adapter: FirestoreRecyclerAdapter<*, *> =
            SearchUsersAdapter(
                uid,
                array,
                options,
                this, city
            )


        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }

    override fun onUserClick(user: UserModel, position: Int, buttonName: String){

        if (buttonName == "addFriendBtn"){
            Toast.makeText(this, "Friend added" , Toast.LENGTH_SHORT).show()
            val fullName = user.first_name + " " + user.last_name
            addFriendToDB(user.uid, fullName, user.profile_image, user.city, user.email, user.description)
        }else if(buttonName == "showUserProfile"){
            val fullName = user.first_name + " " + user.last_name
            val intent = Intent(this, PopUpWindow::class.java)
            intent.putExtra("profileImage", user.profile_image)
            intent.putExtra("username", fullName)
            intent.putExtra("uid", user.uid)
            intent.putExtra("city", user.city)
            intent.putExtra("email", user.email)
            startActivity(intent)

        }

    }

    private fun addFriendToDB(friendUID: String, friendName: String, profileImage: String,
                              friendCity: String, friendEmail: String, friendDescription: String){

        val docRef = db.collection("users").document(uid)
            .collection("friends").document(friendUID)

        val user = hashMapOf(
            "uid" to friendUID,
            "fullName" to friendName,
            "profile_image" to profileImage,
            "city" to friendCity,
            "email" to friendEmail,
            "description" to friendDescription
        )

        docRef.set(user, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "Friend created for $uid")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }



    }

    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }


}


