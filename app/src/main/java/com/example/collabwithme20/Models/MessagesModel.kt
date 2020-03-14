package com.example.collabwithme20.Models

import com.google.firebase.Timestamp


data class MessagesModel(
    var receiver_uid: String = "", var sender_uid: String = "",
    var message_content: String = "", var timestamp: Timestamp? = null )