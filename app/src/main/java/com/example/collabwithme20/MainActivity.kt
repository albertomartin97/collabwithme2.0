package com.example.collabwithme20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {


    public override fun onResume() {
        super.onResume()
        videoView.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loginTextView.setOnClickListener {
            val intentGoToRegistrationActivity = Intent(this, RegistrationActivity::class.java)
            startActivity(intentGoToRegistrationActivity)

        }

        loginBtn.setOnClickListener {
            val intentGoToLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentGoToLoginActivity)

        }


        val videoView = findViewById<VideoView>(R.id.videoView)
        val path = "android.resource://" + packageName + "/" + R.raw.djstudio_vertical_zoom
        videoView?.setVideoURI(Uri.parse(path))
        videoView.start()

    }



}
