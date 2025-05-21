package ru.practicum.android.diploma.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyShort

class VacancyAdapter(
    private var vacancies: List<VacancyShort>,
    private val listener: OnVacancyClickListener
) : RecyclerView.Adapter<VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacansy, parent, false)
        return VacancyViewHolder(view, listener)
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    fun updateList(newList: List<VacancyShort>) {
        val diffCallback = VacancyDiffCallback(vacancies, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        vacancies = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnVacancyClickListener {
        fun onClick(vacancy: VacancyShort)
    }

    private class VacancyDiffCallback(
        private val oldList: List<VacancyShort>,
        private val newList: List<VacancyShort>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
