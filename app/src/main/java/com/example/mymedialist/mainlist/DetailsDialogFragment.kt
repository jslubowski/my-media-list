package com.example.mymedialist.mainlist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mymedialist.R

class DetailsDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder?.setMessage(R.string.dialog_properties_title)



            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}