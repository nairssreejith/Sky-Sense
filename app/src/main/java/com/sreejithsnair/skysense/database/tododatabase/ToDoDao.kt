package com.sreejithsnair.skysense.database.ToDoDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sreejithsnair.skysense.database.tododatabase.ToDoEntity

@Dao
interface ToDoDao {
    @Query("SELECT * FROM TODOENTITY ORDER BY createdAt DESC")
    fun getAllToDo(): LiveData<List<ToDoEntity>>

    @Insert
    fun addToDo(toDoEntity: ToDoEntity)

    @Query("DELETE FROM TODOENTITY WHERE id = :id")
    fun deleteToDo(id: Int)

}