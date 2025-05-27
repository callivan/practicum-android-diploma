package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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
        setListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includedTopBar.btnFirst.setOnClickListener {
            closeFragment()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment()
        }

        binding.includedBtnSet.root.setOnClickListener {
            viewModel.setFilters()
            closeFragment()
        }

        binding.includedBtnCancel.root.setOnClickListener {
            switchButtonsVisibility(false)
            viewModel.clearFilters()
        }

        binding.includedShowNoSalary.itemIcon.setOnClickListener {
            viewModel.onClickShowNoSalary()
        }

        binding.includedSalary.textFieldEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // none
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (p0.toString().isNotEmpty()) {
                    true -> {
                        binding.includedSalary.textFieldHeader.text =
                            requireContext().getString(R.string.filter_main_salary)
                    }
                    false -> {
                        binding.includedSalary.textFieldHeader.text = ""
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // none
            }

        })

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Success -> {
                    setContent(state.data)
                    binding.includedBtnSet.root.isVisible = true
                    binding.includedBtnCancel.root.isVisible = true
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
        switchButtonsVisibility(true)

        binding.includedPlace.apply {
            if (content.place.isNotEmpty()) {
                itemIcon.setImageResource(R.drawable.close_24px)
            } else {
                itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            }
            itemText.text = content.place
            itemTextTop.isVisible = content.place.isNotEmpty()
        }

        binding.includedIndustry.apply {
            if (content.industry.isNotEmpty()) {
                itemIcon.setImageResource(R.drawable.close_24px)
            } else {
                itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            }
            itemText.text = content.industry
            itemTextTop.isVisible = content.industry.isNotEmpty()
        }

        binding.includedSalary.apply {
            if (content.salary == null) {
                textFieldHeader.text = ""
                textFieldClear.isVisible = false
                textFieldEdit.setText("")
            } else {
                textFieldHeader.text = requireContext().getString(R.string.filter_main_salary)
                textFieldClear.isVisible = true
                textFieldEdit.setText(content.salary.toString())
            }
        }

        binding.includedShowNoSalary.itemIcon.apply {
            if (content.showNoSalary) {
                setImageResource(R.drawable.check_box_on__24px)
            } else {
                setImageResource(R.drawable.check_box_off__24px)
            }
        }
    }

    private fun setListeners() {
        binding.includedPlace.itemIcon.setOnClickListener {
            if (binding.includedPlace.itemText.text.isNotEmpty()) {
                binding.includedPlace.itemTextTop.text = ""
                binding.includedPlace.itemText.text = ""
                binding.includedPlace.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            } else {
                (activity as RootActivity).switchNavBarVisibility()
                findNavController().navigate(R.id.action_fragmentFilter_to_fragmentFilterPlace)
            }
        }
        binding.includedIndustry.itemIcon.setOnClickListener {
            if (binding.includedIndustry.itemText.text.isNotEmpty()) {
                binding.includedIndustry.itemTextTop.text = ""
                binding.includedIndustry.itemText.text = ""
                binding.includedIndustry.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            } else {
                (activity as RootActivity).switchNavBarVisibility()
                findNavController().navigate(R.id.action_fragmentFilter_to_fragmentFilterIndustry)
            }
        }
        binding.includedSalary.apply {
            textFieldEdit.setOnFocusChangeListener { _, hasFocus ->
                when (hasFocus) {
                    true -> binding.includedSalary.textFieldHeader
                        .setTextColor(requireContext().getColor(R.color.blue))
                    false -> binding.includedSalary.textFieldHeader
                        .setTextColor(requireContext().getColor(R.color.black))
                }
            }
            textFieldClear.setOnClickListener {
                textFieldHeader.text = ""
                textFieldClear.isVisible = false
                textFieldEdit.setText("")
            }
        }
    }

    private fun fillView() {
        binding.includedTopBar.apply {
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.filter_main_header)
        }

        binding.includedPlace.apply {
            itemTextTop.isVisible = false
            itemTextTop.text = requireContext().getString(R.string.filter_main_place)
            itemText.hint = requireContext().getString(R.string.filter_main_place)
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
        }

        binding.includedIndustry.apply {
            itemTextTop.isVisible = false
            itemTextTop.text = requireContext().getString(R.string.filter_main_industry)
            itemText.hint = requireContext().getString(R.string.filter_main_industry)
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
        }

        binding.includedSalary.apply {
            textFieldClear.isVisible = false
            textFieldHeader.hint = requireContext().getString(R.string.filter_main_salary)
            textFieldEdit.hint = requireContext().getString(R.string.filter_main_salary_hint)
            textFieldEdit.inputType = InputType.TYPE_CLASS_NUMBER
        }

        binding.includedShowNoSalary.apply {
            itemTextTop.isVisible = false
            itemText.text = requireContext().getString(R.string.filter_main_show_no_salary)
            itemIcon.setImageResource(R.drawable.check_box_off__24px)
        }
    }

    private fun closeFragment() {
        (activity as RootActivity).switchNavBarVisibility()
        findNavController().popBackStack()
    }

    private fun switchButtonsVisibility(visible: Boolean) {
        binding.includedBtnSet.root.isVisible = visible
        binding.includedBtnCancel.root.isVisible = visible
    }
}
