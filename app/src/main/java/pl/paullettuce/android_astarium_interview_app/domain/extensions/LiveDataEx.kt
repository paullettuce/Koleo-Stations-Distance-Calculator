package pl.paullettuce.android_astarium_interview_app.domain.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

fun <T, R> LiveData<T>.mapNotNull(transformation: (T) -> R): LiveData<R> {
    return map { transformation(it) }
}

fun <T, R> LiveData<T>.switchMap(transformation: (T) -> LiveData<R>): LiveData<R> {
    return switchMap { transformation(it) }
}