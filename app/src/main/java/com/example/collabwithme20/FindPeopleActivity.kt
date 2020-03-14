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
import kotlinx.android.synthetic.main.activity_find_people.*
import kotlinx.android.synthetic.main.activity_profile.*
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


        createRecyclerView("All", "All")

        allCitiesBtn.setOnClickListener {
            createRecyclerView("All", "All")
        }

        allSkillsBtn.setOnClickListener {
            createRecyclerView("All", "All")
        }


        //Create recyclerview for each city category
        searchLondonBtn.setOnClickListener {
            createRecyclerView("London", "none")
        }

        searchBristolBtn.setOnClickListener {
            createRecyclerView("Bristol", "All")
        }
        searchBirminghamBtn.setOnClickListener {
            createRecyclerView("Birmingham", "All")
        }
        searchCardiffBtn.setOnClickListener {
            createRecyclerView("Cardiff", "All")
        }
        searchDublinBtn.setOnClickListener {
            createRecyclerView("Dublin", "All")
        }
        searchEdinburghBtn.setOnClickListener {
            createRecyclerView("Edinburgh", "All")
        }
        searchManchesterBtn.setOnClickListener {
            createRecyclerView("Manchester", "All")
        }
        searchSwanseaBtn.setOnClickListener {
            createRecyclerView("Swansea", "All")
        }


    }


    private fun createRecyclerView(city: String, skill: String){

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
                this, city, skill
            )


        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }

    override fun onUserClick(user: UserModel, position: Int, buttonName: String){

        //Check button being clicked in recyclerView
        if (buttonName == "addFriendBtn"){
            val fullName = user.first_name + " " + user.last_name
            checkIfUsersAreFriends(user.uid, fullName, user.profile_image, user.city, user.email)
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

    private fun checkIfUsersAreFriends(friendUID: String, friendName: String, profileImage: String,
                                       friendCity: String, friendEmail: String){

        val docRef = db.collection("users").document(uid).
            collection("friends").document(friendUID)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val name = document.getString("fullName")

                if(name == friendName){
                    Toast.makeText(this, "You are already friends" , Toast.LENGTH_SHORT).show()
                }else{
                    checkRequest(friendUID, friendName, profileImage, friendCity, friendEmail)
                }


            } else {
                Log.d("doesn't exist", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

    }

    private fun checkRequest(friendUID: String, friendName: String, profileImage: String, friendCity: String, friendEmail: String){

        val currentUserRequestRef = db.collection("users").document(uid)
            .collection("friend_requests").document(friendUID)

        val currentUserFriendsRef = db.collection("users").document(uid)
            .collection("friends").document(friendUID)

        val friendUser = hashMapOf(
            "uid" to friendUID,
            "fullName" to friendName,
            "profile_image" to profileImage,
            "city" to friendCity,
            "email" to friendEmail,
            "chat" to "false"
        )


        currentUserRequestRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val trueOrFalse = document.getString("request_state")

                //If friend has sent you a request add to friends
                if (trueOrFalse == "true"){
                    addCurrentUserToFriendsDB(friendUID)

                    currentUserFriendsRef.set(friendUser).addOnSuccessListener {
                        Log.d(TAG, "Friend created for $uid")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                    Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show()
                }else{

                    //If current user does not have a request from that person send him a request
                    sendRequestToFriend(friendUID)

                    Toast.makeText(this, "Friend request sent", Toast.LENGTH_SHORT).show()
                }

            } else {
                Log.d("doesn't exist", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }




    }

    private fun sendRequestToFriend(friendUID: String){

        val receiverRequestRef = db.collection("users").document(friendUID)
            .collection("friend_requests").document(uid)

        val currentUserInfoRef = db.collection("users").document(uid)

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String


        currentUserInfoRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()
                val name  = "$firstName $lastName"
                profileImage = document.getString("profile_image").toString()
                city = document.getString("city").toString()
                email = document.getString("email").toString()

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to name,
                    "profile_image" to profileImage,
                    "city" to city,
                    "email" to email,
                    "request_state" to "true"
                )

                //Save request to receiver requests list
                receiverRequestRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }




    }

    private fun addCurrentUserToFriendsDB(friendUID: String){
        val receiverFriendsRef  = db.collection("users").document(friendUID)
            .collection("friends").document(uid)

        val docRef = db.collection("users").document(uid)

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()
                val name  = "$firstName $lastName"
                profileImage = document.getString("profile_image").toString()
                city = document.getString("city").toString()
                email = document.getString("email").toString()

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to name,
                    "profile_image" to profileImage,
                    "city" to city,
                    "email" to email,
                    "chat" to "false"
                )

                //Save current user's info to other user friend's list
                receiverFriendsRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                deleteFriendRequests(friendUID)
            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }


    }

    private fun deleteFriendRequests(friendUID: String){

        val currentUserRequestRef = db.collection("users").document(uid)
            .collection("friend_requests").document(friendUID)

        val receiverRequestRef = db.collection("users").document(friendUID)
            .collection("friend_requests").document(uid)

        currentUserRequestRef.delete()
        receiverRequestRef.delete()
    }


    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }


}


