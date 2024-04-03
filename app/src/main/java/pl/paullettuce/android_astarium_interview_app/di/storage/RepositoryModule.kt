package pl.paullettuce.android_astarium_interview_app.di.storage

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.AddNormalizedNameColumnListMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoListMapper
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsKeywordsRepository
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.network.ApiService
import pl.paullettuce.android_astarium_interview_app.storage.repository.StationsKeywordsRepositoryImpl
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
        stationEntityToStationInfoListMapper: StationEntityToStationInfoListMapper,
        normalizedNameColumnListMapper: AddNormalizedNameColumnListMapper
    ): StationsRepository {
        return StationsRepositoryImpl(
            apiService,
            stationDataDao,
            stationEntityToStationInfoListMapper,
            normalizedNameColumnListMapper
        )
    }

    @Provides
    @Singleton
    fun bindStationsKeywordsRepository(
        stationsKeywordsRepositoryImpl: StationsKeywordsRepositoryImpl
    ): StationsKeywordsRepository = stationsKeywordsRepositoryImpl

    @Provides
    @Singleton
    fun provideSynchronizationInfoRepository(
        sharedPreferences: SharedPreferences
    ): SynchronizationInfoRepository = SynchronizationInfoRepositoryImpl(sharedPreferences)
}