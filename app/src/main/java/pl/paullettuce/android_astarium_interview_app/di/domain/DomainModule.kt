package pl.paullettuce.android_astarium_interview_app.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.*
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.DownloadStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.DownloadStationsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Provides
    fun provideStationEntityToStationInfoMapper() = StationEntityToStationInfoMapper()

    @Provides
    fun provideStationEntityToStationInfoListMapper(
        itemMapper: StationEntityToStationInfoMapper
    ) = StationEntityToStationInfoListMapper(itemMapper)

    @Provides
    fun provideStationEntityToPointF() = StationEntityToPointFMapper()

    @Provides
    fun provideAddNormalizedNameColumnMapper() = AddNormalizedNameColumnMapper()

    @Provides
    fun provideAddNormalizedNameColumnListMapper(
        itemMapper: AddNormalizedNameColumnMapper
    ): AddNormalizedNameColumnListMapper = AddNormalizedNameColumnListMapper(itemMapper)
}