package pl.paullettuce.android_astarium_interview_app.storage.model

import androidx.room.Entity

@Entity(tableName = "autofill_hint")
data class AutofillHint(
    val id: Long,
    val keyword: String,
    val stationId: Long
)