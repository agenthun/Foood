package com.agenthun.foood.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View

/**
 * @project Beauty
 * @authors agenthun
 * @date 2017/3/18 11:19.
 */

fun View.scaleShow(listener: ViewPropertyAnimatorListener?) {
    this.visibility = View.VISIBLE
    ViewCompat.animate(this)
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1.0f)
            .setInterpolator(LinearOutSlowInInterpolator())
            .setListener(listener)
            .start()
}

fun View.scaleHide(listener: ViewPropertyAnimatorListener?) {
    ViewCompat.animate(this)
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0.0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .setListener(listener)
            .setStartDelay(100)
            .start()
}

fun View.doHeartBeat(duration: Long = 1000) {
    val set = AnimatorSet()
    set.playTogether(
            ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 1.2f, 0.9f, 1.0f),
            ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 1.2f, 0.9f, 1.0f)
    )
    set.duration = duration
    set.start()
}