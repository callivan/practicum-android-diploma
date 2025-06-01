package ru.practicum.android.diploma.ui.filter.industry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.industry.IndustryAdapter
import ru.practicum.android.diploma.presentation.models.ScreenState

class FragmentFilterIndustry : Fragment() {

    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterViewModel>()

    private val industryAdapter by lazy {
        val prevSelectedIndustry = viewModel.getFilters()?.industry?.get(0)

        IndustryAdapter(prevSelectedIndustry = prevSelectedIndustry) { selectedIndustry ->
            viewModel.setIndustry(selectedIndustry)
            binding.buttonBlueLayout.isVisible = selectedIndustry != null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
        setupView()
        exit()
    }

    private fun setupView() {
        with(binding) {
            topBar.btnFirst.isVisible = true
            topBar.btnSecond.isVisible = false
            topBar.btnThird.isVisible = false
            topBar.header.text = "Выбор отрасли"
            editText.editTextSearch.hint = "Введите отрасль"
            buttonBlue.buttonBlue.text = "Выбрать"
            viewModel.getIndustries()
        }
    }

    private fun exit() {
        binding.editText.clearIcon.setOnClickListener {
            binding.editText.editTextSearch.text.clear()
            viewModel.getIndustries()
        }

        binding.topBar.btnFirst.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonBlue.buttonBlue.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupRecyclerView() {
        binding.industryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = industryAdapter
        }
    }

    private fun setupSearch() {
        binding.editText.editTextSearch.apply {
            addTextChangedListener(viewModel.getIndustriesTextWatcher())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    binding.editText.clearIcon.isVisible = !s.isNullOrEmpty()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Empty
                }
            })
        }
    }

    private fun observeViewModel() {
        viewModel.getIndustriesScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    binding.loaderWrapper.searchProgressBar.isVisible = true
                }

                is ScreenState.Success -> {
                    binding.loaderWrapper.searchProgressBar.isVisible = false
                    binding.placeholderLayout.isVisible = false
                    binding.industryList.isVisible = true
                    industryAdapter.submitList(state.data)
                }

                is ScreenState.Empty -> {
                    binding.loaderWrapper.searchProgressBar.isVisible = false
                    binding.placeholderLayout.isVisible = true
                    binding.industryList.isVisible = false

                    binding.placeholderImage.setImageResource(R.drawable.err_load_list)
                    binding.placeholderText.text = "Ничего не найдено"
                }

                is ScreenState.NetworkError -> {
                    binding.loaderWrapper.searchProgressBar.isVisible = false
                    binding.placeholderLayout.isVisible = true
                    binding.industryList.isVisible = false

                    binding.placeholderImage.setImageResource(R.drawable.err_no_connection)
                    binding.placeholderText.text = "Нет интернета"
                }

                else -> {
                    binding.loaderWrapper.searchProgressBar.isVisible = false
                    binding.placeholderImage.setImageResource(R.drawable.err_load_list)
                    binding.placeholderText.text = "Не удалось получить список"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
