package pl.paullettuce.android_astarium_interview_app.di.storage

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoListMapper
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.network.ApiService
import pl.paullettuce.android_astarium_interview_app.storage.repository.StationsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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
}