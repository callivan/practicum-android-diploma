package ru.practicum.android.diploma.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.presentation.main.VacancyAdapter
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.ui.vacancy.FragmentVacancy

class FragmentMain : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var vacancyAdapter: VacancyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
        exit()

        binding.topBarItem.header.text = "Поиск вакансий"
        binding.topBarItem.btnFirst.isVisible = false
        binding.topBarItem.btnSecond.isVisible = false
        binding.topBarItem.btnThird.setImageResource(R.drawable.filter_off__24px)
    }

    private fun setupRecyclerView() {
        vacancyAdapter = VacancyAdapter(emptyList(), object : VacancyAdapter.OnVacancyClickListener {
            override fun onClick(vacancy: VacancyShort) {
                findNavController().navigate(
                    R.id.action_fragmentMain_to_fragmentVacancy,
                    Bundle().apply {
                        putString(FragmentVacancy.ID_VACANCY, vacancy.id)
                    }
                )
            }
        })

        binding.listVacancies.apply {
            adapter = vacancyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        binding.editTextInput.editTextSearch.apply  {
            addTextChangedListener(viewModel.getTextWatcher())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    binding.editTextInput.clearIcon.isVisible = !s.isNullOrEmpty()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    fun exit(){
        binding.editTextInput.clearIcon.setOnClickListener {
            binding.editTextInput.editTextSearch.text.clear()
            binding.listVacancies.isVisible = false
            binding.placeholderText.isVisible = false
            binding.button.isVisible = false
            binding.loader.isVisible = false
            binding.placeholderLayout.isVisible = true
            binding.placeholderImage.setImageResource(R.drawable.placeholder_search)
        }

    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: ScreenState<VacanciesResponse>) {
        when (state) {
            is ScreenState.Loading -> showLoading()
            is ScreenState.Success -> {
                if (state.data.items.isEmpty()) {
                    showEmptyState(getString(R.string.err_load_vacancy_list))
                    binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
                } else {
                    showResults(state.data)
                    binding.button.isVisible = true
                    binding.button.text = resources.getQuantityString(
                        R.plurals.vacancies_found,
                        state.data.found,
                        state.data.found
                    )
                }
            }
            is ScreenState.Empty -> {
                binding.placeholderImage.setImageResource(R.drawable.placeholder_search)
            }
            is ScreenState.NetworkError ->{
                showErrorState(getString(R.string.err_no_connection))
                binding.placeholderImage.setImageResource(R.drawable.err_no_connection)
            }
            is ScreenState.NotFound -> {
                showEmptyState(getString(R.string.err_load_vacancy_list))
                binding.button.text = "Таких вакансий нет"
                binding.button.isVisible = true
                binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
            }
            else -> {
                showEmptyState(getString(R.string.err_load_vacancy_list))
                binding.placeholderImage.setImageResource(R.drawable.err_wtf_cat)
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            listVacancies.isVisible = false
            loader.isVisible = true
            placeholderLayout.isVisible = false
            button.isVisible = false
        }
    }

    private fun showResults(data: VacanciesResponse) {
        binding.apply {
            listVacancies.isVisible = true
            loader.isVisible = false
            placeholderLayout.isVisible = false
        }
        viewModel.getScreenState()
        vacancyAdapter.updateList(data.items)
    }

    private fun showEmptyState(message: String) {
        binding.apply {
            listVacancies.isVisible = false
            loader.isVisible = false
            placeholderLayout.isVisible = true

            placeholderImage.setImageResource(R.drawable.placeholder_search)
            placeholderText.text = message
        }
    }

    private fun showErrorState(message: String) {
        binding.apply {
            listVacancies.isVisible = false
            loader.isVisible = false
            placeholderLayout.isVisible = true

            placeholderImage.setImageResource(R.drawable.placeholder_search)
            placeholderText.text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
