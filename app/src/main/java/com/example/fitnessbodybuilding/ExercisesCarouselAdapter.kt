package com.example.fitnessbodybuilding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Exercise(val name: String, val description: String, val kcal: Int)

class ExercisesCarouselAdapter(
    private val exercises: MutableList<Esercizio>
) : RecyclerView.Adapter<ExercisesCarouselAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName: TextView = itemView.findViewById(R.id.tvExerciseTitle)
        val exerciseDescription: TextView = itemView.findViewById(R.id.tvExerciseDescription)
        val exerciseKcal: TextView = itemView.findViewById(R.id.tvExerciseKcal)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_carousel, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseName.text = exercise.nome
        holder.exerciseDescription.text = exercise.descrizione
        holder.exerciseKcal.text = "Kcal burned: ${exercise.kcal}"
    }

    override fun getItemCount(): Int = exercises.size
}
