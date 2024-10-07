package com.example.android.test.favourites

import android.os.Bundle
import android.util.Log
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
import com.example.android.test.databinding.FragmentFavouritesBinding
import com.example.android.test.search.VacanciesActionListener
import com.example.android.test.search.VacancyDelegateAdapter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter


class FavouritesFragment : Fragment() {
    private lateinit var viewModel: FavouritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateVacanciesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFavouritesBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_favourites, container,false)
        binding.toolbar.title = getString(R.string.favourites_title)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))

        val application = requireNotNull(this.activity).application
        val database = VacancyDatabase.getInstance(application).vacancyDatabaseDao
        val factory = FavouritesViewModelFactory(database)
        viewModel = ViewModelProvider.create(this, factory)[FavouritesViewModel::class.java]

        val vacancyAdapter =
            CompositeDelegateAdapter(VacancyDelegateAdapter(
                object : VacanciesActionListener {
                    override fun onButtonClick() {
                    }

                    override fun onVacancyCardClick() {
                        findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToPlugFragment())
                    }

                    override fun onFavouriteMarkClick(id: String) {
                        viewModel.onFavouriteMarkClick(id)
                    }
                }))

        binding.resView.adapter = vacancyAdapter
        viewModel.vacancies.observe(viewLifecycleOwner, Observer{
            Log.i("NETWORK", it.toString())
            vacancyAdapter.swapData(viewModel.filterVacancies(it))
        })

        return binding.root
    }
}