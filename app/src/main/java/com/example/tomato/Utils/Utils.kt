package com.example.tomato.Utils

import java.text.SimpleDateFormat

class Utils {
    fun formatTime(time: Int): String? {
        val dateFormat = SimpleDateFormat("mm:ss")
        return dateFormat.format(time)
    }
}