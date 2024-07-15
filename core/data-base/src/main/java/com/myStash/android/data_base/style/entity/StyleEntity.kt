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

    @ColumnInfo(name = "hass")
    val hass: List<Long> = emptyList(),

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun StyleEntity.toStyle() = Style(
            id = id,
            hass = hass,
            createTime = createTime,
            isRemove = isRemove
        )

        fun Style.toStyleEntity() = StyleEntity(
            id = id,
            hass = hass,
            createTime = createTime,
            isRemove = isRemove
        )
    }
}