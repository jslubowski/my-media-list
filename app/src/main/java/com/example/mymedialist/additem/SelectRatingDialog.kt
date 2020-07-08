package com.example.mymedialist.additem

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.mymedialist.R
import com.example.mymedialist.databinding.DialogSelectRatingBinding
import kotlinx.android.synthetic.main.fragment_movie_details.*

class SelectRatingDialog(
    private val addItemViewModel: AddItemViewModel
) : DialogFragment() {

    val MAX_VALUE = 10
    val MIN_VALUE = 1

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.Theme_AppCompat_Light)
            val inflater = requireActivity().layoutInflater
            val binding = DialogSelectRatingBinding.inflate(inflater)

            builder.setView(binding.root)

            binding.lifecycleOwner = this

            val numberPicker = binding.numberPicker
            numberPicker.maxValue = MAX_VALUE
            numberPicker.minValue = MIN_VALUE

            builder.setMessage(R.string.rating_picker_title)
            builder.setPositiveButton(R.string.rating_picker_positive) { _, _ ->
                run {
                    addItemViewModel.setRating(numberPicker.value)
                    this.dismiss()
                }
            }

            builder.setNegativeButton(R.string.rating_picker_negative) { _, _ ->
                run {
                    this.dismiss()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}