package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity(), FriendsAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "NewMessageActivity"

    }

    //Declares recyclerView
    private lateinit var recyclerView: RecyclerView

    //Initialize Firebase
    private val db = FirebaseFirestore.getInstance()

    //Get current user id
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        backBtn.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        checkRecyclerViewIsEmpty()
        createRecyclerView()

    }

    /**
     * Creates recyclerView with friends where chat equals false (which means that they have not
     * talked before
     */
    private fun createRecyclerView(){

        val query = db.collection("users").document(uid)
            .collection("friends").whereEqualTo("chat", "false")


        val array = FirestoreArray(query, ClassSnapshotParser(FriendsModel::class.java))


        val options = FirestoreRecyclerOptions.Builder<FriendsModel>()
            .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.new_message_recyclerView)


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

        //Opens chat log
        if(buttonName == "showUserProfile"){
            val fullName = friend.fullName
            val intent = Intent(this, ChatLogActivity::class.java)
            intent.putExtra("profileImage", friend.profile_image)
            intent.putExtra("username", fullName)
            intent.putExtra("uid", friend.uid)
            intent.putExtra("city", friend.city)
            intent.putExtra("email", friend.email)
            intent.putExtra("description", friend.description)
            intent.putExtra("context", "NewMessageActivity")
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

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
            } else {
                recyclerView.visibility = View.INVISIBLE
                empty_friends_for_chat.setText(R.string.empty_friends_for_chat)
            }
        }

    }
    /**
     * Go to messages activity when back button is pressed
     */
    override fun onBackPressed() {
        val intent = Intent(this, MessagesActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.fade_out)
        startActivity(intent, options.toBundle())
    }



}
