package com.example.collabwithme20.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.collabwithme20.Models.FriendRequestsModel
import com.example.collabwithme20.R
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.friend_requests_row.view.*



class FriendRequestsAdapter(var uid: String, array: FirestoreArray<FriendRequestsModel>,
                            options: FirestoreRecyclerOptions<FriendRequestsModel>,
                            var clickListener: OnUserClickListener
):
    FirestoreRecyclerAdapter<FriendRequestsModel, FriendRequestsAdapter.FriendRequestsViewHolder>(options){



    private var temporaryList = array


    class FriendRequestsViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun bindItems(friendRequest: FriendRequestsModel, action: OnUserClickListener){


            containerView.friendNameTextView.text = friendRequest.fullName


            if(friendRequest.profile_image == "") {
                Glide.with(containerView.profileImageFriend).load(R.drawable.default_profile_pic)
                    .transform(CircleCrop()).into(containerView.profileImageFriend)
            }else {
                Glide.with(containerView.profileImageFriend).load(friendRequest.profile_image)
                    .transform(CircleCrop()).into(containerView.profileImageFriend)
            }

            containerView.setOnClickListener {
                action.onUserClick(friendRequest, adapterPosition, "showUserProfile")
            }

            containerView.acceptFriend.setOnClickListener {
                action.onUserClick(friendRequest, adapterPosition, "acceptUserBtn")



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(R.layout.friend_requests_row, parent, false)

        return FriendRequestsViewHolder(
            inflatedView
        )
    }



    override fun onBindViewHolder(p0: FriendRequestsViewHolder, p1: Int, p2: FriendRequestsModel) {

        p0.apply {

            p0.bindItems(temporaryList[p1], clickListener)

        }
    }

    override fun getItemCount() = temporaryList.size

    interface OnUserClickListener{
        fun onUserClick(friendRequest: FriendRequestsModel, position: Int, buttonName: String)
    }
}