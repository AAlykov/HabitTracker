package com.tomsk.alykov.habittracker.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Calculation {

    fun calculateTimeBetweenDates(startDate: String): String {
        val endDate = timeStampToString(System.currentTimeMillis())
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date1 = sdf.parse(startDate)
        val date2 = sdf.parse(endDate)
        var isNegative = false
        var difference = date2.time - date1.time
        if (difference < 0) {
            difference = -(difference)
            isNegative = true
        }
        val minutes = difference / 60 / 1000
        val hours = difference / 60 /1000 / 60
        val days = (difference/60/1000/60)/24
        val months = (difference/60/1000/60)/24 / (365/12)
        val years =  (difference/60/1000/60)/24 / 365

        if (isNegative) {
            return when {
                minutes < 240 -> "Start in $minutes minutes"
                hours < 48 -> "Start in $hours hours"
                days < 61 -> "Start in $days days"
                months < 24 -> "Start in $months months"
                else -> "Start in $years years"
            }
        } else
            return when {
                minutes < 240 -> "$minutes minutes ago"
                hours < 48 -> "$hours hours ago"
                days < 61 -> "$days days ago"
                months < 24 -> "$months months ago"
                else -> "Start in $years years ago"
            }
    }

    private fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.format(Date(stamp.time))
        return date.toString()
    }

    fun cleanDate(_day: Int, _month: Int, _year: Int): String {
        var day = _day.toString()
        var month = _month.toString()
        if (_day < 10) {
            day = "0$_day"
        }
        if (_month < 9) {
            month = "0${_month+1}"
        }
        return "$day/$month/$_year"
    }

    fun cleanTime(_hour: Int, _minute: Int): String {
        var hour = _hour.toString()
        var minute = _minute.toString()
        if (_hour < 10) {
            hour = "0$_hour"
        }
        if (_minute < 10) {
            minute = "0$_minute"
        }
        return "$hour:$minute"
    }

}