package ru.practicum.android.diploma.ui.filter.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.R.color.gray
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceBinding
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import kotlin.getValue

class FragmentFilterPlace : Fragment() {
    private var _binding: FragmentFilterPlaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        navFun()
        setCountryContent()
        setRegionContent()

        binding.country.itemIcon.setOnClickListener {
            viewModel.setArea(mutableListOf())
            setCountryContent()
        }

        val country = viewModel.getFilters()?.area?.get(0)

        binding.country.itemIcon.setOnClickListener {
            viewModel.setArea(if (country != null) mutableListOf(country) else mutableListOf())
            setRegionContent()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setupView() {
        binding.topBar.btnFirst.isVisible = true
        binding.topBar.btnSecond.isVisible = false
        binding.topBar.btnThird.isVisible = false
        binding.topBar.header.text = "Выбор места работы"

        binding.country.itemText.setTextColor(ContextCompat.getColor(requireContext(), gray))
        binding.country.itemText.text = "Страна"
        binding.country.itemTextTop.isVisible = false
        binding.country.itemIcon.setImageResource(R.drawable.arrow_forward_24px)

        binding.region.itemText.setTextColor(ContextCompat.getColor(requireContext(), gray))
        binding.region.itemText.text = "Регион"
        binding.region.itemTextTop.isVisible = false
        binding.region.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
    }

    private fun setCountryContent() {
        val filters = viewModel.getFilters()
        val country = if (filters?.area?.isNotEmpty() == true) filters.area?.get(0) else null

        if (country == null) {
            return
        }

        binding.country.itemTextTop.isVisible = true
        binding.country.itemIcon.setImageResource(R.drawable.close_24px)
        binding.country.itemTextTop.text = "Страна"
        binding.country.itemText.text = country.name
    }

    private fun setRegionContent() {
        val filters = viewModel.getFilters()
        val region = filters?.area?.size?.let { if (it > 1) filters.area?.get(1) else null }

        if (region == null) {
            return
        }

        binding.region.itemTextTop.isVisible = true
        binding.region.itemIcon.setImageResource(R.drawable.close_24px)
        binding.region.itemTextTop.text = "Регион"
        binding.region.itemText.text = region.name
    }

    private fun navFun() {
        binding.regionLayout.setOnClickListener {
            val selectedCountry = viewModel.getFilters()?.area?.get(0)

            if (selectedCountry != null) {
                findNavController().navigate(
                    R.id.action_fragmentFilterPlace_to_fragmentRegion,
                    Bundle().apply {
                        putString(COUNTRY_ID, selectedCountry.id)
                    }
                )
            }
        }
        binding.countryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilterPlace_to_fragmentCountry)
        }
        binding.topBar.btnFirst.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val COUNTRY_ID = "COUNTRY_ID"
    }
}
