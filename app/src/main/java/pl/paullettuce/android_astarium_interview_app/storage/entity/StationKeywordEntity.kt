package pl.paullettuce.android_astarium_interview_app.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station_keyword_entity")
data class StationKeywordEntity(
    @PrimaryKey val id: Long,
    val keyword: String,
    val stationId: Long
)