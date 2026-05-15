package com.starter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starter.core.database.dao.UserDao
import com.starter.core.database.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
