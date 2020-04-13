package com.example.collabwithme20.Models

/**
 * Represents a user
 * @property first_name
 * @property last_name
 * @property music_production
 * @property singing
 * @property instrumentalist
 * @property rapping
 * @property graphic_design
 * @property clothing_design
 * @property sound_engineer
 * @property video_production
 * @property profile_image
 * @property uid
 * @property city
 * @property email
 * @property description
 */
data class UserModel(var first_name: String = "", var last_name: String = "",
                     var music_production: String = "", var singing: String = "",
                     var instrumentalist: String = "", var rapping: String = "",
                     var graphic_design: String = "", var clothing_design: String = "",
                     var sound_engineer: String = "", var video_production: String = "",
                     var profile_image: String = "", var uid: String = "", var city: String = ""
                     , var email: String = "", var description : String = ""
                     )