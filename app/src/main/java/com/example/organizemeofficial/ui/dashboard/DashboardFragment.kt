package com.example.organizemeofficial.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.organizemeofficial.databinding.FragmentDashboardBinding
import com.example.organizemeofficial.task.Task
import com.example.organizemeofficial.task.TaskRepository

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar el botón para añadir tarea
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.etTaskDescription.text.toString()

            if (title.isNotEmpty()) {
                // Aquí guardamos la tarea
                addTask(title, description)
                // Limpiamos los campos
                binding.etTaskTitle.text.clear()
                binding.etTaskDescription.text.clear()
                Toast.makeText(context, "Tarea añadida", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun addTask(title: String, description: String) {
        // Crear tarea con el estado "no completada"
        val task = Task(title, description, false)
        TaskRepository.addTask(task) // Agregar la tarea al repositorio
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
