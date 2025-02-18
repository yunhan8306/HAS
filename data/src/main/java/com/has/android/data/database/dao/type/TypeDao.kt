package com.has.android.data.database.dao.type

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.has.android.data.database.dao.base.BaseDao
import com.has.android.data.database.model.TypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeDao: BaseDao<TypeEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: TypeEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: TypeEntity): Int

    @Delete
    override suspend fun delete(entity: TypeEntity): Int

    @Query("SELECT * FROM type")
    fun selectAll() : Flow<List<TypeEntity>>

    @Query("SELECT * FROM type WHERE name = :name LIMIT 1")
    fun getType(name: String): TypeEntity?
}