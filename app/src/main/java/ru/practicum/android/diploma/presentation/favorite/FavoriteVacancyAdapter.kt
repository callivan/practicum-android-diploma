package ru.practicum.android.diploma.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.favorite.FavoriteVacancyViewHolder

class FavoriteVacancyAdapter(
    private var vacancies: List<VacancyDetails>,
    private val listener: OnVacancyClickListener
) : RecyclerView.Adapter<FavoriteVacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacansy, parent, false)
        return FavoriteVacancyViewHolder(view, listener)
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: FavoriteVacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    fun updateList(newList: List<VacancyDetails>) {
        val diffCallback = FavoriteVacancyDiffCallback(vacancies, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        vacancies = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnVacancyClickListener {
        fun onClick(vacancy: VacancyDetails)
    }

    private class FavoriteVacancyDiffCallback(
        private val oldList: List<VacancyDetails>,
        private val newList: List<VacancyDetails>
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
