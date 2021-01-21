package pl.paullettuce.android_astarium_interview_app.presentation.bottom_sheet

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.DeleteFromBottomSheetListener

class DistanceBottomSheet(
    private val mainView: ViewGroup,
    private val deleteFromBottomSheetListener: DeleteFromBottomSheetListener
) {
    private val bottomSheet = BottomSheet(mainView)
    private val station1Name: TextView = mainView.findViewById(R.id.station1Name)
    private val station2Name: TextView = mainView.findViewById(R.id.station2Name)
    private val delete1: View = mainView.findViewById(R.id.delete1)
    private val delete2: View = mainView.findViewById(R.id.delete2)
    private val distance: TextView = mainView.findViewById(R.id.distance)

    fun noStationsSelected() {
        delete1.hide()
        delete2.hide()
        distance.hide()

        station1Name.text = ""
        station2Name.text = ""
    }

    fun oneStationSelected(stationInfo: StationInfo) {
        distance.hide()
        delete1.show()
        delete2.hide()
        delete1.setOnClickListener { deleteFromBottomSheetListener.deleteItemFromBottomSheet(stationInfo) }

        station1Name.text = stationInfo.name
        station2Name.text = ""
    }

    fun twoStationsSelected(station1: StationInfo, station2: StationInfo) {
        delete1.show()
        delete2.show()
        delete1.setOnClickListener { deleteFromBottomSheetListener.deleteItemFromBottomSheet(station1) }
        delete2.setOnClickListener { deleteFromBottomSheetListener.deleteItemFromBottomSheet(station2) }

        station1Name.text = station1.name
        station2Name.text = station2.name
    }

    fun showDistance(distanceValue: Int) {
        distance.show()
        val kilometers = mainView.context.getString(R.string.kms, distanceValue)
        distance.text = kilometers
    }

    fun isOpened(): Boolean = bottomSheet.isOpened()

    fun hideAndClear() {
        deleteFromBottomSheetListener.deleteAllItemsFromBottomSheet()
        bottomSheet.hide()
    }

    fun open() {
        bottomSheet.open()
    }

    private fun View.show() {
        isVisible = true
    }

    private fun View.hide() {
        isVisible = false
    }
}