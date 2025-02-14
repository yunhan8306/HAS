package com.has.android.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.has.android.core.model.Type

@Entity(tableName = "type")
data class TypeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "order")
    val order: Long? = id,

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun TypeEntity.toType(): Type = Type(
            id = id,
            name = name,
            order = order,
            createTime = createTime,
            isRemove = isRemove
        )

        fun Type.toEntity(): TypeEntity = TypeEntity(
            id = id,
            name = name,
            order = order,
            createTime = createTime,
            isRemove = isRemove
        )
    }
}