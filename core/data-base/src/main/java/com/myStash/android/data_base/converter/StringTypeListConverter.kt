package com.myStash.android.data_base.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class StringTypeListConverter @Inject constructor(
    private val moshi: Moshi
) {
    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val type = Types.newParameterizedType(List::class.java, String::class.javaObjectType)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        val type = Types.newParameterizedType(List::class.java, String::class.javaObjectType)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(list)
    }
}