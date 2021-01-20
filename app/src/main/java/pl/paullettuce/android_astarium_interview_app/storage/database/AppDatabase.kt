package pl.paullettuce.android_astarium_interview_app.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.entity.AutofillHintEntity
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

@Database(entities = [StationDataEntity::class, AutofillHintEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDataDao(): StationDataDao
}