package com.example.collabwithme20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class MessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(intentGoToPreviousActivity)
        }

    }
}
