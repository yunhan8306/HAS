package com.myStash.android.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myStash.android.data_base.converter.LongTypeListConverter
import com.myStash.android.data_base.converter.StringTypeListConverter
import com.myStash.android.data_base.feed.dao.FeedDao
import com.myStash.android.data_base.feed.entity.FeedEntity
import com.myStash.android.data_base.has.dao.HasDao
import com.myStash.android.data_base.has.entity.HasEntity
import com.myStash.android.data_base.style.dao.StyleDao
import com.myStash.android.data_base.style.entity.StyleEntity
import com.myStash.android.data_base.tag.dao.TagDao
import com.myStash.android.data_base.tag.entity.TagEntity
import com.myStash.android.data_base.type.dao.TypeDao
import com.myStash.android.data_base.type.entity.TypeEntity
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
@TypeConverters(value = [LongTypeListConverter::class, StringTypeListConverter::class])
abstract class DataBase : RoomDatabase() {

    abstract fun HasDao() : HasDao

    abstract fun StyleDao() : StyleDao

    abstract fun TagDao() : TagDao

    abstract fun TypeDao() : TypeDao

    abstract fun FeedDao() : FeedDao

    companion object {
        private const val DATA_BASE_NAME = "myStashDataBase"

        fun build(context: Context, moshi: Moshi) = Room.databaseBuilder(
            context = context,
            klass = DataBase::class.java,
            name = DATA_BASE_NAME
        )
            .addTypeConverter(LongTypeListConverter(moshi))
            .addTypeConverter(StringTypeListConverter(moshi))
            .fallbackToDestructiveMigration().build()
    }
}