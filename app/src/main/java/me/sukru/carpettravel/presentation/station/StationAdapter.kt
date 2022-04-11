package me.sukru.carpettravel.presentation.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.extensions.nDecimal
import me.sukru.carpettravel.databinding.RowSpaceStationBinding
import me.sukru.carpettravel.domain.model.Station


class StationAdapter(
    private val onTravelClick: (Station) -> Unit,
    private val onFavoriteClick: (Station) -> Unit,
): ListAdapter<Station, StationAdapter.StationViewHolder>(StationDiffCallbackUtil()) {

    inner class StationViewHolder(private val binding: RowSpaceStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Station) {
            binding.stationName.text = item.name
            binding.eus.text = "EUS: ${(item.eus).nDecimal(2)}"
            binding.stationStock.text = "${item.capacity}/${item.stock}"
            binding.favoriteButton.setImageResourceIf(item.isFavorite,
                R.drawable.ic_baseline_star_24,
                R.drawable.ic_baseline_star_border_24
            )
            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(item)
            }
            binding.travel.backgroundTintList = ContextCompat
                .getColorStateList(binding.root.context, if(item.isVisited) R.color.gray else R.color.green_600)
            binding.travel.isEnabled = !item.isVisited
            if (item.isVisited) {
                binding.travel.setOnClickListener(null)
            } else {
                binding.travel.setOnClickListener {
                    onTravelClick(item)
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
        val binding = RowSpaceStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
