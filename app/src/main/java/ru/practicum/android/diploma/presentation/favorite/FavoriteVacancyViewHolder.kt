package ru.practicum.android.diploma.presentation.favorite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.ui.favorite.FavoriteVacancyAdapter
import ru.practicum.android.diploma.util.DpToPx

class FavoriteVacancyViewHolder(
    private val view: View,
    private val listener: FavoriteVacancyAdapter.OnVacancyClickListener
) : RecyclerView.ViewHolder(view) {
    private val logoView: ImageView = view.findViewById(R.id.item_logo)
    private val headerView: TextView = view.findViewById(R.id.item_text_header)
    private val employerView: TextView = view.findViewById(R.id.item_text_employer)
    private val salaryView: TextView = view.findViewById(R.id.item_text_salary)

    fun bind(model: VacancyDetails) {
        Glide.with(view.context)
            .load(model.logo)
            .placeholder(R.drawable.placeholder_32px)
            .override(DpToPx.dpToPx(100F, view.context), DpToPx.dpToPx(100F, view.context))
            .transform(RoundedCorners(DpToPx.dpToPx(12F, view.context)))
            .into(logoView)

        headerView.text = model.name
        employerView.text = model.employer

        model.salaryRangeFrom?.let { from ->
            val salaryText = buildString {
                append(from)
                model.salaryRangeTo?.let { to -> append(" - $to") }
                model.salaryRangeCurrency?.let { currency -> append(" $currency") }
            }
            salaryView.text = salaryText
            salaryView.visibility = View.VISIBLE
        } ?: run {
            salaryView.visibility = View.GONE
        }

        view.setOnClickListener {
            listener.onClick(model)
        }
    }
}
