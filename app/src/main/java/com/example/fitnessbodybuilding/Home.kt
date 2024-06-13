package com.example.fitnessbodybuilding

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Benvenuto
        view.findViewById<TextView>(R.id.tvWelcomeTitle).text =
            "Benvenuto ${DataManagement.getInstance().loggato?.username}!"

        // Card Peso e Altezza
        view.findViewById<TextView>(R.id.tvWeightValue).text =
            "${DataManagement.getInstance().loggato?.peso} Kg"
        view.findViewById<TextView>(R.id.tvHeightValue).text =
            "${DataManagement.getInstance().loggato?.altezza} cm"

        // Carousel Esercizi
        val recyclerViewCarousel: RecyclerView = view.findViewById(R.id.recyclerViewCarousel)
        recyclerViewCarousel.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val exercises = DataManagement.getInstance().esercizi
        val adapter = ExercisesCarouselAdapter(exercises)
        recyclerViewCarousel.adapter = adapter
        recyclerViewCarousel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition =
                        layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                        recyclerView.smoothScrollToPosition(firstVisibleItemPosition)
                    }
                }
            }
        })

        // New Workout Button
        val createNewWorkoutButton = view.findViewById<MaterialButton>(R.id.createNewWorkoutButton)
        createNewWorkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_workoutFragment)
        }
        DataManagement.getInstance().getLastWorkout()?.let { updateLastWorkoutUI(it, view) }


    }

    @SuppressLint("MissingInflatedId")
    private fun updateLastWorkoutUI(lastWorkout: Allenamento, view: View) {
        val lastWorkoutDetails = view.findViewById<LinearLayout>(R.id.lastWorkoutDetails)
        lastWorkoutDetails.removeAllViews() // Clear any existing views
        view.findViewById<TextView>(R.id.tvData).text = lastWorkout.data.toString()

        lastWorkout.divisione.listaEsercizi.forEach { exercise ->
            val exerciseView =
                layoutInflater.inflate(R.layout.item_exercise, lastWorkoutDetails, false)
            val exerciseNameTextView = exerciseView.findViewById<TextView>(R.id.tvExerciseName)
            val exerciseWeightTextView = exerciseView.findViewById<TextView>(R.id.tvPesoValue)
            val exerciseSeriesTextView = exerciseView.findViewById<TextView>(R.id.tvSerieValue)
            val exerciseRepsTextView = exerciseView.findViewById<TextView>(R.id.tvRepsValue)

            exerciseNameTextView.text = exercise.Esercizio.nome
            exerciseWeightTextView.text = "${exercise.peso} kg"
            exerciseSeriesTextView.text = "${exercise.serie} S"
            exerciseRepsTextView.text = "${exercise.ripetizioni} R"

            lastWorkoutDetails.addView(exerciseView)
        }
    }

}
