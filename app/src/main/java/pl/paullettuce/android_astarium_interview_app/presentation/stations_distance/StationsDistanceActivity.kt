package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.distance_bottom_sheet.*
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.list.RecyclerViewMargin
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.list.StationInfoListAdapter
import javax.inject.Inject

@AndroidEntryPoint
class StationsDistanceActivity : AppCompatActivity(),
    StationsDistanceContract.View, StationsDistanceContract.StationInfoListInteractor {

    @Inject
    lateinit var presenter: StationsDistanceContract.Presenter

    @Inject
    lateinit var stationInfoListAdapter: StationInfoListAdapter

    lateinit var bottomSheet: BottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomSheet = BottomSheet(distanceBottomSheet)
        setupRecyclerView()
        setListeners()
        observeForData()

        presenter.initialize()
    }

    override fun onStationInfoListItemClick(item: StationInfo) {
        TODO("Not yet implemented")
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

    private fun observeForData() {
        presenter.stationsInfoObservableData().observe(this, Observer {
            stationInfoListAdapter.setItems(it)
        })
    }

    private fun setListeners() {
        openDistanceSheetFAB.setOnClickListener {
            bottomSheet.toggle()
        }
    }

    private fun setupRecyclerView() {
        stationsInfoRecView.layoutManager = LinearLayoutManager(this)
        stationsInfoRecView.adapter = stationInfoListAdapter
        stationsInfoRecView.addItemDecoration(
            RecyclerViewMargin(verticalMarginDp = R.dimen.recycler_view_item_margin)
        )
    }
}

class BottomSheet(view: View) {
    val behavior = BottomSheetBehavior.from(view)

    init {
        behavior.isDraggable = false
        hide()
    }

    fun toggle() {
        if (isHidden()) open()
        else hide()
    }

    fun open() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hide() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun isHidden() = behavior.state == BottomSheetBehavior.STATE_HIDDEN
    private fun isOpened() = behavior.state == BottomSheetBehavior.STATE_EXPANDED

}
