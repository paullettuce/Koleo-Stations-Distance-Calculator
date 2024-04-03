package pl.paullettuce.android_astarium_interview_app.storage.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository

class SynchronizationInfoRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SynchronizationInfoRepository {
    private val KEY_LAST_SYNCH_TIMESTAMP = "last_synchronization_timestamp"
    private val CACHE_VALIDITY_AGE = 24 * 60 * 60 * 1000

    override fun saveSynchronizationTimestampNow() {
        sharedPreferences.edit(commit = true) { putLong(KEY_LAST_SYNCH_TIMESTAMP, now()) }
    }

    override fun isSynchronizationNeeded(): Boolean {
        return true
        val lastSynchronizationTimestamp = getLastSynchronizationTimestamp()
        if (lastSynchronizationTimestamp <= 0) return true
        return now() - lastSynchronizationTimestamp >= CACHE_VALIDITY_AGE
    }

    private fun getLastSynchronizationTimestamp(): Long {
        return sharedPreferences.getLong(KEY_LAST_SYNCH_TIMESTAMP, 0)
    }

    private fun now() = System.currentTimeMillis()
}