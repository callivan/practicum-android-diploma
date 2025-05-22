package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.util.isConnected

class FragmentVacancy : Fragment() {

    companion object {
        const val ID_VACANCY = "id_vacancy"
    }

    val viewModel by viewModel<VacancyViewModel>()

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
        //TODO
    }

    private fun showLoading() {
        //TODO
    }

    private fun showContent(vacancy: VacancyDetails) {
        binding.vacancyName.text = vacancy.name
    }

    private fun onDeleteFromFavorite() {
        //TODO
    }

    private fun onInsertFromFavorite() {
        //TODO
    }
}
