package me.sukru.carpettravel.presentation.favorite_station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.extensions.setImageResourceIf
import me.sukru.carpettravel.databinding.RowFavoriteSpaceStationBinding
import me.sukru.carpettravel.domain.model.Station

class FavoriteStationAdapter(
    private val onFavoriteClick: (Station) -> Unit,
): ListAdapter<Station, FavoriteStationAdapter.StationViewHolder>(StationDiffCallbackUtil()) {

    inner class StationViewHolder(private val binding: RowFavoriteSpaceStationBinding) :
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
        val binding = RowFavoriteSpaceStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}