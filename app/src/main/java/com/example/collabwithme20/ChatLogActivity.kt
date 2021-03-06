package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
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
import kotlinx.android.synthetic.main.activity_chat_log.backBtn
import kotlinx.android.synthetic.main.activity_messages.*
import java.util.*


class ChatLogActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChatLogActivity"

    }
    //Declares recyclerView
    private lateinit var recyclerView: RecyclerView

    //Initialize Firebase
    private val db = FirebaseFirestore.getInstance()

    //Get current user id
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    //Initialise variables
    private var friendImageName = ""
    private var friendName = ""
    private var friendUID = ""
    private var context = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, MessagesActivity::class.java)
            startActivity(intentGoToPreviousActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
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

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        //Set activity title
        chatName.text = friendName

        messageEditText.isLongClickable = true


        createRecyclerView(friendUID)

        sendMessageBtn.setOnClickListener{
            val messageContent = messageEditText.text.toString()

            if(messageContent != ""){
                addChatValueToFriends(friendUID)
                //checkIfUsersChat(friendUID)
                sendMessage(friendUID)
                messageEditText.text?.clear()
            }

        }

        //Check when keyboard is visible and set size of recyclerView
        val rootView: ViewGroup = findViewById(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r);

            val heightDiff = rootView.rootView.height - (r.bottom - r.top);
            if (heightDiff > rootView.rootView.height / 4) {
                //Keyboard shown
                chatLogRecyclerView.layoutParams = chatLogRecyclerView.layoutParams.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = 1180
                }

            }else{
                //Keyboard
                chatLogRecyclerView.layoutParams = chatLogRecyclerView.layoutParams.apply {
                    width = 0
                    height = 0
                }
            }
        }

    }

    /**
     * Creates recyclerView of the right chat room
     * @param friendUID
     */
    private fun createRecyclerView(friendUID: String) {
        val chatName: String
        val mLinearLayoutManager = LinearLayoutManager(this)

        //Check chat name
        if (friendUID < uid) {
            chatName = friendUID + uid
        } else {
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

        mLinearLayoutManager.stackFromEnd = true //Opens the end of the list from recyclerview

        recyclerView.layoutManager = mLinearLayoutManager

        adapter.startListening()

        //Scrolls recyclerView to last message sent
        adapter.registerAdapterDataObserver( object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart: Int,
                itemCount: Int
            ) {
                recyclerView.scrollToPosition(positionStart)
            }
        })

    }

    /**
     * Stores message in the right chat room
     * @param friendUID
     */
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

        //Set message
        documentRef.set(message, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    /**
     * When chat is started changes "chat" to true in db
     * @param friendUID
     */
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
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        friendRef.set(friend, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }

    /**
     * When back button is pressed goes to MessagesActivity
     */
    override fun onBackPressed() {
        val intent = Intent(this, MessagesActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

}
