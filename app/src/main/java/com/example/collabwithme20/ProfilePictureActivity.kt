package com.example.collabwithme20

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_picture.*
import java.util.*


class ProfilePictureActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProfilePictureActivity"

    }


    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()
    private var selectedPhotoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture)

        //Profile picture onClick
        selectPhotoBtn.setOnClickListener {
            Log.d(TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

        }

        savePhotoBtn.setOnClickListener {
            uploadImageToFirebase()

            Handler().postDelayed({
                val caller = intent.getStringExtra("caller")

                if (caller == "ProfileActivity") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }else if(caller == "RegistrationActivity"){
                    val intent = Intent(this, ChooseCityActivity::class.java)
                    intent.putExtra("caller", "ProfilePictureActivity")
                    startActivity(intent)

                }
            }, 3000)
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was
            Log.d(TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectPhotoImageView.setImageBitmap(bitmap)
            selectPhotoBtn.alpha = 0f

        }

    }

    private fun uploadImageToFirebase(){
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d(TAG, "Successfully uploaded: ${it.metadata?.path}")

            ref.downloadUrl.addOnCompleteListener{taskSnapshot ->

                val url = taskSnapshot.result
                val link = url.toString()

                saveImageUrlToDB(link)
            }

        }

    }

    private fun saveImageUrlToDB(profileImageUrl : String){
        val ref = db.collection("users").document(uid)


        val user = hashMapOf(
            "profile_image" to profileImageUrl
        )

        ref.set(user, SetOptions.merge()).addOnSuccessListener {
            Log.d(TAG, "User profile created for $uid")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }





}
