package pl.paullettuce.android_astarium_interview_app.domain.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(transformation: (T?) -> R): LiveData<R> {
    return Transformations.map(this, transformation)
}

fun <T, R> LiveData<T>.mapNotNull(transformation: (T) -> R): LiveData<R> {
    return Transformations.map(this, transformation)
}