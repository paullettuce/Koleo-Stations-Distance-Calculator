package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.lifecycle.LiveData
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo

interface StationsDistanceContract {

    interface View {
        fun showLoading()
        fun showNoConnectionError()
        fun showErrorMessage(message: String)
    }

    interface Presenter {
        val stationsLiveData: LiveData<List<StationInfo>>
        fun initialize()
        fun dispose()
    }

    interface StationInfoListInteractor {
        fun onStationInfoListItemClick(item: StationInfo)
    }
}