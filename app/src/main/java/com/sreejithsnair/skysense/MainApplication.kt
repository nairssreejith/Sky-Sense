package com.sreejithsnair.skysense

import android.app.Application
import androidx.room.Room
import com.sreejithsnair.skysense.database.tododatabase.ToDoDatabase

class MainApplication: Application() {
    companion object{
       lateinit var toDoDatabase: ToDoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        toDoDatabase = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.DATABASE_NAME
        ).build()

    }

}