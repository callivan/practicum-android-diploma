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
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.place.RegionAdapter
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.ui.filter.place.FragmentFilterPlace

class FragmentRegion : Fragment() {

    companion object {
        private const val PLACEHOLDER_EMPTY = "empty"
        private const val PLACEHOLDER_ERR = "error"
    }

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterViewModel>()
    private var adapter = RegionAdapter { selectedRegion ->
        val area = viewModel.getFilters()?.area
        val updatedArea = mutableListOf<Area>()

        if (area != null) {
            updatedArea.addAll(area)
        }

        if (updatedArea.size > 1) {
            updatedArea.removeLastOrNull()
        }

        updatedArea.add(selectedRegion)

        viewModel.setArea(updatedArea)
        findNavController().popBackStack()
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

        val countryId = arguments?.getString(FragmentFilterPlace.COUNTRY_ID) ?: return
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
        viewModel.getRegionsScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    showContent()
                    binding.loaderWrapper.searchProgressBar.isVisible = true
                }

                is ScreenState.Success -> {
                    showContent()
                    binding.loaderWrapper.searchProgressBar.isVisible = false
                    adapter.updateList(state.data.areas)
                }

                is ScreenState.Empty -> {
                    showPlaceholder(PLACEHOLDER_EMPTY)
                    adapter.updateList(emptyList())
                }

                else -> {
                    binding.loaderWrapper.searchProgressBar.isVisible = false
                    showPlaceholder(PLACEHOLDER_ERR)
                }
            }
        }
    }

    private fun showContent() {
        binding.includedErrEmpty.root.isVisible = false
        binding.regionList.isVisible = true
    }

    private fun showPlaceholder(type: String) {
        binding.regionList.isVisible = false
        binding.includedErrEmpty.apply {
            when (type) {
                PLACEHOLDER_ERR -> {
                    placeholderImage.setImageResource(R.drawable.err_load_list)
                    placeholderText.text = requireContext().getString(R.string.err_load_list)
                }
                PLACEHOLDER_EMPTY -> {
                    placeholderImage.setImageResource(R.drawable.err_wtf_cat)
                    placeholderText.text = requireContext().getString(R.string.err_cant_find_region)
                }
            }
            root.isVisible = true
        }
    }

    private fun setupSearch() {
        binding.editText.editTextSearch.addTextChangedListener(viewModel.getRegionsTextWatcher())
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
