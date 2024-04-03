package pl.paullettuce.android_astarium_interview_app.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationKeywordsDao
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationKeywordEntity

@Database(entities = [StationDataEntity::class, StationKeywordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDataDao(): StationDataDao
    abstract fun stationKeywordsDao(): StationKeywordsDao
}