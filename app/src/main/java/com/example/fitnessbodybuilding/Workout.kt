package com.example.fitnessbodybuilding

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Workout.newInstance] factory method to
 * create an instance of this fragment.
 */
class Workout : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        DataManagement.getInstance().getSchedaUser()
        if (DataManagement.getInstance().scheda.Esercizi.isEmpty()) {
            val button: Button = view.findViewById(R.id.btnC)
            button.setOnClickListener {
                clearFragmentContent(this)
                findNavController().navigate(R.id.creazione_schedaFragment)
            }
        } else {
            val tableContainer: ViewGroup = view.findViewById(R.id.tbContainer)
            generateTablesWithEx(tableContainer, view)
        }

        return view
    }

    private fun generateTablesWithEx(container: ViewGroup, view: View) {
        container.removeAllViews()
        for (i in 0 until DataManagement.getInstance().scheda.Esercizi.size) {
            // Aggiunge l'intestazione del giorno
            val dayTextView = TextView(requireContext()).apply {
                text = "Giorno ${i + 1}"
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                textSize = 24f
                setTypeface(null, Typeface.BOLD)
            }
            // Aggiunge l'intestazione al container
            container.addView(dayTextView)

            // Crea una nuova TableLayout
            val tableLayout = TableLayout(requireContext()).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(16, 16, 16, 16)
            }

            // Aggiunge l'intestazione alla tabella
            val headerRow1 = TableRow(requireContext()).apply {
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER
            }

            val headerTextView1 = TextView(requireContext()).apply {
                text = "Esercizio"
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                textSize = 18f
                setTypeface(null, Typeface.BOLD)
            }

            headerRow1.addView(headerTextView1)
            tableLayout.addView(headerRow1)

            val headerRow2 = TableRow(requireContext()).apply {
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER
            }

            val headerTextView2 = TextView(requireContext()).apply {
                text = "Serie"
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                textSize = 18f
                setTypeface(null, Typeface.BOLD)
            }

            headerRow2.addView(headerTextView2)

            val headerTextView3 = TextView(requireContext()).apply {
                text = "Ripetizioni"
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                textSize = 18f
                setTypeface(null, Typeface.BOLD)
            }

            headerRow2.addView(headerTextView3)

            val headerTextView4 = TextView(requireContext()).apply {
                text = "Peso"
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                textSize = 18f
                setTypeface(null, Typeface.BOLD)
            }

            headerRow2.addView(headerTextView4)
            tableLayout.addView(headerRow2)

            // Aggiunge righe e dati alla tabella
            for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                val exerciseNameRow = TableRow(requireContext()).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER
                }

                val exerciseNameTextView = TextView(requireContext()).apply {
                    text =
                        DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].Esercizio.nome
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    ) // Imposta la larghezza della colonna
                }

                exerciseNameRow.addView(exerciseNameTextView)
                tableLayout.addView(exerciseNameRow)

                val tableRow = TableRow(requireContext()).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER
                }

                // Aggiungi la TextView con il numero per Serie
                val valueTextViewSerie = TextView(requireContext()).apply {
                    text =
                        "Serie: " + DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].serie.toString()
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(
                        0, // Imposta la larghezza della colonna a 0
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f // Imposta il peso della colonna a 1
                    )
                    textSize = 12f // Riduce la dimensione del testo
                    setPadding(0, 0, 0, 0) // Rimuove il padding
                }

                tableRow.addView(valueTextViewSerie)

                // Aggiungi la TextView con il numero per Ripetizioni
                val valueTextViewRipetizioni = TextView(requireContext()).apply {
                    text =
                        "Ripetizioni: " + DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].ripetizioni.toString()
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(
                        0, // Imposta la larghezza della colonna a 0
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f // Imposta il peso della colonna a 1
                    )
                    textSize = 12f // Riduce la dimensione del testo
                    setPadding(0, 0, 0, 0) // Rimuove il padding
                }

                tableRow.addView(valueTextViewRipetizioni)

                // Aggiungi una TextView per il peso
                val pesoTextView = TextView(requireContext()).apply {
                    text =
                        "Peso: " + DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].peso.toString()
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(
                        0, // Imposta la larghezza della colonna a 0
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f // Imposta il peso della colonna a 1
                    )
                    textSize = 12f // Riduce la dimensione del testo
                    setPadding(0, 0, 0, 0) // Rimuove il padding
                }

                // Aggiungi il campo di visualizzazione del peso alla riga
                tableRow.addView(pesoTextView)

                tableLayout.addView(tableRow)
            }

            // Aggiunge la tabella al container
            container.addView(tableLayout)
        }

        // Aggiunge il pulsante Elimina scheda
        val deleteButton = Button(requireContext()).apply {
            text = "Elimina scheda"
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            setPadding(16, 16, 16, 16)
            setOnClickListener {
                DataManagement.getInstance().deleteScheda()
            }
        }
        container.addView(deleteButton)
    }


    private fun clearFragmentContent(fragment: Fragment) {
        val view = fragment.view
        if (view is ViewGroup) {
            view.removeAllViews()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Workout.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Workout().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}