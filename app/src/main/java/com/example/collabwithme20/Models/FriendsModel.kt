package com.example.collabwithme20.Models

/**
 * Represents a friend
 * @property city
 * @property email
 * @property fullName
 * @property profile_image
 * @property uid
 * @property description
 */
data class FriendsModel(var city: String = "",  var email: String = "", var fullName: String = "",
                        var profile_image: String = "", var uid: String = ""
                        , var description: String = "")