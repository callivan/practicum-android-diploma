package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.ui.root.RootActivity

class FragmentFilter : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.filtersConcatenation()
    }

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

        setPlaceContent()
        setIndustryContent()
        setSalaryContent()
        switchButtonsVisibility()

        binding.includedTopBar.btnFirst.setOnClickListener {
            closeFragment()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment()
        }

        binding.includedBtnSet.root.setOnClickListener {
            viewModel.setApply(true)
            closeFragment()
        }

        binding.includedBtnCancel.root.setOnClickListener {
            viewModel.cleanFilters()
            setPlaceContent()
            setIndustryContent()
            setSalaryContent()
            switchButtonsVisibility()
        }

        binding.includedShowNoSalary.itemIcon.setOnClickListener {
            viewModel.setOnlyWithSalary()
            setSalaryContent()
        }

        binding.includedSalary.textFieldEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // none
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (p0.toString().isNotEmpty()) {
                    true -> {
                        binding.includedSalary.apply {
                            textFieldHeader.text = requireContext().getString(R.string.filter_main_salary)
                            textFieldClear.isVisible = true
                        }
                    }

                    false -> {
                        binding.includedSalary.apply {
                            textFieldHeader.text = ""
                            textFieldClear.isVisible = false
                        }
                    }
                }

                viewModel.setSalary(if (p0.toString().isNotEmpty()) p0.toString().toInt() else null)
                switchButtonsVisibility()
            }

            override fun afterTextChanged(p0: Editable?) {
                // none
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setPlaceContent() {
        switchButtonsVisibility()
        val placeList = viewModel.getFilters()?.area
        val place = placeList?.joinToString(", ") { it.name }
        val isPlaceVisible = place != null && placeList.isNotEmpty()

        binding.includedPlace.apply {
            itemTextTop.isVisible = isPlaceVisible
            itemText.text = if (isPlaceVisible) place else getString(R.string.filter_main_place)
            itemIcon.setImageResource(if (isPlaceVisible) R.drawable.close_24px else R.drawable.arrow_forward_24px)
            itemIcon.setOnClickListener {
                viewModel.setArea(null)
                setPlaceContent()
            }
        }
    }

    private fun setIndustryContent() {
        switchButtonsVisibility()
        val industries = viewModel.getFilters()?.industry
        val industry = industries?.joinToString(", ") { it.name }
        val isIndustryVisible = industry != null && industries.isNotEmpty()

        binding.includedIndustry.apply {
            itemTextTop.isVisible = isIndustryVisible
            itemText.text = if (isIndustryVisible) industry else getString(R.string.filter_main_industry)
            itemIcon.setImageResource(if (isIndustryVisible) R.drawable.close_24px else R.drawable.arrow_forward_24px)
            itemIcon.setOnClickListener {
                viewModel.setIndustry(null)
                setIndustryContent()
            }
        }
    }

    private fun setSalaryContent() {
        switchButtonsVisibility()

        val filters = viewModel.getFilters()

        binding.includedSalary.apply {
            if (filters?.salary != null && filters.salary != 0) {
                textFieldClear.isVisible = true
                textFieldEdit.setText(filters.salary.toString())
            } else {
                textFieldClear.isVisible = false
                textFieldEdit.setText("")
            }

            textFieldClear.setOnClickListener {
                textFieldHeader.text = ""
                textFieldClear.isVisible = false
                textFieldEdit.setText("")
                viewModel.setSalary(null)
            }
        }

        val isChecked = filters != null && filters.onlyWithSalary

        binding.includedShowNoSalary.itemIcon.setImageResource(
            if (isChecked) R.drawable.check_box_on__24px else R.drawable.check_box_off__24px
        )
    }

    private fun setListeners() {
        binding.includedPlace.itemIcon.setOnClickListener {
            if (binding.includedPlace.itemText.text.isNotEmpty()) {
                binding.includedPlace.itemTextTop.text = ""
                binding.includedPlace.itemText.text = ""
                binding.includedPlace.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            }
        }
        binding.includedIndustry.itemIcon.setOnClickListener {
            if (binding.includedIndustry.itemText.text.isNotEmpty()) {
                binding.includedIndustry.itemTextTop.text = ""
                binding.includedIndustry.itemText.text = ""
                binding.includedIndustry.itemIcon.setImageResource(R.drawable.arrow_forward_24px)
            }
        }
        binding.includedPlace.root.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilter_to_fragmentFilterPlace)
        }
        binding.includedIndustry.root.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilter_to_fragmentFilterIndustry)
        }
        binding.includedSalary.apply {
            textFieldEdit.setOnFocusChangeListener { _, hasFocus ->
                when (hasFocus) {
                    true -> binding.includedSalary.textFieldHeader.setTextColor(requireContext().getColor(R.color.blue))

                    false -> binding.includedSalary.textFieldHeader.setTextColor(
                        requireContext().getColor(R.color.black)
                    )
                }
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
            textFieldEdit.imeOptions = EditorInfo.IME_ACTION_DONE
            textFieldEdit.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus()
                    val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    true
                } else {
                    false
                }
            }
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

    private fun switchButtonsVisibility() {
        val filters = viewModel.getFilters()
        var state = false
        val isSalary = filters?.salary != null
        val isOnlyWithSalary = filters?.onlyWithSalary == true
        val isArea = filters?.area?.isNotEmpty() == true || filters?.area != null
        val isIndustry = filters?.industry?.isNotEmpty() == true || filters?.industry != null

        state = isSalary || isOnlyWithSalary || isArea || isIndustry

        binding.includedBtnSet.root.isVisible = state
        binding.includedBtnCancel.root.isVisible = state
    }
}
