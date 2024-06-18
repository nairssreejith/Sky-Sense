package com.sreejithsnair.skysense.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sreejithsnair.skysense.MainApplication
import com.sreejithsnair.skysense.database.tododatabase.ToDoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date


class ToDoViewModel: ViewModel() {

    val toDoDao = MainApplication.toDoDatabase.getToDoDao()

    val toDoList : LiveData<List<ToDoEntity>> = toDoDao.getAllToDo()

    fun addToDo(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.addToDo(ToDoEntity(title = title, createdAt = Date.from(Instant.now())))
        }
    }

    fun deleteToDo(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.deleteToDo(id)
        }
    }


}