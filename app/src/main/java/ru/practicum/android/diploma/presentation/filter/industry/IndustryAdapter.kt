package ru.practicum.android.diploma.presentation.filter.industry

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.FilterIndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(
    private val prevSelectedIndustry: Industry? = null,
    private val onItemClick: (Industry?) -> Unit
) : ListAdapter<Industry, IndustryAdapter.IndustryViewHolder>(DiffCallback()) {

    private var selectedIndustry: Industry? = prevSelectedIndustry

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = FilterIndustryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = getItem(position)
        holder.bind(industry, industry.id == selectedIndustry?.id)
    }

    inner class IndustryViewHolder(private val binding: FilterIndustryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(industry: Industry, isSelected: Boolean) {
            binding.radioButton.isChecked = isSelected
            binding.industryName.text = industry.name

            binding.root.setOnClickListener {
                if (selectedIndustry?.id == industry.id) {
                    selectedIndustry = null
                    notifyDataSetChanged()
                    onItemClick(null)
                } else {
                    selectedIndustry = industry
                    notifyDataSetChanged()
                    onItemClick(industry)
                }
            }

            binding.radioButton.setOnClickListener {
                if (selectedIndustry?.id == industry.id) {
                    selectedIndustry = null
                    notifyDataSetChanged()
                    onItemClick(null)
                } else {
                    selectedIndustry = industry
                    notifyDataSetChanged()
                    onItemClick(industry)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Industry>() {
        override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean =
            oldItem == newItem
    }
}
