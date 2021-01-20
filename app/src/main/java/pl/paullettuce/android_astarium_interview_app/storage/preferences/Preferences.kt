package pl.paullettuce.android_astarium_interview_app.storage.preferences

interface Preferences {
    fun lastSynchronizationTimestamp(): Long
    fun saveSynchronizationTimestamp(timestamp: Long)
}