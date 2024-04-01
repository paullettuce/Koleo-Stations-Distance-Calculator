package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.paullettuce.android_astarium_interview_app.databinding.ListItemStationInfoBinding
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.presentation.stations_distance.StationsDistanceContract

class StationInfoListAdapter(
    private val interactor: StationsDistanceContract.StationInfoListInteractor
) : RecyclerView.Adapter<StationInfoViewHolder>() {
    private val items = mutableListOf<StationInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationInfoViewHolder {
        val binding =
            ListItemStationInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationInfoViewHolder(binding, interactor)
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
    private val binding: ListItemStationInfoBinding,
    private val interactor: StationsDistanceContract.StationInfoListInteractor
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: StationInfo) {
        binding.title.text = item.name
        binding.subtitle.text = item.regionInfo
        binding.initials.text = item.initials
        binding.root.setOnClickListener { interactor.onStationInfoListItemClick(item) }
    }
}