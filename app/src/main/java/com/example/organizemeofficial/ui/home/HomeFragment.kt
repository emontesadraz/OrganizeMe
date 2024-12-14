package com.example.organizemeofficial.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizemeofficial.databinding.FragmentHomeBinding
import com.example.organizemeofficial.task.TaskRepository

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tasks = TaskRepository.getTasks()
        binding.recyclerTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerTasks.adapter = TaskAdapter(tasks) { task, action ->
            when (action) {
                "complete" -> {
                    TaskRepository.updateTask(task, true)
                    Toast.makeText(context, "Tarea completada", Toast.LENGTH_SHORT).show()
                }
                "postpone" -> {
                    Toast.makeText(context, "Tarea pospuesta", Toast.LENGTH_SHORT).show()
                }
                "cancel" -> {
                    TaskRepository.removeTask(task)
                    Toast.makeText(context, "Tarea cancelada", Toast.LENGTH_SHORT).show()
                }
            }
            binding.recyclerTasks.adapter?.notifyDataSetChanged()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
