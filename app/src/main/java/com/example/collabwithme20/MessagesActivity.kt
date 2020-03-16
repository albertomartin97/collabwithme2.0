package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
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
import kotlinx.android.synthetic.main.activity_find_people.*
import kotlinx.android.synthetic.main.activity_messages.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.backBtn

class MessagesActivity : AppCompatActivity(), FriendsAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "MessagesActivity"

    }
    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        newMessageBtn.setOnClickListener {
            val intent = Intent(this, NewMessageActivity::class.java)
            startActivity(intent)
        }

        newMessageBtn.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->
                    newMessageBtn.setBackgroundResource(R.drawable.plus_black_filled)
                MotionEvent.ACTION_UP ->
                    newMessageBtn.setBackgroundResource(R.drawable.plus_black)
            }
            v?.onTouchEvent(event) ?: true
        }

        //Set arrow image invisible
        empty_messages_ig.visibility = View.INVISIBLE
        empty_messages.visibility = View.INVISIBLE

        checkRecyclerViewIsEmpty()
        createRecyclerView()


    }

    private fun createRecyclerView(){

        val query = db.collection("users").document(uid)
            .collection("friends").whereEqualTo("chat", "true")


        val array = FirestoreArray(query, ClassSnapshotParser(FriendsModel::class.java))


        val options = FirestoreRecyclerOptions.Builder<FriendsModel>()
            .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.messages_recyclerView)


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
            intent.putExtra("context", "FriendsActivity")
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        }
    }

    private fun checkRecyclerViewIsEmpty(){
        val docRef = db.collection("users").document(uid)
            .collection("friends").whereEqualTo("chat", "true")

        docRef.get().addOnSuccessListener { document ->
            if (document.size() > 0)  {
                recyclerView.visibility = View.VISIBLE
                empty_messages_ig.visibility = View.INVISIBLE
            } else {
                recyclerView.visibility = View.INVISIBLE

                empty_messages_ig.setImageResource(R.drawable.grey_arrow)
                empty_messages.setText(R.string.empty_messages)
                empty_messages.visibility = View.VISIBLE
                empty_messages_ig.visibility = View.VISIBLE

            }
        }


    }


    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}
