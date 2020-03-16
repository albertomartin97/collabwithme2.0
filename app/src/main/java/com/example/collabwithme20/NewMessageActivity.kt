package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_new_message.backBtn

class NewMessageActivity : AppCompatActivity(), FriendsAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "NewMessageActivity"

    }
    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        backBtn.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        createRecyclerView()



    }

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


    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, MessagesActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.fade_out)
        startActivity(intent, options.toBundle())
    }



}
