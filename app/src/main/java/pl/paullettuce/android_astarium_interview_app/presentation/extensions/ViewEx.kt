package pl.paullettuce.android_astarium_interview_app.presentation.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}