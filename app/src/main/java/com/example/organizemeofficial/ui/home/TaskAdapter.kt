package com.example.organizemeofficial.ui.home

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizemeofficial.R
import com.example.organizemeofficial.task.Task

class TaskAdapter(
    private val tasks: List<Task>, // Cambiar a MutableList para poder actualizar la lista
    private val onTaskAction: (Task, String) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.task_name) // Este es el ID que debe existir en item_task.xml
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false) // Aquí se inflará item_task.xml
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Estilo para tareas completadas
        if (task.isCompleted) {
            holder.taskName.paintFlags = holder.taskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.taskName.setTextColor(holder.itemView.context.getColor(R.color.green))
            holder.itemView.isEnabled = false  // Deshabilita la tarea
        } else {
            holder.taskName.paintFlags =
                holder.taskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.taskName.setTextColor(holder.itemView.context.getColor(R.color.black))
            holder.itemView.isEnabled = true // Habilita la tarea si no está completada
        }

        // Establece el nombre de la tarea
        holder.taskName.text = task.title

        // Acción al hacer clic sobre la tarea (solo si no está completada)
        holder.itemView.setOnClickListener {
            if (!task.isCompleted) {
                showTaskDialog(holder.itemView.context, task)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    private fun showTaskDialog(context: Context, task: Task) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(task.title)
        builder.setMessage(task.description)

        if (!task.isCompleted) {
            builder.setPositiveButton("Completar") { _, _ ->
                // Marca la tarea como completada
                task.isCompleted = true
                onTaskAction(task, "complete")
                notifyDataSetChanged() // Notifica a la vista que se ha actualizado
            }
            builder.setNeutralButton("Posponer") { _, _ ->
                onTaskAction(task, "postpone")
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            onTaskAction(task, "cancel")
        }

        builder.create().show()
    }
}

