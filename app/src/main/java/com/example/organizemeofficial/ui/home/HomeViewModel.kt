package com.example.organizemeofficial.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.organizemeofficial.task.Task
import com.example.organizemeofficial.task.TaskRepository

class HomeViewModel : ViewModel() {

    val tasks: LiveData<MutableList<Task>> = TaskRepository.tasks
}
