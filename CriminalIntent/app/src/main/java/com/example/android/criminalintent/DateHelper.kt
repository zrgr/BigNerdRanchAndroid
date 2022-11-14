package com.example.android.criminalintent

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateHelper {
    companion object{
        fun formatDate(oldDate: Date): String {
            val formattedDate = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.UK)
            return formattedDate.format(oldDate)
        }
    }
}