package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo

interface StateControl {
    fun changeStateTo(newState: StationsPickingState)
}

interface StationsPickingState {
    fun onStationInfoClick(item: StationInfo,
                           stateControl: StateControl)
}

class NoStationSelected(
) : StationsPickingState {
    override fun onStationInfoClick(item: StationInfo, stateControl: StateControl) {
        stateControl.changeStateTo(OneStationSelected(item))
    }
}

class OneStationSelected(
    val selectedStation: StationInfo
) : StationsPickingState {
    override fun onStationInfoClick(item: StationInfo, stateControl: StateControl) {
        if (selectedStation == item) {
            stateControl.changeStateTo(NoStationSelected())
        } else {
            stateControl.changeStateTo(TwoStationsSelected(selectedStation, item))
        }
    }
}

class TwoStationsSelected(
    val station1: StationInfo,
    val station2: StationInfo
) : StationsPickingState {
    override fun onStationInfoClick(item: StationInfo, stateControl: StateControl) {
        when (item) {
            station1 -> {
                stateControl.changeStateTo(OneStationSelected(station2))
            }
            station2 -> {
                stateControl.changeStateTo(OneStationSelected(station1))
            }
        }
    }
}