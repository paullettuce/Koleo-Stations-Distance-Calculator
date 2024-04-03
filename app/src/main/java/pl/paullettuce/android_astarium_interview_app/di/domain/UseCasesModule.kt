package pl.paullettuce.android_astarium_interview_app.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToPointMapper
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.*
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations_keywords.DownloadAndSaveStationsKeywordsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations_keywords.DownloadAndSaveStationsKeywordsUseCaseImpl
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun provideDownloadStationsUseCase(
        stationsRepository: StationsRepository
    ): DownloadAndSaveStationsUseCase {
        return DownloadAndSaveStationsUseCaseImpl(stationsRepository)
    }

    @Provides
    @Singleton
    fun provideSynchronizeStationsUseCaseImpl(
        synchronizationRepository: SynchronizationInfoRepository,
        downloadAndSaveStationsUseCase: DownloadAndSaveStationsUseCase,
        downloadAndSaveStationsKeywordsUseCase: DownloadAndSaveStationsKeywordsUseCase
    ): SynchronizeStationsUseCase = SynchronizeStationsUseCaseImpl(
        synchronizationRepository,
        downloadAndSaveStationsUseCase,
        downloadAndSaveStationsKeywordsUseCase
    )

    @Provides
    @Singleton
    fun provideDownloadAndSaveStationsKeywordsUseCase(
        downloadAndSaveStationsKeywordsUseCaseImpl: DownloadAndSaveStationsKeywordsUseCaseImpl
    ): DownloadAndSaveStationsKeywordsUseCase = downloadAndSaveStationsKeywordsUseCaseImpl

    @Provides
    @Singleton
    fun provideCalculateDistanceUseCase(
        repository: StationsRepository,
        mapper: StationEntityToPointMapper
    ): CalculateDistanceUseCase = CalculateDistanceUseCaseImpl(repository, mapper)

    @Provides
    @Singleton
    fun provideSearchStationsUseCase(
        repository: StationsRepository
    ): FilterStationsUseCase = FilterStationsUseCaseImpl(repository)
}