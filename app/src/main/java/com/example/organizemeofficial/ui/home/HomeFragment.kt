package com.example.organizemeofficial.ui.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.organizemeofficial.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configura el mensaje de bienvenida
        val textViewWelcome: TextView = binding.textWelcome
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val userName = sharedPref?.getString("username", "Ana")
        textViewWelcome.text = "Bienvenida $userName"

        // Configura la lista de tareas
        val recyclerTasks = binding.recyclerTasks
        recyclerTasks.layoutManager = LinearLayoutManager(requireContext())

        // Configura el adaptador vacío inicialmente
        adapter = TaskAdapter(emptyList())
        recyclerTasks.adapter = adapter

        // Observa las tareas desde el ViewModel
        homeViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.updateTasks(tasks) // Actualiza el adaptador con la nueva lista de tareas
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
