package pl.paullettuce.android_astarium_interview_app.domain.repository

interface SynchronizationInfoRepository {
    fun saveSynchronizationTimestampNow()
    fun isSynchronizationNeeded(): Boolean
}