package com.myStash.android.data_base.style.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myStash.android.core.model.Style

@Entity(tableName = "style")
data class StyleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "imagePath")
    val imagePaths: List<String> = emptyList(),

    @ColumnInfo(name = "items")
    val items: List<Long> = emptyList(),

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

) {
    companion object {
        fun StyleEntity.toStyle() = Style(
            id = id,
            imagePaths = imagePaths,
            items = items,
            createTime = createTime
        )

        fun Style.toStyleEntity() = StyleEntity(
            id = id,
            imagePaths = imagePaths,
            items = items,
            createTime = createTime
        )
    }
}