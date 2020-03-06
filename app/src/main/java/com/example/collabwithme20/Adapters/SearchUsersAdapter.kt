package com.example.collabwithme20.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.collabwithme20.Models.UserModel
import com.example.collabwithme20.R
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.search_user_row.view.*


class SearchUsersAdapter(var uid: String, array: FirestoreArray<UserModel>,
                         options: FirestoreRecyclerOptions<UserModel>,
                         var clickListener: OnUserClickListener
):
    FirestoreRecyclerAdapter<UserModel, SearchUsersAdapter.ViewHolder>(options){



    private var temporaryList = array


    class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun bindItems(user : UserModel, action: OnUserClickListener){

            val fullName = user.first_name + " " + user.last_name

            containerView.usernameTextView.text = fullName

            if(user.profile_image == ""){
                Glide.with(containerView.profileImageViewList).load(R.drawable.default_profile_pic)
                    .transform(CircleCrop()).into(containerView.profileImageViewList)
            }else {
                Glide.with(containerView.profileImageViewList).load(user.profile_image)
                    .transform(CircleCrop()).into(containerView.profileImageViewList)
            }
            containerView.addFriendBtn.setOnClickListener {
                action.onUserClick(user, adapterPosition, "addFriendBtn")
            }
            containerView.setOnClickListener{
                action.onUserClick(user, adapterPosition, "showUserProfile")
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(
            R.layout.search_user_row,
            parent, false)

        return ViewHolder(
            inflatedView
        )
    }



    override fun onBindViewHolder(p0: ViewHolder, p1: Int, p2: UserModel) {

        p0.apply {
            //If uid from model equals to the current user' uid then it is omitted
            if(p2.uid == uid){

                val param =
                    p0.itemView.layoutParams as RecyclerView.LayoutParams
                param.height = 0
                param.width = LinearLayout.LayoutParams.MATCH_PARENT
                p0.itemView.visibility = View.VISIBLE

            }
            else {
                //getFilter().filter(charSequence)
                p0.bindItems(temporaryList[p1], clickListener)
            }
        }
    }

    override fun getItemCount() = temporaryList.size

    interface OnUserClickListener{
        fun onUserClick(user: UserModel, position: Int, buttonName: String)
    }
}

/*
fun getFilter(): Filter {
    return object : Filter() {

        override fun performFiltering(p0: CharSequence?): FilterResults {

            if (user.uid == charSequence) temporaryList.remove(UserModel())

            val filterResults = FilterResults()
            filterResults.values = temporaryList

            return filterResults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults) {
            temporaryList = p1.values as FirestoreArray<UserModel>
            notifyDataSetChanged()
        }

    }
}

*/