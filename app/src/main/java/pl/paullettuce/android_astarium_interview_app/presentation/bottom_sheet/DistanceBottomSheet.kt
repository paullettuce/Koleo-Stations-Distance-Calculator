package pl.paullettuce.android_astarium_interview_app.presentation.bottom_sheet

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo

class DistanceBottomSheet(
    private val mainView: ViewGroup
) {
    private val bottomSheet = BottomSheet(mainView)
    private val station1Name: TextView = mainView.findViewById(R.id.station1Name)
    private val station2Name: TextView = mainView.findViewById(R.id.station2Name)
    private val distance: TextView = mainView.findViewById(R.id.distance)

    fun noStationsSelected() {
        station1Name.text = ""
        station1Name.setHint(R.string.pick_station)
        station2Name.text = ""
        station2Name.setHint(R.string.pick_station)
        distance.isVisible = false
    }

    fun oneStationSelected(stationInfo: StationInfo) {
        bottomSheet.open()
        station1Name.text = stationInfo.name
        station2Name.text = ""
        station2Name.setHint(R.string.pick_station)
        distance.isVisible = false
    }

    fun twoStationsSelected(station1: StationInfo, station2: StationInfo) {
        station1Name.text = station1.name
        station2Name.text = station2.name
    }

    fun showDistance(distanceValue: Int) {
        distance.isVisible = true
        val kilometers = mainView.context.getString(R.string.kms, distanceValue)
        distance.text = kilometers
    }

    fun toggle() {
        bottomSheet.toggle()
    }
}