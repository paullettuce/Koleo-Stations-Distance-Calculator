package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_station_info.view.*
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.extensions.inflate
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.StationsDistanceContract

class StationInfoListAdapter(
    private val interactor: StationsDistanceContract.StationInfoListInteractor
) : RecyclerView.Adapter<StationInfoViewHolder>() {
    private val items = mutableListOf<StationInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationInfoViewHolder {
        return StationInfoViewHolder(parent.inflate(R.layout.list_item_station_info), interactor)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StationInfoViewHolder, position: Int) {
         holder.bind(items[position])
    }

    fun setItems(newItems: List<StationInfo>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun isEmpty() = itemCount == 0
}

class StationInfoViewHolder(
    itemView: View,
    private val interactor: StationsDistanceContract.StationInfoListInteractor
) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: StationInfo) {
        itemView.title.text = item.name
        itemView.subtitle.text = item.regionInfo
        itemView.initials.text = item.initials
        itemView.setOnClickListener { interactor.onStationInfoListItemClick(item) }
    }
}