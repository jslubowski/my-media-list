package com.example.mymedialist.details

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mymedialist.R
import com.example.mymedialist.databinding.DialogSelectRatingBinding

// TODO couldn't figure out how to use the same class as in
//  searchitem package

class SelectRatingDetailsDialog(
    private val movieDetailsViewModel: MovieDetailsViewModel
) : DialogFragment() {

    val MAX_VALUE = 10
    val MIN_VALUE = 1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.AlertDialogTheme)
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
                    movieDetailsViewModel.editRating(numberPicker.value)
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