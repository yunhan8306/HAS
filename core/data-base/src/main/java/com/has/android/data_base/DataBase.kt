package com.has.android.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.has.android.data_base.converter.LocalDateTypeConverter
import com.has.android.data_base.converter.LongTypeListConverter
import com.has.android.data_base.converter.StringTypeListConverter
import com.has.android.data_base.feed.dao.FeedDao
import com.has.android.data_base.feed.entity.FeedEntity
import com.has.android.data_base.has.dao.HasDao
import com.has.android.data_base.has.entity.HasEntity
import com.has.android.data_base.style.dao.StyleDao
import com.has.android.data_base.style.entity.StyleEntity
import com.has.android.data_base.tag.dao.TagDao
import com.has.android.data_base.tag.entity.TagEntity
import com.has.android.data_base.type.dao.TypeDao
import com.has.android.data_base.type.entity.TypeEntity
import com.squareup.moshi.Moshi

@Database(
    entities = [
        HasEntity::class,
        StyleEntity::class,
        TagEntity::class,
        TypeEntity::class,
        FeedEntity::class
    ],
    version = 1
)
@TypeConverters(value = [LongTypeListConverter::class, StringTypeListConverter::class, LocalDateTypeConverter::class])
abstract class DataBase : RoomDatabase() {

    abstract fun HasDao() : HasDao

    abstract fun StyleDao() : StyleDao

    abstract fun TagDao() : TagDao

    abstract fun TypeDao() : TypeDao

    abstract fun FeedDao() : FeedDao

    companion object {
        private const val DATA_BASE_NAME = "hasDataBase"

        fun build(context: Context, moshi: Moshi) = Room.databaseBuilder(
            context = context,
            klass = DataBase::class.java,
            name = DATA_BASE_NAME
        )
            .addTypeConverter(LongTypeListConverter(moshi))
            .addTypeConverter(StringTypeListConverter(moshi))
            .addTypeConverter(LocalDateTypeConverter(moshi))
            .fallbackToDestructiveMigration().build()
    }
}