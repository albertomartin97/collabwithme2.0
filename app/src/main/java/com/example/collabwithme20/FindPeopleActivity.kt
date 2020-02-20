package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.collabwithme20.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_profile.backBtn
import kotlinx.android.synthetic.main.search_user_row.*


class FindPeopleActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_people)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

        //getUserData()
        //setProfilePicture()

        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchUsersAdapter()

        recyclerView = findViewById<RecyclerView>(R.id.search_recyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    /*
    private fun getUserData() {

        val userName = username_textView


        val docRef = db.collection("users").orderBy("first_name", Query.Direction.ASCENDING)

        val options = Firebase.Builder().setQuery(this, UserModel::class).build()
    }

    private inner class UserViewHolder internal constructor(private val view: View)
        : RecyclerView.ViewHolder(view) {
        internal fun setUserName(userName: String) {
            val textView = view.findViewById<TextView>(R.id.username_textView)
            textView.text = userName
        }
    }

    private inner class ProductFirestoreRecyclerAdapter
    internal constructor(options: FirestoreRecyclerOptions<UserModel>) : FirestoreRecyclerAdapter
    <UserModel, UserViewHolder>(options) {
        override fun onBindViewHolder(productViewHolder: UserViewHolder, position: Int, userModel: UserModel) {
            productViewHolder.setUserName(UserModel.first_name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }
    }
    private fun setProfilePicture() {
        var imageLink: String?

        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                imageLink = document.getString("profile_image")

                Glide.with(this)
                    .load(imageLink)
                    .transform(CircleCrop())
                    .into(profileImageView2)

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

    }
*/
}
