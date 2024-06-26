package com.example.fitnessbodybuilding

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Workout : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        DataManagement.getInstance().setSchedaUser()
        if (DataManagement.getInstance().scheda.Esercizi.isEmpty()) {
            val button: MaterialButton = view.findViewById(R.id.btnC)
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
            val dayTextView = TextView(requireContext()).apply {
                text = "Giorno ${i + 1}"
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                textSize = 24f
                setTypeface(null, Typeface.BOLD)
            }
            container.addView(dayTextView)

            val tableLayout = TableLayout(requireContext()).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(16, 16, 16, 16)
            }

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

            for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                val exerciseNameRow = TableRow(requireContext()).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    gravity = Gravity.CENTER
                }

                val exerciseNameTextView = TextView(requireContext()).apply {
                    text = DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].Esercizio.nome
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
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

                val valueTextViewSerie = TextView(requireContext()).apply {
                    text = "Serie: " + DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].serie.toString()
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textSize = 12f
                    setPadding(0, 0, 0, 0)
                }

                tableRow.addView(valueTextViewSerie)

                val valueTextViewRipetizioni = TextView(requireContext()).apply {
                    text = "Ripetizioni: " + DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].ripetizioni.toString()
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textSize = 12f
                    setPadding(0, 0, 0, 0)
                }

                tableRow.addView(valueTextViewRipetizioni)

                val pesoTextView = TextView(requireContext()).apply {
                    text = "Peso: " + DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].peso.toString()
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textSize = 12f
                    setPadding(0, 0, 0, 0)
                }

                tableRow.addView(pesoTextView)
                tableLayout.addView(tableRow)
            }
            container.addView(tableLayout)

            val startWorkoutButton = MaterialButton(requireContext()).apply {
                text = "Inizia allenamento"
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                    setMargins(0, 16, 0, 16)
                }
                setOnClickListener {
                    val intent = Intent(requireContext(), PaginaAllenamentoActivity::class.java)
                    intent.putExtra("chiaveIntero", i)
                    requireContext().startActivity(intent)
                }
            }
            container.addView(startWorkoutButton)
        }

        val deleteButton = MaterialButton(requireContext()).apply {
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
                clearFragmentContent(this@Workout)
                DataManagement.getInstance().loadSchedaFromDb()
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView3).selectedItemId = R.id.home
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
