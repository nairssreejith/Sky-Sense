package com.sreejithsnair.skysense.database.tododatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sreejithsnair.skysense.database.ToDoDb.ToDoDao
import com.sreejithsnair.skysense.utilities.Converters

@Database(entities = [ToDoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "todo.db"
    }

    abstract fun getToDoDao(): ToDoDao

}