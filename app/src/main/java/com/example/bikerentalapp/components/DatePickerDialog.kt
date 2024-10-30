package com.example.bikerentalapp.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val context = LocalContext.current

    val maxCalendar = Calendar.getInstance()
    maxCalendar.add(Calendar.YEAR, -18)

    val minCalendar = Calendar.getInstance()
    minCalendar.add(Calendar.YEAR, -100)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(
                String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            )
        },
        maxCalendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.datePicker.apply {
        maxDate = maxCalendar.timeInMillis
        minDate = minCalendar.timeInMillis
    }

    datePickerDialog.setOnDismissListener {
        onDismiss()
    }

    datePickerDialog.show()
}