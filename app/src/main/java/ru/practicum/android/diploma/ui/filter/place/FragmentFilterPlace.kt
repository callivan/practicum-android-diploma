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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

        binding.buttonBlue.buttonBlue.text = "Выбрать"
    }

    private fun setCountryContent() {
        val filters = viewModel.getFilters()
        val country = if (filters?.area?.isNotEmpty() == true) filters.area[0] else null
        val region = if (filters?.area?.isNotEmpty() == true && filters.area.size > 1) filters.area[1] else null

        binding.country.itemTextTop.isVisible = country != null
        binding.country.itemIcon.setImageResource(
            if (country == null) R.drawable.arrow_forward_24px else R.drawable.close_24px
        )

        binding.country.itemText.setTextAppearance(
            if (country != null) R.style.colorPlaceFilter else R.style.colorPlaceFilterDefault
        )

        binding.country.itemTextTop.text = "Страна"
        binding.country.itemText.text = if (country == null) "Страна" else country.name
        binding.buttonBlueLayout.isVisible = country != null || region != null

        binding.country.itemIcon.setOnClickListener {
            viewModel.setArea(null)
            setCountryContent()
            setRegionContent()
        }
    }

    private fun setRegionContent() {
        val filters = viewModel.getFilters()
        val country = if (filters?.area?.isNotEmpty() == true) filters.area[0] else null
        val region = if (filters?.area?.isNotEmpty() == true && filters.area.size > 1) filters.area[1] else null

        binding.region.itemTextTop.isVisible = region != null
        binding.region.itemIcon.setImageResource(
            if (region == null) R.drawable.arrow_forward_24px else R.drawable.close_24px
        )

        binding.region.itemText.setTextAppearance(
            if (region != null) R.style.colorPlaceFilter else R.style.colorPlaceFilterDefault
        )

        binding.region.itemTextTop.text = "Регион"
        binding.region.itemText.text = if (region == null) "Регион" else region.name
        binding.buttonBlueLayout.isVisible = country != null || region != null

        binding.region.itemIcon.setOnClickListener {
            val country = if (filters?.area?.isNotEmpty() == true) filters.area[0] else null

            viewModel.setArea(if (country != null) mutableListOf(country) else null)
            setRegionContent()
        }
    }

    private fun navFun() {
        binding.regionLayout.setOnClickListener {
            val selectedCountry = viewModel.getFilters()?.area?.get(0)

            findNavController().navigate(
                R.id.action_fragmentFilterPlace_to_fragmentRegion, Bundle().apply {
                    putString(COUNTRY_ID, selectedCountry?.id)
                })
        }
        binding.countryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilterPlace_to_fragmentCountry)
        }
        binding.topBar.btnFirst.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonBlue.buttonBlue.setOnClickListener {
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
