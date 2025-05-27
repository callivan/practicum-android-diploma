package ru.practicum.android.diploma.presentation.filter.place

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemFilterListBinding
import ru.practicum.android.diploma.domain.models.Area

class RegionAdapter(
    private var regions: List<Area> = listOf(),
    private val onItemClick: (Area) -> Unit
) : RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {

    inner class RegionViewHolder(private val binding: ItemFilterListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(region: Area) {
            binding.itemTextTop.isVisible = false
            binding.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            binding.itemText.text = region.name
            binding.root.setOnClickListener {
                onItemClick(region)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = ItemFilterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.bind(regions[position])
    }

    override fun getItemCount(): Int = regions.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Area>) {
        regions = newList
        notifyDataSetChanged()
    }
}
