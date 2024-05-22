package com.myStash.android.data_base.tag.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myStash.android.core.model.Tag

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "name")
    val name: String,
) {
    companion object {
        fun TagEntity.toTag(): Tag = Tag(
            id = id,
            name = name
        )

        fun Tag.toEntity(): TagEntity = TagEntity(
            id = id,
            name = name
        )
    }
}