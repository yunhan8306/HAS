package com.myStash.android.data_base.item.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myStash.android.core.model.Item

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "tags")
    val tags: List<Long> = emptyList(),

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "imagePath")
    val imagePath: String? = null,

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun ItemEntity.toItem() = Item(
            id = id,
            tags = tags,
            type = type,
            imagePath = imagePath,
            createTime = createTime,
            isRemove = isRemove,
        )

        fun Item.toItemEntity() = ItemEntity(
            id = id,
            tags = tags,
            type = type,
            imagePath = imagePath,
            createTime = createTime,
            isRemove = isRemove,
        )
    }
}