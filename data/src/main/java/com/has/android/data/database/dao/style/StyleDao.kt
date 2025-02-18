package com.has.android.data.database.dao.style

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.has.android.data.database.dao.base.BaseDao
import com.has.android.data.database.model.StyleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StyleDao : BaseDao<StyleEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: StyleEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: StyleEntity): Int

    @Delete
    override suspend fun delete(entity: StyleEntity): Int

    @Query("SELECT * FROM style")
    fun selectAll() : Flow<List<StyleEntity>>
}