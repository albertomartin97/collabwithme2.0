package com.example.collabwithme20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchUsersAdapter :
    RecyclerView.Adapter<SearchUsersAdapter.CustomViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class CustomViewHolder(v : View) : RecyclerView.ViewHolder(v)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SearchUsersAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate((R.layout.search_user_row), parent,
            false)
        return CustomViewHolder(cellForRow)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = 10

}