package com.has.android.data_base.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class LongTypeListConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun toLongList(value: String): List<Long>? {
        val type = Types.newParameterizedType(List::class.java, Long::class.javaObjectType)
        val adapter = moshi.adapter<List<Long>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromLongList(list: List<Long>): String {
        val type = Types.newParameterizedType(List::class.java, Long::class.javaObjectType)
        val adapter = moshi.adapter<List<Long>>(type)
        return adapter.toJson(list)
    }
}