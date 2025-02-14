package com.has.android.data.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.has.android.data.database.converter.LocalDateTypeConverter
import com.has.android.data.database.converter.LongTypeListConverter
import com.has.android.data.database.converter.StringTypeListConverter
import com.has.android.data.database.dao.feed.FeedDao
import com.has.android.data.database.dao.has.HasDao
import com.has.android.data.database.dao.style.StyleDao
import com.has.android.data.database.dao.tag.TagDao
import com.has.android.data.database.dao.type.TypeDao
import com.has.android.data.database.model.FeedEntity
import com.has.android.data.database.model.HasEntity
import com.has.android.data.database.model.StyleEntity
import com.has.android.data.database.model.TagEntity
import com.has.android.data.database.model.TypeEntity
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