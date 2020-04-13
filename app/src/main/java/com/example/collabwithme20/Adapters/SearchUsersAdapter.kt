package com.example.collabwithme20.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
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

/**
 * Find people adapter
 * @property uid
 * @property clickListener
 * @property city
 * @property skill
 * @constructor
 *
 * @param array
 * @param options
 */
class SearchUsersAdapter(var uid: String, array: FirestoreArray<UserModel>,
                         options: FirestoreRecyclerOptions<UserModel>,
                         var clickListener: OnUserClickListener, var city: String, var skill: String
):
    FirestoreRecyclerAdapter<UserModel, SearchUsersAdapter.ViewHolder>(options){


    //Assigns variable
    private var temporaryList = array


    /**
     * Binds items to container view
     * @property containerView
     */
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

    /**
     * Creates view holder
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(
            R.layout.search_user_row,
            parent, false)

        return ViewHolder(
            inflatedView
        )
    }


    /**
     * Binds view holder
     * @param p0
     * @param p1
     * @param p2
     */
    override fun onBindViewHolder(p0: ViewHolder, p1: Int, p2: UserModel) {

        p0.apply {
            //If uid from model equals to the current user' uid then it is omitted
            if(p2.uid == uid){
                val param =
                    p0.itemView.layoutParams as RecyclerView.LayoutParams
                param.height = 0
                param.width = LinearLayout.LayoutParams.MATCH_PARENT
                p0.itemView.visibility = View.INVISIBLE

             //If user's city is different from the value passed is removed
            }else if(city != "All" && p2.city != city){
                val param =
                    p0.itemView.layoutParams as RecyclerView.LayoutParams
                param.height = 0
                param.width = LinearLayout.LayoutParams.MATCH_PARENT
                p0.itemView.visibility = View.INVISIBLE
            }
            //If value passed is "All" show every person from db
            else if(city == "All") {

                //Check what skill is selected and omit the rest
                if(skill == "All"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }
                if (skill == "music_production" && p2.music_production == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "music_production" && p2.music_production != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "singing" && p2.singing == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "singing" && p2.singing != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "rapping" && p2.rapping == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "rapping" && p2.rapping != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "video_production" && p2.video_production == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "video_production" && p2.video_production != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "graphic_designer" && p2.graphic_design == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "graphic_designer" && p2.graphic_design != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "clothing_design" && p2.clothing_design == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "clothing_design" && p2.clothing_design != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "instrumentalist" && p2.instrumentalist == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "instrumentalist" && p2.instrumentalist != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "sound_engineer" && p2.sound_engineer == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "sound_engineer" && p2.sound_engineer != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }

            }
            //Show user if the value passed is equal to user's city
            else if(p2.city == city){

                //Check what skill is selected and omit the rest
                if(skill == "All"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }
                if (skill == "music_production" && p2.music_production == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "music_production" && p2.music_production != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "singing" && p2.singing == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "singing" && p2.singing != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "rapping" && p2.rapping == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "rapping" && p2.rapping != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "video_production" && p2.video_production == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "video_production" && p2.video_production != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "graphic_designer" && p2.graphic_design == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "graphic_designer" && p2.graphic_design != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "clothing_design" && p2.clothing_design == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "clothing_design" && p2.clothing_design != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "instrumentalist" && p2.instrumentalist == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "instrumentalist" && p2.instrumentalist != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }
                if (skill == "sound_engineer" && p2.sound_engineer == "true"){
                    p0.bindItems(temporaryList[p1], clickListener)
                }else if (skill == "sound_engineer" && p2.sound_engineer != "true"){
                    val param =
                        p0.itemView.layoutParams as RecyclerView.LayoutParams
                    param.height = 0
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT
                    p0.itemView.visibility = View.INVISIBLE
                }


            }


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
        fun onUserClick(user: UserModel, position: Int, buttonName: String)
    }
}

