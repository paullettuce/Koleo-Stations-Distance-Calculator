package pl.paullettuce.android_astarium_interview_app.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.Normalizer

@Entity(tableName = "station_data_entity")
data class StationDataEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(collate = ColumnInfo.LOCALIZED)
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Long,
    val city: String,
    val region: String,
    val country: String
) {
    @ColumnInfo(name = "normalized_name")
    var normalizedName: String = name
}