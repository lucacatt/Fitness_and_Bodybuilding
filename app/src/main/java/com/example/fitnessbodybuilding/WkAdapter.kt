package com.example.fitnessbodybuilding

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WkAdapter(
    private val context: Context,
    private val workoutDays: List<Divisione>
) : RecyclerView.Adapter<WkAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        val exerciseTextView: TextView = itemView.findViewById(R.id.exerciseTextView)
        val startWorkoutButton: Button = itemView.findViewById(R.id.startWorkoutButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workoutDay = workoutDays[position]
        holder.dayTextView.text = "Giorno ${position + 1}"

        val exercises = workoutDay.listaEsercizi.joinToString("\n") { exercise ->
            "${exercise.Esercizio.nome} - Serie: ${exercise.serie}, Ripetizioni: ${exercise.ripetizioni}, Peso: ${exercise.peso}"
        }
        holder.exerciseTextView.text = exercises

        holder.startWorkoutButton.setOnClickListener {
            val intent = Intent(context, PaginaAllenamentoActivity::class.java)
            intent.putExtra("chiaveIntero", position)
            context.startActivity(intent)
        }

        Log.d("WkAdapter", "Binding view holder for position: $position")
    }

    override fun getItemCount(): Int {
        return workoutDays.size
    }
}
