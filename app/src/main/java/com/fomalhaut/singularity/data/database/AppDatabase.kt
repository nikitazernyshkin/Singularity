package com.fomalhaut.singularity.data.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fomalhaut.singularity.dev.data.dao.PasswordDao
import com.fomalhaut.singularity.dev.data.entity.PasswordEntity


@SuppressLint("RestrictedApi")
@Database(
    entities = [PasswordEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun passwordDao(): PasswordDao
    companion object INSTANCE {
        @Volatile
        var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            instance?.let { return it }
            synchronized(this){val newInstance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "singularity.db").build(); instance = newInstance}; return instance!!
        }
    }
}