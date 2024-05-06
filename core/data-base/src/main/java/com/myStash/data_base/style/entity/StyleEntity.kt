package com.myStash.data_base.style.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myStash.core.model.Style

@Entity(tableName = "style")
data class StyleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "imagePath")
    val imagePath: String? = null,

    @ColumnInfo(name = "items")
    val items: List<Long> = emptyList(),

    @ColumnInfo(name = "memo")
    val memo: String

) {
    companion object {
        fun StyleEntity.toStyle() = Style(
            id = id,
            imagePath = imagePath,
            items = items,
            memo = memo,
        )

        fun Style.toStyleEntity() = StyleEntity(
            id = id,
            imagePath = imagePath,
            items = items,
            memo = memo,
        )
    }
}