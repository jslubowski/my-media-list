package com.example.my_media_list.titlescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.my_media_list.R
import com.example.my_media_list.databinding.FragmentTitleScreenBinding


class TitleScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleScreenBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title_screen, container, false
        )
        return binding.root
    }

}
