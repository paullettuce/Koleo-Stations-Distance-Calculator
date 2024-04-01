package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance


import org.junit.Test
import org.mockito.Mockito
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo

class StationsPickingStateTest {

    @Test
    fun testNoStationSelected() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val state = NoStationSelected(view)
        Mockito.verify(view).noStationsSelected()
    }

    @Test
    fun testOneStationSelected() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val stationInfo = Mockito.mock(StationInfo::class.java)
        val state = OneStationSelected(stationInfo, view)
        Mockito.verify(view).oneStationSelected(stationInfo)
    }

    @Test
    fun testTwoStationsSelected() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val stationInfo1 = Mockito.mock(StationInfo::class.java)
        val stationInfo2 = Mockito.mock(StationInfo::class.java)
        val state = TwoStationsSelected(stationInfo1, stationInfo2, view)
        Mockito.verify(view).twoStationsSelected(stationInfo1, stationInfo2)
    }

    @Test
    fun testDeleteOneStation() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val stationInfo = Mockito.mock(StationInfo::class.java)
        val state = OneStationSelected(stationInfo, view)

        state.deleteItemFromBottomSheet(stationInfo)

        Mockito.verify(view).noStationsSelected()
    }

    @Test
    fun testDeleteAllStations() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val stationInfo1 = Mockito.mock(StationInfo::class.java)
        val stationInfo2 = Mockito.mock(StationInfo::class.java)
        val state = TwoStationsSelected(stationInfo1, stationInfo2, view)

        state.deleteAllItemsFromBottomSheet()

        Mockito.verify(view).noStationsSelected()
    }

    @Test
    fun testUnselectStationInTwoStationsSelectedState() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val stationInfo1 = Mockito.mock(StationInfo::class.java)
        val stationInfo2 = Mockito.mock(StationInfo::class.java)
        val state = TwoStationsSelected(stationInfo1, stationInfo2, view)

        state.onStationInfoClick(stationInfo1)

        Mockito.verify(view).oneStationSelected(stationInfo2)
    }

    @Test
    fun testUnselectStationInOneStationSelectedState() {
        val view = Mockito.mock(StationsDistanceContract.View::class.java)
        val stationInfo1 = Mockito.mock(StationInfo::class.java)
        val state = OneStationSelected(stationInfo1, view)

        state.onStationInfoClick(stationInfo1)

        Mockito.verify(view).noStationsSelected()
    }

}
