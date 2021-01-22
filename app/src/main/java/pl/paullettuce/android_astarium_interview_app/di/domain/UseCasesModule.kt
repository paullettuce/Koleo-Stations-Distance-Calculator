package pl.paullettuce.android_astarium_interview_app.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.AddNormalizedNameColumnListMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToPointFMapper
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.*
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object UseCasesModule {
    @Provides
    fun provideDownloadStationsUseCase(
        stationsRepository: StationsRepository
    ): DownloadStationsUseCase {
        return DownloadStationsUseCaseImpl(stationsRepository)
    }

    @Provides
    fun provideSaveStationsUseCase(
        stationsRepository: StationsRepository
    ): SaveStationsUseCase = SaveStationsUseCaseImpl(stationsRepository)

    @Provides
    fun provideSynchronizeStationsUseCaseImpl(
        synchronizationRepository: SynchronizationInfoRepository,
        downloadStationsUseCase: DownloadStationsUseCase,
        saveStationsUseCase: SaveStationsUseCase,
        mapper: AddNormalizedNameColumnListMapper
    ): SynchronizeStationsUseCase = SynchronizeStationsUseCaseImpl(
        synchronizationRepository, downloadStationsUseCase, saveStationsUseCase, mapper
    )

    @Provides
    fun provideCalculateDistanceUseCase(
        repository: StationsRepository,
        mapper: StationEntityToPointFMapper
    ): CalculateDistanceUseCase = CalculateDistanceUseCaseImpl(repository, mapper)

    @Provides
    fun provideSearchStationsUseCase(
        repository: StationsRepository
    ): FilterStationsUseCase = FilterStationsUseCaseImpl(repository)
}