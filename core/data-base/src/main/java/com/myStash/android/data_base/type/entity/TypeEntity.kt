package com.myStash.android.data_base.type.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myStash.android.core.model.Type

@Entity(tableName = "type")
data class TypeEntity(
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
        fun TypeEntity.toType(): Type = Type(
            id = id,
            name = name,
            createTime = createTime,
            isRemove = isRemove
        )

        fun Type.toEntity(): TypeEntity = TypeEntity(
            id = id,
            name = name,
            createTime = createTime,
            isRemove = isRemove
        )
    }
}