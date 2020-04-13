package com.example.collabwithme20.Models

import com.google.firebase.Timestamp


/**
 * Represents a message
 * @property receiver_uid
 * @property sender_uid
 * @property message_content
 * @property timestamp
 */
data class MessagesModel(
    var receiver_uid: String = "", var sender_uid: String = "",
    var message_content: String = "", var timestamp: Timestamp? = null )