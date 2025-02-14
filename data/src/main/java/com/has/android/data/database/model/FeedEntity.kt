package com.has.android.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.has.android.core.model.Feed
import java.time.LocalDate

@Entity(tableName = "feed")
data class FeedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long? = null,

    @ColumnInfo(name = "date")
    val date: LocalDate = LocalDate.now(),

    @ColumnInfo(name = "images")
    val images: List<String> = emptyList(),

    @ColumnInfo(name = "styleId")
    val styleId: Long? = null,

    @ColumnInfo(name = "createAt")
    val createTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isRemove")
    val isRemove: Boolean = false
) {
    companion object {
        fun FeedEntity.toFeed() = Feed(
            id = id,
            date = date,
            images = images,
            styleId = styleId,
            createTime = createTime,
            isRemove = isRemove,
        )

        fun Feed.toEntity() = FeedEntity(
            id = id,
            date = date,
            images = images,
            styleId = styleId,
            createTime = createTime,
            isRemove = isRemove,
        )
    }
}