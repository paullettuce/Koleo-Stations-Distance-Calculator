package pl.paullettuce.android_astarium_interview_app.domain.extensions

import timber.log.Timber

fun Any.logd(message: String, vararg args: Any) = logtagged().d("$message, args=$args")
fun Any.loge(t: Throwable) = logtagged().e(t)
fun Any.loge(message: String) = logtagged().e(message)

private fun Any.logtagged() = Timber.tag(this::class.simpleName)
