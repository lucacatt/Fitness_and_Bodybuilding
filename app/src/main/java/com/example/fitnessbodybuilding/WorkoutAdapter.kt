import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbodybuilding.Allenamento
import com.example.fitnessbodybuilding.R
import java.time.LocalDate

data class Exercise(val name: String, val weight: Int, val sets: Int, val reps: Int)
data class Workout(val date: LocalDate, val exercises: List<Exercise>)

class WorkoutAdapter(private val workouts: MutableList<Allenamento>) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWorkoutDate: TextView = itemView.findViewById(R.id.tvWorkoutDate)
        val layoutExercises: ViewGroup = itemView.findViewById(R.id.layoutExercises)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_es, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.tvWorkoutDate.text ="Data: ${workout.data}"
        holder.layoutExercises.removeAllViews()
        val exerciseView = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.item_exercise, holder.layoutExercises, false)
        exerciseView.findViewById<TextView>(R.id.tvExerciseName).text =
            ""
        exerciseView.findViewById<TextView>(R.id.tvPesoValue).text =
            "Kg"
        exerciseView.findViewById<TextView>(R.id.tvSerieValue).text =
            "Serie"
        exerciseView.findViewById<TextView>(R.id.tvRepsValue).text =
            "Reps"
        holder.layoutExercises.addView(exerciseView)
        for (exercise in workout.divisione.listaEsercizi) {
            val exerciseView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.item_exercise, holder.layoutExercises, false)
            exerciseView.findViewById<TextView>(R.id.tvExerciseName).text =
                "${exercise.Esercizio.nome}"
            exerciseView.findViewById<TextView>(R.id.tvPesoValue).text =
                "${exercise.peso}"
            exerciseView.findViewById<TextView>(R.id.tvSerieValue).text =
                "${exercise.serie}"
            exerciseView.findViewById<TextView>(R.id.tvRepsValue).text =
                "${exercise.ripetizioni} "
            holder.layoutExercises.addView(exerciseView)
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}
