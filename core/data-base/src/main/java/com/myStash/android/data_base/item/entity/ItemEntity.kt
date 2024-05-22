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

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "tags")
    val tags: List<Long> = emptyList(),

    @ColumnInfo(name = "brand")
    val brand: Long? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "imagePath")
    val imagePath: String? = null,

    @ColumnInfo(name = "memo")
    val memo: String,

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun ItemEntity.toItem() = Item(
            id = id,
            name = name,
            tags = tags,
            brand = brand,
            type = type,
            imagePath = imagePath,
            memo = memo,
            createTime = createTime,
            isRemove = isRemove,
        )

        fun Item.toItemEntity() = ItemEntity(
            id = id,
            name = name,
            tags = tags,
            brand = brand,
            type = type,
            imagePath = imagePath,
            memo = memo,
            createTime = createTime,
            isRemove = isRemove,
        )
    }
}