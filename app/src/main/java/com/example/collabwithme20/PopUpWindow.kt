package com.example.collabwithme20

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_pop_up_window.*


class PopUpWindow : AppCompatActivity() {

    private var imageName = ""
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window)

        closeBtn.setOnClickListener {
            onBackPressed()
        }
        //Get data from FindPeopleActivity
        val bundle = intent.extras
        imageName = bundle?.getString("profileImage", "Image") ?: ""
        userName = bundle?.getString("username", "Name") ?: ""

        //Set data
        Glide.with(profileImageView).load(imageName).
            transform(CircleCrop()).into(profileImageView)
        usernameTextView.text = userName


        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            pop_up_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            pop_up_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }


        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

}
