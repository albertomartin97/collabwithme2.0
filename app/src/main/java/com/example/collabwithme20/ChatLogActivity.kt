package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.collabwithme20.Adapters.ChatLogAdapter
import com.example.collabwithme20.Models.MessagesModel
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_chat_log.*
import java.util.*


class ChatLogActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChatLogActivity"

    }
    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()


    private var friendImageName = ""
    private var friendName = ""
    private var friendUID = ""
    private var context = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, MessagesActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

        //Get data from NewMessageActivity
        val bundle = intent.extras
        friendImageName = bundle?.getString("profileImage", "Image") ?: ""
        friendName = bundle?.getString("username", "Name") ?: ""
        friendUID = bundle?.getString("uid", "User UID") ?: ""

        if(friendImageName == ""){
            Glide.with(chatProfilePicture).load(R.drawable.default_profile_pic).transform(CircleCrop())
                .into(chatProfilePicture)
        }else{
            Glide.with(chatProfilePicture).load(friendImageName).transform(CircleCrop())
                .into(chatProfilePicture)
        }


        chatName.text = friendName

        createRecyclerView(friendUID)

        sendMessageBtn.setOnClickListener{
            val messageContent = messageEditText.text.toString()

            if(messageContent != ""){
                sendMessage(friendUID)
                messageEditText.text?.clear()
            }

        }


    }

    private fun createRecyclerView(friendUID: String){
        val chatName: String

        //Check chat name
        if(friendUID < uid){
            chatName = friendUID + uid
        }else{
            chatName = uid + friendUID
        }

        val query = db.collection("chat").document(chatName)
            .collection("messages").orderBy("timestamp", Query.Direction.ASCENDING)


        val array = FirestoreArray(query, ClassSnapshotParser(MessagesModel::class.java))


        val options = FirestoreRecyclerOptions.Builder<MessagesModel>()
            .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.chatLogRecyclerView)


        val adapter: FirestoreRecyclerAdapter<*, *> =
            ChatLogAdapter(
                array,
                options
            )


        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }

    //Send message
    private fun sendMessage(friendUID: String){
        val messageContent = messageEditText.text.toString()
        val chatID: String
        val messageID = UUID.randomUUID().toString()
        val timestamp = Timestamp.now()

        if(uid < friendUID) {
            chatID = uid + friendUID
        }else{
            chatID = friendUID + uid
        }

        val documentRef = db.collection("chat").
            document(chatID).collection("messages").document(messageID)

        val message = hashMapOf(
            "message_content" to messageContent,
            "sender_uid" to uid,
            "receiver_uid" to friendUID,
            "timestamp" to timestamp
        )

        addChatValueToFriends(friendUID)

        //Set message
        documentRef.set(message, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    private fun addChatValueToFriends(friendUID: String){

        val currentUserRef = db.collection("users").
            document(uid).collection("friends").document(friendUID)

        val friendRef = db.collection("users").
            document(friendUID).collection("friends").document(uid)

        val friend = hashMapOf(
            "chat" to "true"
        )

        currentUserRef.set(friend, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
            addChatValueToFriends(friendUID)
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        friendRef.set(friend, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
            addChatValueToFriends(friendUID)
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }




    }

    override fun onBackPressed() {
        val intent = Intent(this, MessagesActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(this,
            android.R.anim.slide_in_left, android.R.anim.fade_out)
        startActivity(intent, options.toBundle())
    }

}
