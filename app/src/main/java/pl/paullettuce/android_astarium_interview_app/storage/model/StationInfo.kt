package pl.paullettuce.android_astarium_interview_app.storage.model

import androidx.room.Entity

@Entity(tableName = "station_info")
data class StationInfo(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Long,
    val city: String,
    val region: String,
    val country: String
)