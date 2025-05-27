package ru.practicum.android.diploma.ui.filter.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.R.color.gray
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceBinding
import ru.practicum.android.diploma.presentation.filter.place.FilterPlaceViewModel

class FragmentFilterPlace : Fragment() {
    private var _binding: FragmentFilterPlaceBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by activityViewModels<FilterPlaceViewModel>()

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

        sharedViewModel.selectedCountry.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                binding.country.itemTextTop.isVisible = true
                binding.country.itemIcon.setImageResource(R.drawable.close_24px)
                binding.country.itemTextTop.text = "Страна"
                binding.country.itemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.country.itemText.text = country.name

                binding.country.itemIcon.setOnClickListener {
                    sharedViewModel.selectedCountry.value = null
                }
            } else {
                setupView()

                binding.country.itemIcon.setOnClickListener(null)
            }
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

    private fun navFun() {
        binding.regionLayout.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilterPlace_to_fragmentRegion)
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

}
