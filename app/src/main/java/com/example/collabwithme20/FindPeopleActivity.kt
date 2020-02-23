package com.example.collabwithme20


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.collabwithme20.Models.UserModel
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*



open class FindPeopleActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    //private lateinit var viewAdapter: Adapter<*>
    //private var viewAdapter: SearchUsersAdapter? = null
    //private lateinit var viewManager: LayoutManager
    //private var adapter: SearchUsersAdapter? = null


    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.collabwithme20.R.layout.activity_find_people)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

        createRecyclerView()


    }


    private fun createRecyclerView(){

        val query = db.collection("users")

        //val options = FirestoreRecyclerOptions.Builder<UserModel>()
        //  .setQuery(query, UserModel::class.java).build()

        val array = FirestoreArray(query, ClassSnapshotParser(UserModel::class.java))

        val options = FirestoreRecyclerOptions.Builder<UserModel>()
          .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.search_recyclerView)

        val adapter: FirestoreRecyclerAdapter<*, *> = SearchUsersAdapter(array, options)
        //viewAdapter = SearchUsersAdapter(options)

        //recyclerView.layoutManager = viewManager
        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }




}


