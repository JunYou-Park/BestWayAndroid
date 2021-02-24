package com.yawtseb.bestway.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View

class AnimationHelper {
    companion object{

        fun newPushUpAnimation(viewToPushUp: View, duration: Long) {
            val pushUp = ObjectAnimator.ofFloat(viewToPushUp, "translationY", 150f, 0f)
            pushUp.duration = duration.toLong()
            pushUp.start()
        }

        fun pushDownAnimation(viewToPushDown: View) {
            val pushDown = ObjectAnimator.ofFloat(viewToPushDown, "translationY", -100f, 0f)
            pushDown.duration = 300
            pushDown.start()
        }

        fun pushUpAnimation(viewToPushUp: View) {
            val pushUp = ObjectAnimator.ofFloat(viewToPushUp, "translationY", 100f, 0f)
            pushUp.duration = 150
            pushUp.start()
        }

        fun bottomPushUpAnimation(viewToPushUp: View) {
            val pushUp = ObjectAnimator.ofFloat(viewToPushUp, "translationY", 300f, 0f)
            pushUp.duration = 300
            pushUp.start()
        }

        fun bottomPushDownAnimation(viewToPushUp: View) {
            val pushUp = ObjectAnimator.ofFloat(viewToPushUp, "translationY", 0f, 300f)
            pushUp.duration = 300
            pushUp.start()
        }

        fun fadeOutAnimation(viewToFadeOut: View) {
            val fadeOut = ObjectAnimator.ofFloat(viewToFadeOut, "alpha", 1f, 0f)
            fadeOut.duration = 150
            fadeOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    // We wanna set the view to GONE, after it's fade out. so it actually disappear from the layout & don't take up space.
                    viewToFadeOut.visibility = View.GONE
                }
            })
            fadeOut.start()
        }


        fun fadeInAnimation(viewToFadeIn: View, duration: Long) {
            val fadeIn = ObjectAnimator.ofFloat(viewToFadeIn, "alpha", 0f, 1f)
            fadeIn.duration = duration
            fadeIn.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    // We wanna set the view to VISIBLE, but with alpha 0. So it appear invisible in the layout.
                    viewToFadeIn.visibility = View.VISIBLE
                    viewToFadeIn.alpha = 0f
                }
            })
            fadeIn.start()
        }
    }
}