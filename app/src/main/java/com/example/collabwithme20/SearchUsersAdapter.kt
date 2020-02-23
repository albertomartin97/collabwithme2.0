package com.example.collabwithme20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collabwithme20.Models.UserModel
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.search_user_row.view.*





class SearchUsersAdapter(var array: FirestoreArray<UserModel>,options: FirestoreRecyclerOptions<UserModel>):
    FirestoreRecyclerAdapter<UserModel, SearchUsersAdapter.ViewHolder>(options){

    class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun bindItems(user : UserModel){
            containerView.usernameTextView.text = user.first_name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUsersAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(com.example.collabwithme20.R.layout.search_user_row,
            parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int, p2: UserModel) {
        p0.apply {
            p0.bindItems(array[p1])

        }

    }

    override fun getItemCount() = array.size


}



/*
// Create new views (invoked by the layout manager)
override fun onCreateViewHolder(parent: ViewGroup,
                                viewType: Int): SearchUsersAdapter.CustomViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)

    val cellForRow = layoutInflater.inflate((R.layout.search_user_row), parent,
        false)
    return SearchUsersAdapter.CustomViewHolder(cellForRow)
}

// Replace the contents of a view (invoked by the layout manager)
override fun onBindViewHolder(holder: SearchUsersAdapter.CustomViewHolder, position: Int) {

}

// Return the size of your dataset (invoked by the layout manager)
override fun getItemCount() = 10
*/