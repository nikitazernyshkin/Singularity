package com.fomalhaut.singularity.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fomalhaut.singularity.dev.data.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: PasswordEntity)

    @Update
    suspend fun update(password: PasswordEntity)

    @Delete
    suspend fun delete(password: PasswordEntity)

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): Flow<List<PasswordEntity>>
}