package me.sukru.carpettravel.presentation.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.extensions.setBackgroundTintListIf
import me.sukru.carpettravel.common.extensions.setImageResourceIf
import me.sukru.carpettravel.databinding.RowSpaceStationBinding
import me.sukru.carpettravel.domain.model.Station


class StationAdapter(
    private val onTravelClick: (Station) -> Unit,
    private val onFavoriteClick: (Station) -> Unit,
) : ListAdapter<Station, StationAdapter.StationViewHolder>(StationDiffCallbackUtil()) {

    inner class StationViewHolder(private val binding: RowSpaceStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Station) {
            binding.apply {
                tvStationName.text = item.name
                tvEus.text = root.context.getString(R.string.eus_left, item.eus)
                tvStationStock.text =
                    root.context.getString(R.string.station_stock, item.capacity, item.stock)
                btnFavorite.setImageResourceIf(
                    item.isFavorite,
                    R.drawable.ic_baseline_star_24,
                    R.drawable.ic_baseline_star_border_24
                )
                btnFavorite.setOnClickListener {
                    onFavoriteClick(item)
                }
                btnTravel.setBackgroundTintListIf(
                    item.isVisited,
                    R.color.gray,
                    R.color.green_600
                )
                btnTravel.isEnabled = !item.isVisited
                if (item.isVisited) {
                    btnTravel.setOnClickListener(null)
                } else {
                    btnTravel.setOnClickListener {
                        onTravelClick(item)
                    }
                }
            }
        }
    }

    class StationDiffCallbackUtil : DiffUtil.ItemCallback<Station>() {

        override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding =
            RowSpaceStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
