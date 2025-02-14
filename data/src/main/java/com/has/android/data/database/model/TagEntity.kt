package com.has.android.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.has.android.core.model.Tag

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun TagEntity.toTag(): Tag = Tag(
            id = id,
            name = name,
            createTime = createTime,
            isRemove = isRemove
        )

        fun Tag.toEntity(): TagEntity = TagEntity(
            id = id,
            name = name,
            createTime = createTime,
            isRemove = isRemove
        )
    }
}