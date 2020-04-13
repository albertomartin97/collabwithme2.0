package com.example.collabwithme20.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collabwithme20.Models.MessagesModel
import com.example.collabwithme20.R
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chat_to_row.view.messageTextViewRow


/**
 * Chat adapter
 *
 * @constructor
 *
 * @param array
 * @param options
 */
class ChatLogAdapter(array: FirestoreArray<MessagesModel>,
                     options: FirestoreRecyclerOptions<MessagesModel>):
    FirestoreRecyclerAdapter<MessagesModel, ChatLogAdapter.ChatLogViewHolder>(options){

    //Initialise variables
    private var temporaryList = array
    private val MESSAGE_SENDER = 1
    private val MESSAGE_RECEIVER = 2

    //Get current user id
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    /**
     * Binds items to container view
     * @property containerView
     */
    class ChatLogViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun bindItems(message: MessagesModel){
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

            if(uid == message.sender_uid){
                containerView.messageTextViewRow.text = message.message_content

            }else{
                containerView.messageTextViewRow.text = message.message_content

            }
        }

    }

    /**
     * Checks position
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).sender_uid == (uid)) {
            MESSAGE_SENDER
        } else MESSAGE_RECEIVER
    }

    /**
     * Creates view holder
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var inflatedView= layoutInflater.inflate(R.layout.chat_to_row, parent, false)
        if(viewType == 1){
            inflatedView = layoutInflater.inflate(R.layout.chat_to_row, parent, false)
        }else if(viewType == 2){
            inflatedView = layoutInflater.inflate(R.layout.chat_from_row, parent, false)
        }

        return ChatLogViewHolder(
            inflatedView
        )
    }


    /**
     * Binds view holder
     * @param p0
     * @param p1
     * @param p2
     */
    override fun onBindViewHolder(p0: ChatLogViewHolder, p1: Int, p2: MessagesModel) {

        p0.apply {

            p0.bindItems(temporaryList[p1])

        }
    }

    /**
     * Gets item count
     */
    override fun getItemCount() = temporaryList.size

}