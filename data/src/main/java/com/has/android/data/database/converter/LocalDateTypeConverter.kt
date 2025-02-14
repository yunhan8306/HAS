package com.has.android.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import java.time.LocalDate
import javax.inject.Inject

@ProvidedTypeConverter
class LocalDateTypeConverter @Inject constructor(
    private val moshi: Moshi
) {
    @TypeConverter
    fun toLocalDate(value: String): LocalDate? {
        return moshi.adapter(LocalDate::class.java).fromJson(value)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return moshi.adapter(LocalDate::class.java).toJson(date)
    }

}