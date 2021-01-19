package pl.paullettuce.android_astarium_interview_app.domain.extensions

import timber.log.Timber

fun Any.logd(message: String, vararg args: Any) = Timber.d(message, args)
fun Any.loge(t: Throwable) = Timber.e(t)