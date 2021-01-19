package pl.paullettuce.android_astarium_interview_app.di.presentation

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.StationsDistanceActivity
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.StationsDistanceContract
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.StationsDistancePresenter

@Module
@InstallIn(ActivityComponent::class)
object StationsDistanceModule {

    @Provides
    fun provideStationsDistanceActivity(
        activity: Activity
    ): StationsDistanceActivity = activity as StationsDistanceActivity

    @Provides
    fun provideStationsDistanceView(
        activity: StationsDistanceActivity
    ): StationsDistanceContract.View = activity

    @Provides
    fun provideStationsDistancePresenter(
        presenter: StationsDistancePresenter
    ): StationsDistanceContract.Presenter = presenter
}