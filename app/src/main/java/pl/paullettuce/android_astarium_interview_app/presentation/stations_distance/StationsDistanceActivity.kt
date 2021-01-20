package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
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

    private fun setupRecyclerView() {
        stationsInfoRecView.layoutManager = LinearLayoutManager(this)
        stationsInfoRecView.adapter = stationInfoListAdapter
        stationsInfoRecView.addItemDecoration(
            RecyclerViewMargin(verticalMarginDp = R.dimen.recycler_view_item_margin)
        )
    }
}
