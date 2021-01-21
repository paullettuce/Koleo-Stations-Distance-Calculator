package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.distance_bottom_sheet.*
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.presentation.bottom_sheet.DistanceBottomSheet
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.list.RecyclerViewMargin
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.list.StationInfoListAdapter
import javax.inject.Inject

@AndroidEntryPoint
class StationsDistanceActivity : AppCompatActivity(),
    StationsDistanceContract.View, StationsDistanceContract.StationInfoListInteractor,
    DeleteFromBottomSheetListener {

    @Inject
    lateinit var presenter: StationsDistanceContract.Presenter

    @Inject
    lateinit var stationInfoListAdapter: StationInfoListAdapter

    private lateinit var stationsPickingState: StationsPickingState
    private lateinit var bottomSheet: DistanceBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupRecyclerView()
        setListeners()
        observeForData()

        presenter.initialize()
    }

    override fun onStationInfoListItemClick(item: StationInfo) =
        stationsPickingState.onStationInfoClick(item)

    override fun noStationsSelected() {
        bottomSheet.noStationsSelected()
    }

    override fun oneStationSelected(stationInfo: StationInfo) {
        openBottomSheet()
        bottomSheet.oneStationSelected(stationInfo)
    }

    override fun twoStationsSelected(station1: StationInfo, station2: StationInfo) {
        bottomSheet.twoStationsSelected(station1, station2)
        presenter.calculateDistance(station1, station2)
    }

    override fun showDistance(distanceValue: Int) {
        bottomSheet.showDistance(distanceValue)
    }

    override fun deleteItemFromBottomSheet(item: StationInfo) {
        stationsPickingState.deleteItemFromBottomSheet(item)
    }

    override fun deleteAllItemsFromBottomSheet() {
        stationsPickingState.deleteAllItemsFromBottomSheet()
    }

    override fun setStationsPickingState(stationsPickingState: StationsPickingState) {
        this.stationsPickingState = stationsPickingState
    }

    override fun showLoading() {
        Toast.makeText(this, "Loading data", Toast.LENGTH_SHORT).show()
    }

    override fun showNoConnectionError() {
        Toast.makeText(this, "NO INTERNET", Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(stringRes: Int) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (bottomSheet.isOpened()) {
            closeBottomSheet()
        } else {
            super.onBackPressed()
        }
    }

    private fun observeForData() {
        presenter.stationsInfoObservableData().observe(this, Observer {
            stationInfoListAdapter.setItems(it)
        })
    }

    private fun setupViews() {
        bottomSheet = DistanceBottomSheet(distanceBottomSheetView, this)
        stationsPickingState = NoStationSelected(this)
    }

    private fun setListeners() {
        openDistanceSheetFAB.setOnClickListener {
            if (bottomSheet.isOpened()) {
                closeBottomSheet()
            } else {
                openBottomSheet()
            }
        }
    }

    private fun closeBottomSheet() {
        bottomSheet.hideAndClear()
        openDistanceSheetFAB.setImageResource(R.drawable.ic_distance)
    }

    private fun openBottomSheet() {
        bottomSheet.open()
        openDistanceSheetFAB.setImageResource(R.drawable.ic_cancel)
    }

    private fun setupRecyclerView() {
        stationsInfoRecView.layoutManager = LinearLayoutManager(this)
        stationsInfoRecView.adapter = stationInfoListAdapter
        stationsInfoRecView.addItemDecoration(
            RecyclerViewMargin(verticalMarginDp = R.dimen.recycler_view_item_margin)
        )
    }
}
