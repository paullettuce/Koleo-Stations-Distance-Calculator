package pl.paullettuce.android_astarium_interview_app.di.storage

import android.content.Context
import android.content.SharedPreferences
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
    fun providePreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences(
            "stations_distance_app_shared_prefs",
            Context.MODE_PRIVATE
        )
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