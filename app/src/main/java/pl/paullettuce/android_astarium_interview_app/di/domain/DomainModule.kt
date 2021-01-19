package pl.paullettuce.android_astarium_interview_app.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.GetStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.GetStationsUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Provides
    fun provideGetStationsUseCase(
        stationsRepository: StationsRepository
    ): GetStationsUseCase {
        return GetStationsUseCaseImpl(stationsRepository)
    }
}