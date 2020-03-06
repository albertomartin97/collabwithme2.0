package com.example.collabwithme20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_profile.*

class FriendsActivity : AppCompatActivity(), FriendsAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "FriendsActivity"

    }
    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)


        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }


        createRecyclerView()



    }
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

    override fun onUserClick(friend: FriendsModel, position: Int, buttonName: String){

        if(buttonName == "showUserProfile"){
            val fullName = friend.fullName
            val intent = Intent(this, FriendsPopUpWindow::class.java)
            intent.putExtra("profileImage", friend.profile_image)
            intent.putExtra("username", fullName)
            intent.putExtra("uid", friend.uid)
            intent.putExtra("city", friend.city)
            intent.putExtra("email", friend.email)
            startActivity(intent)

        }

    }

    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

}
