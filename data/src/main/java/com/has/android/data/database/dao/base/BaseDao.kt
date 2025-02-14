package com.has.android.data.database.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: Collection<T>)

    /** update 항목 수 반환 */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: T) : Int

    /** delete 항목 수 반환 */
    @Delete
    suspend fun delete(entity: T): Int
}