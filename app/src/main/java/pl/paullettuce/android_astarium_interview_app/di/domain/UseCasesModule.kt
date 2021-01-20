package pl.paullettuce.android_astarium_interview_app.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.*
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCaseImpl
import pl.paullettuce.android_astarium_interview_app.storage.preferences.Preferences

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
    fun provideGetStationsUseCase(
        stationsRepository: StationsRepository
    ): GetStationsUseCase = GetStationsUseCaseImpl(stationsRepository)

    @Provides
    fun provideSaveStationsUseCase(
        stationsRepository: StationsRepository
    ): SaveStationsUseCase = SaveStationsUseCaseImpl(stationsRepository)

    @Provides
    fun provideSynchronizeStationsUseCaseImpl(
        preferences: Preferences,
        downloadStationsUseCase: DownloadStationsUseCase,
        saveStationsUseCase: SaveStationsUseCase
    ): SynchronizeStationsUseCase = SynchronizeStationsUseCaseImpl(
        preferences, downloadStationsUseCase, saveStationsUseCase
    )
}