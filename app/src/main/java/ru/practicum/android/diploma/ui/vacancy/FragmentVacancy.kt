package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.util.isConnected
import java.util.Currency

class FragmentVacancy : Fragment() {

    companion object {
        const val ID_VACANCY = "id_vacancy"
        const val IMG_CORNER = 12
    }

    val viewModel by viewModel<VacancyViewModel>()

    private var currentVacancy: VacancyDetails? = null
    private var isFavorite: Boolean = false // Временно

    private var _binding: FragmentVacancyBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includedTopBar.header.text = requireContext().getString(R.string.vacancy_screen_header)
        binding.includedTopBar.btnSecond.setImageResource(R.drawable.sharing_24px)
        binding.includedTopBar.btnThird.setImageResource(R.drawable.favorites_off__24px)

        binding.includedErr.placeholderImage.setImageResource(R.drawable.err_no_vacancy)
        binding.includedErr.placeholderText.text = requireContext().getString(R.string.err_no_vacancy)

        binding.includedTopBar.btnFirst.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding.includedTopBar.btnSecond.setOnClickListener {
            // Скоро будет
        }

        binding.includedTopBar.btnThird.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteFromFavoriteById(currentVacancy!!.id)
                isFavorite = false
            } else {
                viewModel.insertInFavorite(currentVacancy!!)
                isFavorite = true
            }
        }

        viewModel.getVacancyById(arguments?.getString(ID_VACANCY)!!, isConnected(requireContext()))

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                ScreenState.Empty -> { showError() }
                ScreenState.Loading -> { showLoading() }
                is ScreenState.Success -> { showContent(state.data) }
                is ScreenState.Delete -> { onDeleteFromFavorite() }
                is ScreenState.Insert -> { onInsertFromFavorite() }
                else -> Unit
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showError() {
        binding.includedProgressBar.root.isVisible = false
        binding.contentView.isVisible = false
        binding.includedErr.root.isVisible = true
    }

    private fun showLoading() {
        binding.includedProgressBar.root.isVisible = true
        binding.contentView.isVisible = false
        binding.includedErr.root.isVisible = false
    }

    private fun showContent(vacancy: VacancyDetails) {
        binding.includedProgressBar.root.isVisible = false
        binding.contentView.isVisible = true
        binding.includedErr.root.isVisible = false
        currentVacancy = vacancy

        binding.vacancyName.text = vacancy.name
        var salaryString = ""
        if (vacancy.salaryRangeFrom != null) {
            salaryString += "от ${vacancy.salaryRangeFrom} "
        }
        if (vacancy.salaryRangeTo != null) {
            salaryString += "до ${vacancy.salaryRangeTo} "
        }
        salaryString += getCurrencySymbol(vacancy.salaryRangeCurrency!!)
        binding.vacancySalary.text = salaryString
        binding.includedVacancyCard.titleVacancyCard.text = vacancy.employer
        binding.includedVacancyCard.cityVacancyCard.text = vacancy.address?.split(',')?.get(0) ?: ""
        Glide.with(this)
            .load(vacancy.logo)
            .centerCrop()
            .placeholder(R.drawable.placeholder_32px)
            .transform(RoundedCorners(IMG_CORNER))
            .into(binding.includedVacancyCard.imageVacancyCard)
        binding.valueExp.text = vacancy.experience
        binding.valueWorkFormat.text = vacancy.workFormat?.joinToString(", ") ?: ""
        binding.valueDescription.text = HtmlCompat.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        if (vacancy.keySkills != null) {
            binding.valueSkills.text = vacancy.keySkills.joinToString("\n") { "• $it" }
            binding.headerSkills.isVisible = vacancy.keySkills.isNotEmpty()
            binding.valueSkills.isVisible = true
        }
    }

    fun getCurrencySymbol(currencyCode: String): String {
        return try {
            val currency = Currency.getInstance(currencyCode)
            currency.symbol
        } catch (e: IllegalArgumentException) {
            Log.e("currencyCode", "Invalid code")
            currencyCode
        }
    }

    private fun onDeleteFromFavorite() {
        binding.includedTopBar.btnThird.setImageResource(R.drawable.favorites_off__24px)
    }

    private fun onInsertFromFavorite() {
        binding.includedTopBar.btnThird.setImageResource(R.drawable.favorites_on__24px)
    }
}
