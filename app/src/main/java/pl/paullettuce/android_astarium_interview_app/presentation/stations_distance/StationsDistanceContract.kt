package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

interface StationsDistanceContract {

    interface View {
        fun showLoading()
        fun showNoConnectionError()
        fun showMessage(message: String)
        fun showMessage(@StringRes stringRes: Int)
    }

    interface Presenter {
        fun initialize()
        fun dispose()
        fun stationsInfoObservableData(): LiveData<List<StationInfo>>
    }

    interface StationInfoListInteractor {
        fun onStationInfoListItemClick(item: StationInfo)
    }
}