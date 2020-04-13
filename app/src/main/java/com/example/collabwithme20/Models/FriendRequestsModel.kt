package com.example.collabwithme20.Models

/**
 * Represents a friend request
 * @property uid
 * @property fullName
 * @property profile_image
 * @property city
 * @property email
 * @property request_state
 */
data class FriendRequestsModel( var uid: String = "", var fullName: String = "",
                                var profile_image: String = "", var city: String = "",
                                var email: String = "", var request_state: String = "")
