package com.example.my_media_list.mainlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.my_media_list.R
import com.example.my_media_list.databinding.FragmentMainListBinding

class MainListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_list, container, false
        )
        val viewModelFactory = MainListViewModelFactory()
        val mainListViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainListViewModel::class.java)
        binding.mainListViewModel = mainListViewModel


        return binding.root
    }

}
