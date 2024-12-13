package com.example.organizemeofficial.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.organizemeofficial.task.Task

class HomeViewModel : ViewModel() {

    // Texto del mensaje de bienvenida
    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenida a tu app"
    }
    val text: LiveData<String> = _text

    // Lista de tareas completadas
    private val _tasks = MutableLiveData<List<Task>>().apply {
        value = listOf(
            Task("Comprar pan", "Ir a la panadería", true),
            Task("Hacer ejercicio", "Correr 30 minutos", true),
            Task("Leer un libro", "Terminar el libro de ciencia ficción", true)
        )
    }
    val tasks: LiveData<List<Task>> = _tasks
}
