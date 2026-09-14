package com.example.notesapp

import androidx.room.TypeConverter
import java.time.LocalDateTime

class DateConverters {

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime {
        return LocalDateTime.parse(date)
    }
}
