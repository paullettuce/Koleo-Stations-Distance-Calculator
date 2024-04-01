package pl.paullettuce.android_astarium_interview_app.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.paullettuce.android_astarium_interview_app.domain.mappers.AddNormalizedNameColumnListMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.AddNormalizedNameColumnMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToPointMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoListMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoMapper

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideStationEntityToStationInfoMapper() = StationEntityToStationInfoMapper()

    @Provides
    fun provideStationEntityToStationInfoListMapper(
        itemMapper: StationEntityToStationInfoMapper
    ) = StationEntityToStationInfoListMapper(itemMapper)

    @Provides
    fun provideStationEntityToPoint() = StationEntityToPointMapper()

    @Provides
    fun provideAddNormalizedNameColumnMapper() = AddNormalizedNameColumnMapper()

    @Provides
    fun provideAddNormalizedNameColumnListMapper(
        itemMapper: AddNormalizedNameColumnMapper
    ): AddNormalizedNameColumnListMapper = AddNormalizedNameColumnListMapper(itemMapper)
}