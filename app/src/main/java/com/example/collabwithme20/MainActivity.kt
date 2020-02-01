package com.example.collabwithme20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registrationLinkTextView.setOnClickListener {
            val intentGoToRegistrationActivity = Intent(this, RegistrationActivity::class.java)
            startActivity(intentGoToRegistrationActivity)

        }


        val videoView = findViewById<VideoView>(R.id.videoView)
        val path = "android.resource://" + packageName + "/" + R.raw.djstudio_vertical_zoom
        videoView?.setVideoURI(Uri.parse(path))
        videoView.start()

    }
}
