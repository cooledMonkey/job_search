package com.example.android.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.android.test.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentFavouritesBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_favourites, container,false)
        binding.toolbar.title = getString(R.string.favourites_title)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))

        return binding.root
    }


}