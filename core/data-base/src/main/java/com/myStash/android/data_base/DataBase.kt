package com.myStash.android.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myStash.android.data_base.converter.LongTypeListConverter
import com.myStash.android.data_base.item.dao.ItemDao
import com.myStash.android.data_base.style.dao.StyleDao
import com.myStash.android.data_base.tag.dao.TagDao
import com.myStash.android.data_base.item.entity.ItemEntity
import com.myStash.android.data_base.style.entity.StyleEntity
import com.myStash.android.data_base.tag.entity.TagEntity
import com.squareup.moshi.Moshi

@Database(
    entities = [
        ItemEntity::class,
        StyleEntity::class,
        TagEntity::class
    ],
    version = 1
)
@TypeConverters(value = [LongTypeListConverter::class])
abstract class DataBase : RoomDatabase() {

    abstract fun ItemDao() : ItemDao

    abstract fun StyleDao() : StyleDao

    abstract fun TagDao() : TagDao


    companion object {
        private const val DATA_BASE_NAME = "myStashDataBase"

        fun build(context: Context, moshi: Moshi) = Room.databaseBuilder(
            context = context,
            klass = DataBase::class.java,
            name = DATA_BASE_NAME
        )
            .addTypeConverter(LongTypeListConverter(moshi))
            .fallbackToDestructiveMigration().build()
    }
}