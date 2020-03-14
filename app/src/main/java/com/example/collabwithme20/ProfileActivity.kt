package com.example.collabwithme20


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProfileActivity"

    }


    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        getUserData()   //Get user data from DB
        setProfilePicture()  //Ge profile image name

        //Set colours when activity is launched
        changeSkillsColour("music_production")
        changeSkillsColour("singing")
        changeSkillsColour("rapping")
        changeSkillsColour("video_production")
        changeSkillsColour("graphic_design")
        changeSkillsColour("clothing_design")
        changeSkillsColour("sound_engineer")
        changeSkillsColour("instrumentalist")

        profileImageView2.setOnClickListener {
            val intent = Intent(this, ProfilePictureActivity::class.java)
            intent.putExtra("caller", "ProfileActivity")
            startActivity(intent)
        }

        changePhotoBtn.setOnClickListener {
            val intent = Intent(this, ProfilePictureActivity::class.java)
            intent.putExtra("caller", "ProfileActivity")
            startActivity(intent)
        }


        cityTextView.setOnClickListener {
            val intent = Intent(this, ChooseCityActivity::class.java)
            intent.putExtra("caller", "ProfileActivity" )
            startActivity(intent)
        }

        saveDescriptionBtn.setOnClickListener {
            val desc = descriptionEditText.text.toString()
            if (desc != "") {
                saveDescriptionIntoDB()
                Toast.makeText(this, "Description updated", Toast.LENGTH_SHORT).show()
            }


        }

        //Update skills

        musicProductionBtn.setOnClickListener {

            updateSkills("music_production")

        }

        singerBtn.setOnClickListener {

            updateSkills("singing")
        }

        rapperBtn.setOnClickListener {

            updateSkills("rapping")

        }

        videoProductionBtn.setOnClickListener {

            updateSkills("video_production")

        }

        graphicDesignerBtn.setOnClickListener {

            updateSkills("graphic_design")

        }

        clothingDesignBtn.setOnClickListener {

            updateSkills("clothing_design")

        }

        soundEngineeringBtn.setOnClickListener {

            updateSkills("sound_engineer")

        }

        instrumentalistBtn.setOnClickListener {

            updateSkills("instrumentalist")

        }


    }

    private fun getUserData(){

        var firstName: String
        var lastName : String
        val userName = usernameTextView
        val fullName = firstNameTextView
        val email = emailTextView
        val city = cityTextView
        val description = descriptionEditText

        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                userName.text = document.getString("first_name")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()

                val name  = "$firstName $lastName"
                val desc = document.getString("description")

                fullName.hint = name
                email.text = document.getString("email")
                city.text = document.getString("city")

                //Check if description exists and sets hint
                if (desc == null || desc == "")
                description.hint = "Bio and link your work"
                else{
                    description.hint = desc
                }

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }
    }


    //Assigns colour to the selected skills
    private fun changeSkillsColour(skill: String) {

        lateinit var button: Button

        //Assign button to change style
        when (skill) {
            "music_production" -> {
                button = musicProductionBtn
            }
            "singing" -> {
                button = singerBtn
            }
            "rapping" -> {
                button = rapperBtn
            }
            "video_production" -> {
                button = videoProductionBtn
            }
            "graphic_design" -> {
                button = graphicDesignerBtn
            }
            "clothing_design" -> {
                button = clothingDesignBtn
            }
            "sound_engineer" -> {
                button = soundEngineeringBtn
            }
            "instrumentalist" -> {
                button = instrumentalistBtn
            }
        }

        val documentRef = db.collection("users").document(uid)

        documentRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                when {
                    document.getString(skill) == "true" -> {

                        //Change style of button
                        button.setBackgroundResource(R.drawable.style10)

                    }
                    document.getString(skill) == "false" -> {

                        //Change style of button
                        button.setBackgroundResource(R.drawable.style4)

                    }
                    document.getString(skill) == null -> {

                        //Change style of button
                        button.setBackgroundResource(R.drawable.style4)

                    }
                    else -> {
                        Log.d("doesn't exist", "No such document")

                    }
                }
            }

        }
    }

    private fun setProfilePicture(){
        var imageLink : String?

        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                imageLink = document.getString("profile_image")

                if(imageLink == null){
                    Glide.with(this)
                        .load(R.drawable.default_profile_pic)
                        .transform(CircleCrop())
                        .into(profileImageView2)
                }else {
                    Glide.with(this)
                        .load(imageLink)
                        .transform(CircleCrop())
                        .into(profileImageView2)
                }

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }


    }

    private fun saveDescriptionIntoDB(){
        val descriptionContent = descriptionEditText.text.toString()

        val documentRef = db.collection("users").document(uid)

        val user = hashMapOf(
            "description" to descriptionContent
        )

        //Set user document with the description
        documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun updateNameIntoDB(){
        val name = firstNameTextView.text.toString()

        val documentRef = db.collection("users").document(uid)

        if(name != ""){
            val lastName = name.substring(name.lastIndexOf(" ")+1)
            val firstName = name.substring(0, name.lastIndexOf(" "))

            val user = hashMapOf(
                "first_name" to firstName,
                "last_name" to lastName
            )

            //Set user document with the description
            documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                Log.d(TAG, "User profile created for $uid")
            }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

    }


    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val name = firstNameTextView.text.toString()

        val count = name.split(" ").size

        //Check if input is name and surname
        when {
            count >= 2 -> {
                updateNameIntoDB()

                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

            }
            name.length <= 2 -> {

                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            else -> {
                Toast.makeText(this, "Please insert name and surname", Toast.LENGTH_SHORT).show()

            }
        }



    }


    private fun updateSkills(skill : String){
        lateinit var button: Button

        //Assign button being clicked
        when (skill) {
            "music_production" -> {
                button = musicProductionBtn
            }
            "singing" -> {
                button = singerBtn
            }
            "rapping" -> {
                button = rapperBtn
            }
            "video_production" -> {
                button = videoProductionBtn
            }
            "graphic_design" -> {
                button = graphicDesignerBtn
            }
            "clothing_design" -> {
                button = clothingDesignBtn
            }
            "sound_engineer" -> {
                button = soundEngineeringBtn
            }
            "instrumentalist" -> {
                button = instrumentalistBtn
            }
        }


        val documentRef = db.collection("users").document(uid)

        documentRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                when {
                    document.getString(skill) == "true" -> {

                        //Change style of button
                        button.setBackgroundResource(R.drawable.style4)

                        //Change skill to false
                        val skillTrue = "false"
                        val user = hashMapOf(
                            skill to skillTrue
                        )


                        //Merge user document with the skill update
                        documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                            Log.d(TAG, "User profile created for $uid")
                        }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }

                    }
                    document.getString(skill) == "false" -> {

                        //Change style of button
                        button.setBackgroundResource(R.drawable.style10)

                        //Change skill to true
                        val skillTrue = "true"
                        val user = hashMapOf(
                            skill to skillTrue
                        )

                        //Merge user document with the skill update
                        documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                            Log.d(TAG, "User profile created for $uid")
                        }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }

                    }
                    document.getString(skill) == null -> {

                        //Change style of button
                        button.setBackgroundResource(R.drawable.style10)

                        //Change skill to true
                        val skillTrue = "true"
                        val user = hashMapOf(
                            skill to skillTrue
                        )

                        //Set user document with the skill update
                        documentRef.set(user, SetOptions.merge()).addOnSuccessListener {
                            Log.d(TAG, "User profile created for $uid")
                        }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                }

            } else {
                Log.d("doesn't exist", "No such document")


            }
        }

    }



}

