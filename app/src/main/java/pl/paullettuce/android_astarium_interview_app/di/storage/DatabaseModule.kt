package pl.paullettuce.android_astarium_interview_app.di.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.database.AppDatabase
import pl.paullettuce.android_astarium_interview_app.storage.preferences.Preferences
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePreferences(): Preferences {
        return object : Preferences {
            override fun lastSynchronizationTimestamp(): Long {
                TODO("Not yet implemented")
            }

            override fun saveSynchronizationTimestamp(timestamp: Long) {
                TODO("Not yet implemented")
            }
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "stations_distance_app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideStationDataDao(
        appDatabase: AppDatabase
    ): StationDataDao = appDatabase.stationDataDao()
}