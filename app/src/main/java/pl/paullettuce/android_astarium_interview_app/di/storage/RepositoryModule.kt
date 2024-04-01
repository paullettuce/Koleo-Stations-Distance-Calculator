package pl.paullettuce.android_astarium_interview_app.di.storage

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoListMapper
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.network.ApiService
import pl.paullettuce.android_astarium_interview_app.storage.repository.StationsRepositoryImpl
import pl.paullettuce.android_astarium_interview_app.storage.repository.SynchronizationInfoRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStationsRepository(
        apiService: ApiService,
        stationDataDao: StationDataDao,
        mapper: StationEntityToStationInfoListMapper
    ): StationsRepository {
        return StationsRepositoryImpl(apiService, stationDataDao, mapper)
    }

    @Provides
    @Singleton
    fun provideSynchronizationInfoRepository(
        sharedPreferences: SharedPreferences
    ): SynchronizationInfoRepository = SynchronizationInfoRepositoryImpl(sharedPreferences)
}