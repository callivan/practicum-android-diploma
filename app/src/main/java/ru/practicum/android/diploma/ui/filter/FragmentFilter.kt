package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.SelectedFilters
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.ui.root.RootActivity

class FragmentFilter : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        fillView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment()
        }

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Success -> {
                    setContent(state.data)
                }
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setContent(content: SelectedFilters) {
        binding.includedPlace.apply {
            itemTextTop.isVisible = true
            itemText.text = content.place
        }
        binding.includedIndustry.apply {
            itemTextTop.isVisible = true
            itemText.text = content.industry
        }
        binding.includedSalary.textFieldEdit.setText(content.salary.toString())
        binding.includedShowNoSalary.itemIcon.apply {
            if (content.showNoSalary) {
                setImageResource(R.drawable.check_box_on__24px)
            } else {
                setImageResource(R.drawable.check_box_off__24px)
            }
        }
    }

    private fun fillView() {
        binding.includedTopBar.apply {
            btnFirst.setOnClickListener {
                closeFragment()
            }
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.filter_main_header)
        }

        binding.includedPlace.apply {
            itemTextTop.isVisible = false
            itemTextTop.text = requireContext().getString(R.string.filter_main_place)
            itemText.text = requireContext().getString(R.string.filter_main_place)
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            itemIcon.setOnClickListener {
                (activity as RootActivity).switchNavBarVisibility()
                findNavController().navigate(R.id.action_fragmentFilter_to_fragmentFilterPlace)
            }
        }

        binding.includedIndustry.apply {
            itemTextTop.isVisible = false
            itemTextTop.text = requireContext().getString(R.string.filter_main_industry)
            itemText.text = requireContext().getString(R.string.filter_main_industry)
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            itemIcon.setOnClickListener {
                (activity as RootActivity).switchNavBarVisibility()
                findNavController().navigate(R.id.action_fragmentFilter_to_fragmentFilterIndustry)
            }
        }

        binding.includedSalary.apply {
            textFieldClear.isVisible = false
            textFieldHeader.text = requireContext().getString(R.string.filter_main_salary)
            textFieldEdit.hint = requireContext().getString(R.string.filter_main_salary_hint)
        }

        binding.includedShowNoSalary.apply {
            itemTextTop.isVisible = false
            itemText.text = requireContext().getString(R.string.filter_main_show_no_salary)
            itemIcon.setImageResource(R.drawable.check_box_off__24px)
            itemIcon.setOnClickListener {
                viewModel.onClickShowNoSalary()
            }
        }

        binding.includedBtnSet.root.setOnClickListener {
            viewModel.setFilters()
            closeFragment()
        }

        binding.includedBtnSet.root.setOnClickListener {
            viewModel.clearFilters()
            closeFragment()
        }
    }

    private fun closeFragment() {
        (activity as RootActivity).switchNavBarVisibility()
        findNavController().popBackStack()
    }
}
