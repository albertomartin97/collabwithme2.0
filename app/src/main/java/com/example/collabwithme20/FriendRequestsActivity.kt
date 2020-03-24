package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.R.anim
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collabwithme20.Adapters.FriendRequestsAdapter
import com.example.collabwithme20.Models.FriendRequestsModel
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friend_requests.*
import kotlinx.android.synthetic.main.activity_friend_requests.backBtn


class FriendRequestsActivity : AppCompatActivity() , FriendRequestsAdapter.OnUserClickListener{
    companion object {
        private const val TAG = "FriendRequestsActivity"

    }
    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_requests)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, FriendsActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this,
                anim.slide_in_left, anim.fade_out)
            startActivity(intentGoToPreviousActivity, options.toBundle())
        }

        createRecyclerView()

        checkRecyclerViewIsEmpty()

    }

    private fun createRecyclerView(){

        val query = db.collection("users").document(uid)
            .collection("friend_requests")


        val array = FirestoreArray(query, ClassSnapshotParser(FriendRequestsModel::class.java))



        val options =   FirestoreRecyclerOptions.Builder<FriendRequestsModel>()
            .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.friend_requests_recyclerView)


        val adapter: FirestoreRecyclerAdapter<*, *> =
            FriendRequestsAdapter(
                uid,
                array,
                options,
                this
            )


        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }

    override fun onUserClick(friendRequest: FriendRequestsModel, position: Int, buttonName: String){

        //Opens FriendsPopUpWindow
        if(buttonName == "showUserProfile"){
            val fullName = friendRequest.fullName
            val intent = Intent(this, FriendsPopUpWindow::class.java)
            intent.putExtra("profileImage", friendRequest.profile_image)
            intent.putExtra("username", fullName)
            intent.putExtra("uid", friendRequest.uid)
            intent.putExtra("city", friendRequest.city)
            intent.putExtra("email", friendRequest.email)
            intent.putExtra("context", "FriendRequestsActivity")
            startActivity(intent)

            //Opens MessagesActivity
        }else if(buttonName == "acceptUserBtn"){
            acceptFriend(friendRequest.uid, friendRequest.fullName, friendRequest.profile_image
            , friendRequest.city, friendRequest.email)
        }
    }



    private fun acceptFriend(friendUID: String, friendName: String, profileImage: String, friendCity: String, friendEmail: String){

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

        //Save current user info to the other person's friend list
        addCurrentUserToFriendsDB(friendUID)

        //Save friend to current users' friend list
        currentUserFriendsRef.set(friendUser).addOnSuccessListener {
            Log.d(TAG, "Friend created for $uid")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show()

    }


    private fun addCurrentUserToFriendsDB(friendUID: String){
        val receiverFriendsRef  = db.collection("users").document(friendUID)
            .collection("friends").document(uid)

        val currentUserRef = db.collection("users").document(uid)

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String


        //Get current user's information and save it to other person's friend list
        currentUserRef.get().addOnSuccessListener { document ->
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

                //Save to other person's friend list
                receiverFriendsRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                //Delete friend request when saved into db
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

    private fun checkRecyclerViewIsEmpty(){
        val docRef = db.collection("users").document(uid)
            .collection("friend_requests")

        docRef.get().addOnSuccessListener { document ->
            if (document.size() > 0)  {
                recyclerView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.INVISIBLE
                empty_friend_requests.setText(R.string.empty_friend_requests)
            }
        }


    }

    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, FriendsActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(this, anim.slide_in_left, anim.fade_out)
        startActivity(intent, options.toBundle())
    }




}
