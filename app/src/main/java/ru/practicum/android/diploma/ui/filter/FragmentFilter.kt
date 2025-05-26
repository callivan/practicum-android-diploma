package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.ui.root.RootActivity

class FragmentFilter : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
            itemText.text = requireContext().getString(R.string.filter_main_place)
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
        }

        binding.includedIndustry.apply {
            itemTextTop.isVisible = false
            itemText.text = requireContext().getString(R.string.filter_main_industry)
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
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
        }
    }

    private fun closeFragment() {
        (activity as RootActivity).switchNavBarVisibility()
        findNavController().popBackStack()
    }
}
