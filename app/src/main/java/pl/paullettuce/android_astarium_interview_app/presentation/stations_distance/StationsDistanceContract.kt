package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

interface StationsDistanceContract {

    interface View {
        fun setStationsPickingState(stationsPickingState: StationsPickingState)
        fun noStationsSelected()
        fun oneStationSelected(stationInfo: StationInfo)
        fun twoStationsSelected(station1: StationInfo, station2: StationInfo)
        fun showDistance(distanceValue: Int)

        fun showLoading(show: Boolean)
        fun showNoConnectionError()
        fun showMessage(@StringRes stringRes: Int)
    }

    interface Presenter {
        val filteredStationsLiveData: LiveData<List<StationInfo>>
        fun initialize()
        fun dispose()
        fun calculateDistance(item1: StationInfo, item2: StationInfo)
        fun filterStationsByQuery(query: String)
    }

    interface StationInfoListInteractor {
        fun onStationInfoListItemClick(item: StationInfo)
    }
}