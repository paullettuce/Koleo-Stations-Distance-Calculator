package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import pl.paullettuce.android_astarium_interview_app.R

@AndroidEntryPoint
class StationsDistanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}