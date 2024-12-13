package com.example.organizemeofficial.task

object TaskRepository {

    private val tasks = mutableListOf<Task>()

    fun getTasks(): List<Task> {
        return tasks
    }

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun updateTask(task: Task, isCompleted: Boolean) {
        val index = tasks.indexOf(task)
        if (index != -1) {
            tasks[index] = task.copy(isCompleted = isCompleted)
        }
    }

    fun removeTask(task: Task) {
        tasks.remove(task)
    }
}
