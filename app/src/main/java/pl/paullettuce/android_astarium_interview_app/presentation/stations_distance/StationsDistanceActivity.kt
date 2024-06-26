package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.databinding.ActivityMainBinding
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.presentation.bottom_sheet.DistanceBottomSheet
import pl.paullettuce.android_astarium_interview_app.presentation.extensions.hide
import pl.paullettuce.android_astarium_interview_app.presentation.extensions.hideKeyboard
import pl.paullettuce.android_astarium_interview_app.presentation.extensions.show
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

    private lateinit var binding: ActivityMainBinding

    private lateinit var stationsPickingState: StationsPickingState
    private lateinit var bottomSheet: DistanceBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViews()
        setupToolbar()
        setupRecyclerView()
        setListeners()
        observeForData()

        presenter.initialize()
    }

    override fun onStationInfoListItemClick(item: StationInfo) {
        hideKeyboard()
        stationsPickingState.onStationInfoClick(item)
    }

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

    override fun showLoading(show: Boolean) = with(binding.loading) {
        if (show) {
            show()
        } else {
            hide()
        }
    }

    override fun showNoConnectionError() {
        if (stationInfoListAdapter.isEmpty()) binding.retryButton.show()
        Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show()
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
        presenter.filteredStationsLiveData.observe(this, Observer {
            stationInfoListAdapter.setItems(it)
        })
    }

    private fun setupViews() {
        bottomSheet = DistanceBottomSheet(binding.distanceBottomSheet.distanceBottomSheetView, this)
        stationsPickingState = NoStationSelected(this)
    }

    private fun setListeners() {
        binding.openDistanceSheetFAB.setOnClickListener {
            if (bottomSheet.isOpened()) {
                closeBottomSheet()
            } else {
                openBottomSheet()
            }
        }
        binding.retryButton.setOnClickListener {
            it.hide()
            presenter.synchronizeData()
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText ?: return false
                handleSearchQuery(newText)
                return true
            }
        })
    }

    private fun handleSearchQuery(query: String) {
        binding.contentMain.stationsInfoRecView.scrollToPosition(0)
        presenter.filterStationsByQuery(query)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun openBottomSheet() {
        bottomSheet.open()
        binding.openDistanceSheetFAB.setImageResource(R.drawable.ic_cancel)
    }

    private fun closeBottomSheet() {
        bottomSheet.hideAndClear()
        binding.openDistanceSheetFAB.setImageResource(R.drawable.ic_distance)
    }

    private fun setupRecyclerView() = with(binding.contentMain.stationsInfoRecView) {
        layoutManager = LinearLayoutManager(this@StationsDistanceActivity)
        adapter = stationInfoListAdapter
        addItemDecoration(
            RecyclerViewMargin(verticalMarginDp = R.dimen.recycler_view_item_margin)
        )
    }
}
