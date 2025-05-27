package ru.practicum.android.diploma.ui.filter.place.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.presentation.filter.place.FilterRegionViewModel
import ru.practicum.android.diploma.presentation.filter.place.RegionAdapter
import ru.practicum.android.diploma.presentation.models.ScreenState

class FragmentRegion : Fragment() {
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterRegionViewModel>()
    private var adapter = RegionAdapter { selectedRegion ->
        // Обработка выбора региона
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupRecyclerView()
        observeViewModel()
        setupSearch()
        navFun()

        val countryId = arguments?.getString("countryId") ?: return
        viewModel.getCountryRegions(countryId)
    }

    private fun setupView() {
        binding.topBar.btnSecond.isVisible = false
        binding.topBar.btnThird.isVisible = false
        binding.topBar.header.text = "Выбор региона"

        binding.editText.editTextSearch.hint = "Введите регион"
    }

    private fun setupRecyclerView() {
        binding.regionList.layoutManager = LinearLayoutManager(requireContext())
        binding.regionList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    // Показать лоадер, если необходимо
                }
                is ScreenState.Success -> {
                    adapter.updateList(state.data.areas)
                }
                is ScreenState.Empty -> {
                    adapter.updateList(emptyList())
                }
                else -> {
                    // Обработка ошибок или Init
                }
            }
        }
    }

    private fun setupSearch() {
        binding.editText.editTextSearch.addTextChangedListener(viewModel.getTextWatcher())
    }

    private fun navFun() {
        binding.topBar.btnFirst.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
