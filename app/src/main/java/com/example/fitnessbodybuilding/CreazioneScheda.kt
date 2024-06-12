package com.example.fitnessbodybuilding

import android.graphics.Typeface
import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.example.fitnessbodybuilding.databinding.FragmentCreazioneSchedaBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreazioneScheda.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreazioneScheda : Fragment() {
    // TODO: Rename and change types of parameters
    private val args: CreazioneSchedaArgs by navArgs()
    private lateinit var binding: FragmentCreazioneSchedaBinding
    var index: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_creazione_scheda, container, false)

        val button: Button = view.findViewById(R.id.btnCadenza)
        val tableContainer: ViewGroup = view.findViewById(R.id.tableContainer)

        button.setOnClickListener {
            generateTables(tableContainer, view)
        }
        val esercizio = args.esercizio
        index = args.giorno
        if (esercizio != "0") {
            val es = searchExercise(esercizio)
            val sv = Svolge(es)
            DataManagement.getInstance().scheda.Esercizi[index].listaEsercizi.add(sv)
            generateTablesWithEx(tableContainer, view)
        }
        return view
    }

    private fun generateTables(container: ViewGroup, view: View) {
        container.removeAllViews()
        val giorni: EditText = view.findViewById(R.id.txtSplit)
        if (giorni.text.toString().isNotEmpty()) {
            for (i in 0 until giorni.text.toString().toInt()) {
                DataManagement.getInstance().scheda.Esercizi.add(Divisione())
            }
            for (i in 0 until DataManagement.getInstance().scheda.Esercizi.size) {
                // Crea una nuova TableLayout
                val tableLayout = TableLayout(requireContext())
                tableLayout.layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                tableLayout.setPadding(16, 16, 16, 16)

                // Aggiunge l'intestazione alla tabella
                val headerRow = TableRow(requireContext())
                headerRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                headerRow.gravity = Gravity.CENTER

                val headerTextView = TextView(requireContext())
                headerTextView.text = "Giorno " + (i + 1)
                headerTextView.gravity = Gravity.CENTER
                headerTextView.setPadding(8, 8, 8, 8)
                headerTextView.textSize = 18f
                headerTextView.setTypeface(null, Typeface.BOLD)

                headerRow.addView(headerTextView)
                tableLayout.addView(headerRow)

                // Aggiunge righe e dati alla tabella
                for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                    val tableRow = TableRow(requireContext())
                    tableRow.layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    tableRow.gravity = Gravity.CENTER

                    val textView = TextView(requireContext())
                    textView.text =
                        DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.get(j).Esercizio.nome
                    textView.gravity = Gravity.CENTER
                    textView.setPadding(8, 8, 8, 8)

                    tableRow.addView(textView)
                    tableLayout.addView(tableRow)
                }

                // Aggiunge la tabella al container
                container.addView(tableLayout)

                // Crea un nuovo bottone centrato
                val button = Button(requireContext())
                button.text = "Aggiungi esercizio"
                val buttonParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                buttonParams.gravity = Gravity.CENTER
                button.layoutParams = buttonParams
                button.setPadding(16, 16, 16, 16)

                button.setOnClickListener(Navigation.createNavigateOnClickListener(
                    R.id.action_creazioneScheda_to_aggiungiEsercizioFragment,
                    Bundle().apply {
                        putInt("giorno", i) // Passa l'indice del giorno
                    }
                ))

                // Aggiunge il bottone al container
                container.addView(button)
            }
        }

        // Crea un nuovo bottone centrato
        val button = Button(requireContext())
        button.text = "Salva"
        val buttonParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        buttonParams.gravity = Gravity.CENTER
        button.layoutParams = buttonParams
        button.setPadding(16, 36, 16, 16)

        // Aggiunge un listener al bottone "Salva"
        button.setOnClickListener {
            // Gestisci l'evento di clic qui

        }

        // Aggiunge il bottone al container
        container.addView(button)
    }

    private fun generateTablesWithEx(container: ViewGroup, view: View) {
        container.removeAllViews()
        for (i in 0 until DataManagement.getInstance().scheda.Esercizi.size) {
            // Crea una nuova TableLayout
            val tableLayout = TableLayout(requireContext())
            tableLayout.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            tableLayout.setPadding(16, 16, 16, 16)

            // Aggiunge l'intestazione alla tabella
            val headerRow1 = TableRow(requireContext())
            headerRow1.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            headerRow1.gravity = Gravity.CENTER

            val headerTextView1 = TextView(requireContext())
            headerTextView1.text = "Esercizio"
            headerTextView1.gravity = Gravity.CENTER
            headerTextView1.setPadding(8, 8, 8, 8)
            headerTextView1.textSize = 18f
            headerTextView1.setTypeface(null, Typeface.BOLD)

            headerRow1.addView(headerTextView1)

            val headerTextView2 = TextView(requireContext())
            headerTextView2.text = "Serie"
            headerTextView2.gravity = Gravity.CENTER
            headerTextView2.setPadding(8, 8, 8, 8)
            headerTextView2.textSize = 18f
            headerTextView2.setTypeface(null, Typeface.BOLD)

            headerRow1.addView(headerTextView2)

            tableLayout.addView(headerRow1)

            // Aggiunge righe e dati alla tabella
            for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                val tableRow = TableRow(requireContext())
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                tableRow.gravity = Gravity.CENTER

                val textView = TextView(requireContext())
                textView.text =
                    DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.get(j).Esercizio.nome
                textView.gravity = Gravity.CENTER
                textView.setPadding(8, 8, 8, 8)
                textView.layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                ) // Imposta la larghezza della colonna

                tableRow.addView(textView)

                // Aggiungi il pulsante "+" alla riga
                val plusButton = Button(requireContext())
                plusButton.text = "+"
                plusButton.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                plusButton.setPadding(4, 4, 4, 4) // Riduci la spaziatura del pulsante
                plusButton.setOnClickListener {
                    val valueTextView = tableRow.findViewWithTag<TextView>("valueTextView")
                    var currentValue = valueTextView?.text.toString().toInt()
                    currentValue++
                    valueTextView?.text = currentValue.toString()
                }

                // Aggiungi il pulsante "-" alla riga
                val minusButton = Button(requireContext())
                minusButton.text = "-"
                minusButton.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                minusButton.setPadding(4, 4, 4, 4) // Riduci la spaziatura del pulsante
                minusButton.setOnClickListener {
                    val valueTextView = tableRow.findViewWithTag<TextView>("valueTextView")
                    var currentValue = valueTextView?.text.toString().toInt()
                    if (currentValue > 0) {
                        currentValue--
                        valueTextView?.text = currentValue.toString()
                    }
                }

                // Aggiungi la TextView con il numero
                val valueTextView = TextView(requireContext())
                valueTextView.text = "0"
                valueTextView.gravity = Gravity.CENTER
                valueTextView.tag = "valueTextView" // Aggiungi un tag alla TextView
                valueTextView.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                // Aggiungi i pulsanti e la TextView alla riga
                tableRow.addView(plusButton)
                tableRow.addView(valueTextView)
                tableRow.addView(minusButton)

                tableLayout.addView(tableRow)
            }

            // Aggiunge la tabella al container
            container.addView(tableLayout)

            // Crea un nuovo bottone centrato
            val button = Button(requireContext())
            button.text = "Aggiungi esercizio"
            val buttonParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            buttonParams.gravity = Gravity.CENTER
            button.layoutParams = buttonParams
            button.setPadding(16, 16, 16, 16)

            button.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_creazioneScheda_to_aggiungiEsercizioFragment,
                Bundle().apply {
                    putInt("giorno", i) // Passa l'indice del giorno
                }
            ))

            // Aggiunge il bottone al container
            container.addView(button)
        }

        // Crea un nuovo bottone centrato
        val button = Button(requireContext())
        button.text = "Salva"
        val buttonParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        buttonParams.gravity = Gravity.CENTER
        button.layoutParams = buttonParams
        button.setPadding(16, 36, 16, 16)

        // Aggiunge un listener al bottone "Salva"
        button.setOnClickListener {
            val tablesList = mutableListOf<List<Int>>()

            for (i in 0 until container.childCount) {
                val child = container.getChildAt(i)
                if (child is TableLayout) {
                    val tableValues = mutableListOf<Int>()
                    for (j in 0 until child.childCount) {
                        val row = child.getChildAt(j)
                        if (row is TableRow) {
                            for (k in 0 until row.childCount) {
                                val element = row.getChildAt(k)
                                if (element is TextView && element.tag == "valueTextView") {
                                    tableValues.add(element.text.toString().toInt())
                                }
                            }
                        }
                    }
                    tablesList.add(tableValues)
                }
            }
            for (i in 0 until DataManagement.getInstance().scheda.Esercizi.size) {
                for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                    DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.get(j).serie =
                        tablesList.get(i).get(j)
                }
            }
        }

        // Aggiunge il bottone al container
        container.addView(button)
    }


    private fun clearFragmentContent(fragment: Fragment) {
        val view = fragment.view
        if (view is ViewGroup) {
            view.removeAllViews()
        }
    }

    private fun searchExercise(es: String): Esercizio {
        val esercizi = DataManagement.getInstance().esercizi
        for (i in 0 until esercizi.size) {
            if (esercizi.get(i).nome == es)
                return esercizi.get(i)
        }
        return Esercizio()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreazioneScheda.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreazioneScheda().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
