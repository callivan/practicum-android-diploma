package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavotiteBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModule
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.ui.vacancy.FragmentVacancy

class FragmentFavotite : Fragment() {
    private var _binding: FragmentFavotiteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModule>()
    private var adapter: FavoriteVacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavotiteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()
        setupRecyclerView()
        viewModel.getFavoriteVacancies()

    }

    private fun setupToolbar() {
        with(binding.topBarItem) {
            btnFirst.isVisible = false
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = getString(R.string.page_favorite)
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoriteVacancyAdapter(emptyList(), object : FavoriteVacancyAdapter.OnVacancyClickListener {
            override fun onClick(vacancy: VacancyDetails) {
                findNavController().navigate(
                    R.id.action_fragmentFavotite_to_fragmentVacancy,
                    Bundle().apply {
                        putString(FragmentVacancy.ID_VACANCY, vacancy.id)
                    }
                )
            }
        })
        binding.favouriteList.layoutManager = LinearLayoutManager(requireContext())
        binding.favouriteList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> showLoading()
                is ScreenState.Success -> {
                    showContent(state.data)
                    binding.placeholderLayout.isVisible = true
                }
                is ScreenState.Empty -> showEmptyState()
                is ScreenState.NotFound -> showError(getString(R.string.err_empty_list))
                is ScreenState.NetworkError -> {
                    showError(getString(R.string.err_load_vacancy_list))
                    binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
                }
                is ScreenState.InternalServerError -> {
                    showError(getString(R.string.err_load_vacancy_list))
                    binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
                }
                is ScreenState.UnknownError -> {
                    showError(getString(R.string.err_load_vacancy_list))
                    binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
                }
                else -> {
                    showError(getString(R.string.err_load_vacancy_list))
                    binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            loader.isVisible = true
            placeholderText.isVisible = false
            // Скрываем RecyclerView, если он есть
        }
    }

    private fun showContent(vacancies: List<VacancyDetails>) {
        with(binding) {
            loader.isVisible = false
            favouriteList.isVisible = true
            placeholderLayout.isVisible = false
            placeholderImage.isVisible = false
            placeholderText.isVisible = false
            adapter?.updateList(vacancies)
        }
    }

    private fun showEmptyState() {
        with(binding) {
            loader.isVisible = false
            placeholderLayout.isVisible = true
            placeholderText.isVisible = true
            placeholderImage.isVisible = true
            placeholderText.text = getString(R.string.err_empty_list)
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            loader.isVisible = false
            placeholderLayout.isVisible = true
            placeholderText.isVisible = true
            placeholderText.text = errorMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
