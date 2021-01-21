package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo

interface DeleteFromBottomSheetListener {
    fun deleteItemFromBottomSheet(item: StationInfo)
    fun deleteAllItemsFromBottomSheet()
}

abstract class StationsPickingState(
    private val view: StationsDistanceContract.View
) : DeleteFromBottomSheetListener {
    abstract fun onStationInfoClick(item: StationInfo)

    protected fun noStationsSelected() {
        view.setStationsPickingState(NoStationSelected(view))
    }

    protected fun oneStationSelected(item: StationInfo) {
        view.setStationsPickingState(OneStationSelected(item, view))
    }

    protected fun twoStationsSelected(item1: StationInfo, item2: StationInfo) {
        view.setStationsPickingState(TwoStationsSelected(item1, item2, view))
    }
}

class NoStationSelected(
    view: StationsDistanceContract.View
) : StationsPickingState(view) {
    init {
        view.noStationsSelected()
    }

    override fun onStationInfoClick(item: StationInfo) {
        oneStationSelected(item)
    }

    override fun deleteItemFromBottomSheet(item: StationInfo) { }
    override fun deleteAllItemsFromBottomSheet() { }
}

class OneStationSelected(
    private val selectedStation: StationInfo,
    view: StationsDistanceContract.View
) : StationsPickingState(view) {
    init {
        view.oneStationSelected(selectedStation)
    }

    override fun onStationInfoClick(item: StationInfo) {
        if (selectedStation == item) {
            noStationsSelected()
        } else {
            twoStationsSelected(selectedStation, item)
        }
    }

    override fun deleteItemFromBottomSheet(item: StationInfo) {
        noStationsSelected()
    }

    override fun deleteAllItemsFromBottomSheet() {
        noStationsSelected()
    }
}

class TwoStationsSelected(
    private val station1: StationInfo,
    private val station2: StationInfo,
    view: StationsDistanceContract.View
) : StationsPickingState(view) {
    init {
        view.twoStationsSelected(station1, station2)
    }

    override fun onStationInfoClick(item: StationInfo) {
        unselectStation(item)
    }

    override fun deleteItemFromBottomSheet(item: StationInfo) {
        unselectStation(item)
    }

    override fun deleteAllItemsFromBottomSheet() {
        noStationsSelected()
    }

    private fun unselectStation(item: StationInfo) {
        when (item) {
            station1 -> {
                oneStationSelected(station2)
            }
            station2 -> {
                oneStationSelected(station1)
            }
        }
    }
}