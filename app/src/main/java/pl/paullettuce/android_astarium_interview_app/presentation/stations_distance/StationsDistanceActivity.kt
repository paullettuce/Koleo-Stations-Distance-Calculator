package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import pl.paullettuce.android_astarium_interview_app.R
import javax.inject.Inject

@AndroidEntryPoint
class StationsDistanceActivity : AppCompatActivity(), StationsDistanceContract.View {

    @Inject
    lateinit var presenter: StationsDistanceContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.initialize()
    }

    override fun showLoading() {
        Toast.makeText(this, "Loading data", Toast.LENGTH_SHORT).show()
    }

    override fun showNoConnectionError() {
        Toast.makeText(this, "NO INTERNET", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}