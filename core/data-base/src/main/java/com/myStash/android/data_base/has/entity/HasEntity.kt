package com.myStash.android.data_base.has.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myStash.android.core.model.Has

@Entity(tableName = "has")
data class HasEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "tags")
    val tags: List<Long> = emptyList(),

    @ColumnInfo(name = "type")
    val type: Long,

    @ColumnInfo(name = "imagePath")
    val imagePath: String? = null,

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun HasEntity.toHas() = Has(
            id = id,
            tags = tags,
            type = type,
            imagePath = imagePath,
            createTime = createTime,
            isRemove = isRemove,
        )

        fun Has.toHasEntity() = HasEntity(
            id = id,
            tags = tags,
            type = type,
            imagePath = imagePath,
            createTime = createTime,
            isRemove = isRemove,
        )
    }
}