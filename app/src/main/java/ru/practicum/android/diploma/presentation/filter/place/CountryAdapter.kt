package ru.practicum.android.diploma.presentation.filter.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemFilterListBinding
import ru.practicum.android.diploma.domain.models.Area

class CountryAdapter(
    private var countries: List<Area> = listOf(),
    private val onItemClick: (Area) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(private val binding: ItemFilterListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Area) {
            binding.itemTextTop.isVisible = false
            binding.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            binding.itemText.text = country.name
            binding.root.setOnClickListener {
                onItemClick(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemFilterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    fun updateList(newList: List<Area>) {
        countries = newList
        notifyDataSetChanged()
    }
}
