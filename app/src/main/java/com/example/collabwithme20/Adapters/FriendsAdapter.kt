package com.example.collabwithme20.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.collabwithme20.Models.FriendsModel
import com.example.collabwithme20.R
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.friends_row.view.*


/**
 * Friends adapter
 * @property uid
 * @property clickListener
 * @constructor
 *
 * @param array
 * @param options
 */
class FriendsAdapter(var uid: String, array: FirestoreArray<FriendsModel>,
                         options: FirestoreRecyclerOptions<FriendsModel>,
                     var clickListener: OnUserClickListener
):
    FirestoreRecyclerAdapter<FriendsModel, FriendsAdapter.FriendsViewHolder>(options){


    //Assigns variable
    private var temporaryList = array

    /**
     * Binds items to container view
     * @property containerView
     */
    class FriendsViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun bindItems(friend: FriendsModel, action: OnUserClickListener){

            containerView.friendNameTextView.text = friend.fullName

            //Put default picture is there is no image
            if(friend.profile_image == "") {
                Glide.with(containerView.profileImageFriend).load(R.drawable.default_profile_pic)
                    .transform(CircleCrop()).into(containerView.profileImageFriend)
            }else {
                Glide.with(containerView.profileImageFriend).load(friend.profile_image)
                    .transform(CircleCrop()).into(containerView.profileImageFriend)
            }


            containerView.chatBtn.visibility = View.INVISIBLE
            containerView.setOnClickListener {
                action.onUserClick(friend, adapterPosition, "showUserProfile")
            }

        }

    }

    /**
     * Creates view holder
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(R.layout.friends_row, parent, false)

        return FriendsViewHolder(
            inflatedView
        )
    }


    /**
     * Binds view holder
     * @param p0
     * @param p1
     * @param p2
     */
    override fun onBindViewHolder(p0: FriendsViewHolder, p1: Int, p2: FriendsModel) {

        p0.apply {

                p0.bindItems(temporaryList[p1], clickListener)

        }
    }

    /**
     * Gets item count
     */
    override fun getItemCount() = temporaryList.size

    /**
     * Manages user click
     */
    interface OnUserClickListener{
        fun onUserClick(friend: FriendsModel, position: Int, buttonName: String)
    }
}