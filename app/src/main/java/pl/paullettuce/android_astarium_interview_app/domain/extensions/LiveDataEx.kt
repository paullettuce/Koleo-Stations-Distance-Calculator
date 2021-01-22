package pl.paullettuce.android_astarium_interview_app.domain.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(transformation: (T?) -> R): LiveData<R> {
    return Transformations.map(this, transformation)
}

fun <T, R> LiveData<T>.mapNotNull(transformation: (T) -> R): LiveData<R> {
    return Transformations.map(this, transformation)
}

fun <T, R> LiveData<T>.switchMap(transformation: (T) -> LiveData<R>): LiveData<R> {
    return Transformations.switchMap(this, transformation)
}