package com.example.organizemeofficial.ui.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizemeofficial.R
import com.example.organizemeofficial.task.Task

class TaskAdapter(
    private val tasks: List<Task>,
    private val onTaskAction: (Task, String) -> Unit // Callback para manejar acciones
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.task_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.title

        holder.itemView.setOnClickListener {
            showTaskDialog(holder.itemView.context, task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    private fun showTaskDialog(context: Context, task: Task) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(task.title)
        builder.setMessage(task.description) // Muestra la descripción de la tarea

        // Botón "Completar"
        builder.setPositiveButton("Completar") { _, _ ->
            onTaskAction(task, "complete")
        }

        // Botón "Posponer"
        builder.setNeutralButton("Posponer") { _, _ ->
            onTaskAction(task, "postpone")
        }

        // Botón "Cancelar"
        builder.setNegativeButton("Cancelar") { _, _ ->
            onTaskAction(task, "cancel")
        }

        // Mostrar el diálogo
        builder.create().show()
    }
}
