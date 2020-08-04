package com.example.mymedialist.statistics

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import timber.log.Timber
import java.time.LocalDate
import java.util.*

class SelectFromDateDialog(
    private val statisticsViewModel: StatisticsViewModel
): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        return DatePickerDialog(this.requireContext(), this, year, month, day)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Timber.i("$year - $month - $dayOfMonth")
        statisticsViewModel.setFromDate(LocalDate.of(year, month + 1, dayOfMonth))
    }
}