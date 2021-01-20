package pl.paullettuce.android_astarium_interview_app.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station_data_entity")
data class StationDataEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Long,
    val city: String,
    val region: String,
    val country: String
)