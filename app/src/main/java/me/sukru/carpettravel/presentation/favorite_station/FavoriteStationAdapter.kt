package me.sukru.carpettravel.presentation.favorite_station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.sukru.carpettravel.R
import me.sukru.carpettravel.databinding.RowFavoriteSpaceStationBinding
import me.sukru.carpettravel.domain.model.Station

class FavoriteStationAdapter(
    private val onFavoriteClick: (Station) -> Unit,
): ListAdapter<Station, FavoriteStationAdapter.StationViewHolder>(StationDiffCallbackUtil()) {

    inner class StationViewHolder(private val binding: RowFavoriteSpaceStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Station) {
            binding.stationName.text = item.name
            binding.eus.text = "EUS: ${firstTwoDecimals(item.eus)}"
            binding.stationStock.text = "${item.capacity}/${item.stock}"
            binding.favoriteButton.setImageResource(if (item.isFavorite) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_border_24)
            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(item)
            }
        }
    }

    fun firstTwoDecimals(value: Double): String {
        return String.format("%.2f", value)
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
        val binding = RowFavoriteSpaceStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}