package com.example.organizemeofficial.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object TaskRepository {

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> = _tasks

    fun getTasks(): List<Task> {
        return _tasks.value ?: emptyList()
    }

    fun addTask(task: Task) {
        val currentTasks = _tasks.value ?: mutableListOf()
        currentTasks.add(task)
        _tasks.postValue(currentTasks) // Notificamos los cambios a los observadores
    }

    fun updateTask(task: Task, isCompleted: Boolean) {
        val currentTasks = _tasks.value ?: mutableListOf()
        val index = currentTasks.indexOf(task)
        if (index != -1) {
            currentTasks[index] = task.copy(isCompleted = isCompleted)
            _tasks.postValue(currentTasks) // Notificamos los cambios
        }
    }

    fun removeTask(task: Task) {
        val currentTasks = _tasks.value ?: mutableListOf()
        currentTasks.remove(task)
        _tasks.postValue(currentTasks) // Notificamos los cambios
    }
}
