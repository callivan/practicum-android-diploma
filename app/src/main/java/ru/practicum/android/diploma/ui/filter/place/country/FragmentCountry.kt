package ru.practicum.android.diploma.ui.filter.place.country

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.presentation.filter.place.CountryAdapter
import ru.practicum.android.diploma.presentation.filter.place.FilterCountryViewModel
import ru.practicum.android.diploma.presentation.filter.place.FilterPlaceViewModel
import ru.practicum.android.diploma.presentation.models.ScreenState

class FragmentCountry : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private var adapter = CountryAdapter { selectedCountry ->
        sharedViewModel.selectedCountry.value = selectedCountry
        findNavController().popBackStack()
    }
    private val viewModel by viewModel<FilterCountryViewModel>()
    private val sharedViewModel by activityViewModels<FilterPlaceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTopBar()
        navFun()
        setupRecyclerView()
        observeViewModel()

        viewModel.getCountries()
    }

    private fun setupTopBar() {
        binding.topBar.btnSecond.isVisible = false
        binding.topBar.btnThird.isVisible = false
        binding.topBar.header.text = "Выбор страны"
    }

    private fun navFun() {
        binding.topBar.btnFirst.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.countryList.layoutManager = LinearLayoutManager(requireContext())
        binding.countryList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Success -> {
                    adapter.updateList(state.data)
                    Log.d("FragmentCountry", "Loaded countries: ${state.data.size}")
                }
                is ScreenState.Empty -> {
                    Log.d("FragmentCountry", "Country list is empty")
                    // Обработка пустого состояния
                }
                is ScreenState.Loading -> {
                    // Показ лоадера
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
