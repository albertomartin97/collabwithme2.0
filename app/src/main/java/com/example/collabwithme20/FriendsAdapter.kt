package com.example.collabwithme20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.collabwithme20.Models.FriendsModel
import com.example.collabwithme20.Models.UserModel
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.friends_row.view.*




class FriendsAdapter(var uid: String, array: FirestoreArray<FriendsModel>,
                         options: FirestoreRecyclerOptions<FriendsModel>,
                     var clickListener: OnUserClickListener):
    FirestoreRecyclerAdapter<FriendsModel, FriendsAdapter.FriendsViewHolder>(options){



    private var temporaryList = array


    class FriendsViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun bindItems(friend: FriendsModel, action: OnUserClickListener){

            containerView.friendNameTextView.text = friend.fullName

            Glide.with(containerView.profileImageFriend).load(friend.profile_image).
                transform(CircleCrop()).into(containerView.profileImageFriend)

            containerView.setOnClickListener {
                action.onUserClick(friend, adapterPosition, "showUserProfile")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(R.layout.friends_row, parent, false)

        return FriendsViewHolder(inflatedView)
    }



    override fun onBindViewHolder(p0: FriendsViewHolder, p1: Int, p2: FriendsModel) {

        p0.apply {

                p0.bindItems(temporaryList[p1], clickListener)

        }
    }

    override fun getItemCount() = temporaryList.size

    interface OnUserClickListener{
        fun onUserClick(friend: FriendsModel, position: Int, buttonName: String)
    }
}