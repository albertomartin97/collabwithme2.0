package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collabwithme20.Adapters.FriendsAdapter
import com.example.collabwithme20.Models.FriendsModel
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.activity_profile.backBtn


class FriendsActivity : AppCompatActivity(), FriendsAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "FriendsActivity"

    }

    //Declares recyclerView
    private lateinit var recyclerView: RecyclerView

    //Initialize Firebase
    private val db = FirebaseFirestore.getInstance()

    //Get current user id
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)


        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //Open friend requests
        friendRequestsBtn.setOnClickListener {
            val intent = Intent(this, FriendRequestsActivity::class.java)
            startActivity(intent)
        }

        //Click effect on img button
        friendRequestsBtn.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->
                    friendRequestsBtn.setBackgroundResource(R.drawable.bright_bell)
                MotionEvent.ACTION_UP ->
                    friendRequestsBtn.setBackgroundResource(R.drawable.bell)
            }
            v?.onTouchEvent(event) ?: true
        }



        //Open FindPeopleActivity (Discover)
        discoverBtn.setOnClickListener {
            val intent = Intent(this, FindPeopleActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //Set discoverBtn invisible
        discoverBtn.visibility = View.INVISIBLE

        createRecyclerView()

        updateNotificationIcon()

        checkRecyclerViewIsEmpty()

    }

    /**
     * Creates recyclerView displaying friends in db
     */
    private fun createRecyclerView(){

        val query = db.collection("users").document(uid)
            .collection("friends")


        val array = FirestoreArray(query, ClassSnapshotParser(FriendsModel::class.java))


        val options = FirestoreRecyclerOptions.Builder<FriendsModel>()
            .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.friends_recyclerView)


        val adapter: FirestoreRecyclerAdapter<*, *> =
            FriendsAdapter(
                uid,
                array,
                options,
                this
            )


        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }

    /**
     * Manages when user clicks on recyclerView elements
     * @param friend
     * @param position
     * @param buttonName
     */
    override fun onUserClick(friend: FriendsModel, position: Int, buttonName: String){

        //Opens FriendsPopUpWindow
        if(buttonName == "showUserProfile"){
            val fullName = friend.fullName
            val intent = Intent(this, FriendsPopUpWindow::class.java)
            intent.putExtra("profileImage", friend.profile_image)
            intent.putExtra("username", fullName)
            intent.putExtra("uid", friend.uid)
            intent.putExtra("city", friend.city)
            intent.putExtra("email", friend.email)
            intent.putExtra("description", friend.description)
            intent.putExtra("context", "FriendsActivity")
            startActivity(intent)

        //Opens MessagesActivity
        }else if(buttonName == "chatBtn"){
            val intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Checks friends list is empty to display message
     */
    private fun checkRecyclerViewIsEmpty(){
        val docRef = db.collection("users").document(uid)
            .collection("friends")

        docRef.get().addOnSuccessListener { document ->
            if (document.size() > 0)  {
                recyclerView.visibility = View.VISIBLE
                discoverBtn.visibility = View.INVISIBLE
            } else {
                recyclerView.visibility = View.INVISIBLE

                empty_friends.setText(R.string.empty_friends)
                discoverBtn.visibility = View.VISIBLE
            }
        }


    }

    /**
     * Checks friend requests in db to update button
     */
    private fun updateNotificationIcon(){

        val docRef = db.collection("users").document(uid)
            .collection("friend_requests")

        //Check if there are friend requests
        docRef.get().addOnSuccessListener { document ->
            if (document.size() > 0) {
                friendRequestsBtn.setBackgroundResource(R.drawable.notification)

                friendRequestsBtn.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN ->
                            friendRequestsBtn.setBackgroundResource(R.drawable.bright_notification)
                        MotionEvent.ACTION_UP ->
                            friendRequestsBtn.setBackgroundResource(R.drawable.notification)
                    }
                    v?.onTouchEvent(event) ?: true
                }

                //Assign notification number
                if (document.size() == 1){
                    smallNotificationNumber.text = "1"
                }
                if (document.size() == 2){
                    smallNotificationNumber.text = "2"
                }
                if (document.size() == 3){
                    smallNotificationNumber.text = "3"
                }
                if (document.size() == 4){
                    smallNotificationNumber.text = "4"
                }
                if (document.size() == 5){
                    smallNotificationNumber.text = "5"
                }
                if (document.size() == 6){
                    smallNotificationNumber.text = "6"
                }
                if (document.size() == 7){
                    smallNotificationNumber.text = "7"
                }
                if (document.size() == 8){
                    smallNotificationNumber.text = "8"
                }
                if (document.size() == 9){
                    smallNotificationNumber.text = "9"
                }
                if (document.size() > 9){
                    smallNotificationNumber.text = "9+"
                    smallNotificationNumber.x = 1050F
                }

            }

            else {
                friendRequestsBtn.setBackgroundResource(R.drawable.bell)

                friendRequestsBtn.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN ->
                            friendRequestsBtn.setBackgroundResource(R.drawable.bright_bell)
                        MotionEvent.ACTION_UP ->
                            friendRequestsBtn.setBackgroundResource(R.drawable.bell)
                    }
                    v?.onTouchEvent(event) ?: true
                }
            }
        }

    }

    /**
     * Go to home screen when back button is pressed
     */
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

}
