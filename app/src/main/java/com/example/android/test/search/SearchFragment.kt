package com.example.android.test.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.test.R
import com.example.android.test.database.VacancyDatabase
import com.example.android.test.databinding.FragmentSearchBinding
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onStart() {
        super.onStart()
        viewModel.updateVacanciesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val database = VacancyDatabase.getInstance(application).vacancyDatabaseDao
        val factory = SearchViewModelFactory(database)
        viewModel = ViewModelProvider.create(this, factory)[SearchViewModel::class.java]
        val vacancyAdapter =
            CompositeDelegateAdapter(VacancyDelegateAdapter(
                object : VacanciesActionListener {
                    override fun onButtonClick() {
                    }
                    override fun onVacancyCardClick() {
                        findNavController().navigate(SearchFragmentDirections.
                        actionSearchFragmentToPlugFragment())
                    }
                    override fun onFavouriteMarkClick(id: String) {
                        viewModel.onFavouriteMarkClick(id)
                    }
                }), ButtonDelegateAdapter(object : ButtonActionListener {
                override fun onButtonClick() {
                    viewModel.onBigBlueButtonClick()
                    binding.textView2.text = viewModel.buildVacanciesCountString(requireContext())
                    binding.resViewFilters.visibility = View.GONE
                    binding.textView.visibility = View.GONE
                    binding.textView2.visibility = View.VISIBLE
                    binding.textView3.visibility = View.VISIBLE
                }
            })
            )
        binding.recyclerView.adapter = vacancyAdapter
        viewModel.vacancies.observe(viewLifecycleOwner, Observer {
            vacancyAdapter.swapData(viewModel.buildListVacancies())
            if(viewModel.isBigBlueButtonClicked.value == true){
                binding.textView2.text = viewModel.buildVacanciesCountString(requireContext())
                binding.resViewFilters.visibility = View.GONE
                binding.textView.visibility = View.GONE
                binding.textView2.visibility = View.VISIBLE
                binding.textView3.visibility = View.VISIBLE
            }
        })
        val offersAdapter = CompositeDelegateAdapter(
            FiltersDelegateAdapter()
        )
        binding.resViewFilters.adapter = offersAdapter
        viewModel.filters.observe(viewLifecycleOwner, Observer {
            offersAdapter.swapData(it)
        })
        viewModel.isBigBlueButtonClicked.observe(
            viewLifecycleOwner,
            Observer { vacancyAdapter.swapData(viewModel.buildListVacancies()) })

        return binding.root
    }
}