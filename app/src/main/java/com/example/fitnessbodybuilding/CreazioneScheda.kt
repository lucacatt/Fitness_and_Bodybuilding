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
            DataManagement.getInstance().scheda.Esercizi = mutableListOf()
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
            // Aggiunge l'intestazione del giorno
            val dayTextView = TextView(requireContext())
            dayTextView.text = "Giorno ${i + 1}"
            dayTextView.gravity = Gravity.CENTER
            dayTextView.setPadding(8, 8, 8, 8)
            dayTextView.textSize = 24f
            dayTextView.setTypeface(null, Typeface.BOLD)

            // Aggiunge l'intestazione al container
            container.addView(dayTextView)

            // Crea una nuova TableLayout
            val tableLayout = TableLayout(requireContext())
            tableLayout.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            tableLayout.setPadding(16, 16, 16, 16)

            // Aggiunge l'intestazione alla tabella
            var headerRow = TableRow(requireContext())
            headerRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            headerRow.gravity = Gravity.CENTER

            val headerTextView1 = TextView(requireContext())
            headerTextView1.text = "Esercizio"
            headerTextView1.gravity = Gravity.CENTER
            headerTextView1.setPadding(8, 8, 8, 8)
            headerTextView1.textSize = 18f
            headerTextView1.setTypeface(null, Typeface.BOLD)

            headerRow.addView(headerTextView1)

            tableLayout.addView(headerRow)

            headerRow = TableRow(requireContext())
            headerRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            headerRow.gravity = Gravity.CENTER

            val headerTextView2 = TextView(requireContext())
            headerTextView2.text = "Serie"
            headerTextView2.gravity = Gravity.CENTER
            headerTextView2.setPadding(8, 8, 8, 8)
            headerTextView2.textSize = 18f
            headerTextView2.setTypeface(null, Typeface.BOLD)

            headerRow.addView(headerTextView2)

            val headerTextView3 = TextView(requireContext())
            headerTextView3.text = "Ripetizioni"
            headerTextView3.gravity = Gravity.CENTER
            headerTextView3.setPadding(8, 8, 8, 8)
            headerTextView3.textSize = 18f
            headerTextView3.setTypeface(null, Typeface.BOLD)

            headerRow.addView(headerTextView3)

            val headerTextView4 = TextView(requireContext())
            headerTextView4.text = "Peso"
            headerTextView4.gravity = Gravity.CENTER
            headerTextView4.setPadding(8, 8, 8, 8)
            headerTextView4.textSize = 18f
            headerTextView4.setTypeface(null, Typeface.BOLD)

            headerRow.addView(headerTextView4)

            tableLayout.addView(headerRow)
            // Aggiunge righe e dati alla tabella
            for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                val exerciseNameRow = TableRow(requireContext())
                exerciseNameRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                exerciseNameRow.gravity = Gravity.CENTER

                val exerciseNameTextView = TextView(requireContext())
                exerciseNameTextView.text =
                    DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].Esercizio.nome
                exerciseNameTextView.gravity = Gravity.CENTER
                exerciseNameTextView.setPadding(8, 8, 8, 8)
                exerciseNameTextView.layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                ) // Imposta la larghezza della colonna

                exerciseNameRow.addView(exerciseNameTextView)
                tableLayout.addView(exerciseNameRow)

                val tableRow = TableRow(requireContext())
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                tableRow.gravity = Gravity.CENTER

                // Aggiungi il pulsante "+" alla riga per Serie
                val plusButtonSerie = Button(requireContext())
                plusButtonSerie.text = "+"
                plusButtonSerie.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                plusButtonSerie.textSize = 12f // Riduce la dimensione del testo
                plusButtonSerie.setPadding(0, 0, 0, 0) // Rimuove il padding
                plusButtonSerie.setOnClickListener {
                    val valueTextView = tableRow.findViewWithTag<TextView>("valueTextViewSerie")
                    var currentValue = valueTextView?.text.toString().toInt()
                    currentValue++
                    valueTextView?.text = currentValue.toString()
                }

                // Aggiungi il pulsante "-" alla riga per Serie
                val minusButtonSerie = Button(requireContext())
                minusButtonSerie.text = "-"
                minusButtonSerie.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                minusButtonSerie.textSize = 12f // Riduce la dimensione del testo
                minusButtonSerie.setPadding(0, 0, 0, 0) // Rimuove il padding
                minusButtonSerie.setOnClickListener {
                    val valueTextView = tableRow.findViewWithTag<TextView>("valueTextViewSerie")
                    var currentValue = valueTextView?.text.toString().toInt()
                    if (currentValue > 0) {
                        currentValue--
                        valueTextView?.text = currentValue.toString()
                    }
                }

                // Aggiungi la TextView con il numero per Serie
                val valueTextViewSerie = TextView(requireContext())
                valueTextViewSerie.text = "0"
                valueTextViewSerie.gravity = Gravity.CENTER
                valueTextViewSerie.tag = "valueTextViewSerie" // Aggiungi un tag alla TextView
                valueTextViewSerie.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                valueTextViewSerie.textSize = 12f // Riduce la dimensione del testo
                valueTextViewSerie.setPadding(0, 0, 0, 0) // Rimuove il padding

                // Aggiungi i pulsanti e la TextView alla riga per Serie
                tableRow.addView(plusButtonSerie)
                tableRow.addView(valueTextViewSerie)
                tableRow.addView(minusButtonSerie)

                // Aggiungi il pulsante "+" alla riga per Ripetizioni
                val plusButtonRipetizioni = Button(requireContext())
                plusButtonRipetizioni.text = "+"
                plusButtonRipetizioni.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                plusButtonRipetizioni.textSize = 12f // Riduce la dimensione del testo
                plusButtonRipetizioni.setPadding(0, 0, 0, 0) // Rimuove il padding
                plusButtonRipetizioni.setOnClickListener {
                    val valueTextView =
                        tableRow.findViewWithTag<TextView>("valueTextViewRipetizioni")
                    var currentValue = valueTextView?.text.toString().toInt()
                    currentValue++
                    valueTextView?.text = currentValue.toString()
                }

                // Aggiungi il pulsante "-" alla riga per Ripetizioni
                val minusButtonRipetizioni = Button(requireContext())
                minusButtonRipetizioni.text = "-"
                minusButtonRipetizioni.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                minusButtonRipetizioni.textSize = 12f // Riduce la dimensione del testo
                minusButtonRipetizioni.setPadding(0, 0, 0, 0) // Rimuove il padding
                minusButtonRipetizioni.setOnClickListener {
                    val valueTextView =
                        tableRow.findViewWithTag<TextView>("valueTextViewRipetizioni")
                    var currentValue = valueTextView?.text.toString().toInt()
                    if (currentValue > 0) {
                        currentValue--
                        valueTextView?.text = currentValue.toString()
                    }
                }

                // Aggiungi la TextView con il numero per Ripetizioni
                val valueTextViewRipetizioni = TextView(requireContext())
                valueTextViewRipetizioni.text = "0"
                valueTextViewRipetizioni.gravity = Gravity.CENTER
                valueTextViewRipetizioni.tag =
                    "valueTextViewRipetizioni" // Aggiungi un tag alla TextView
                valueTextViewRipetizioni.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                valueTextViewRipetizioni.textSize = 12f // Riduce la dimensione del testo
                valueTextViewRipetizioni.setPadding(0, 0, 0, 0) // Rimuove il padding

                // Aggiungi i pulsanti e la TextView alla riga per Ripetizioni
                tableRow.addView(plusButtonRipetizioni)
                tableRow.addView(valueTextViewRipetizioni)
                tableRow.addView(minusButtonRipetizioni)

                // Aggiungi un EditText per il peso
                val pesoEditText = EditText(requireContext())
                pesoEditText.hint = "Peso"
                pesoEditText.gravity = Gravity.CENTER
                pesoEditText.inputType = InputType.TYPE_CLASS_NUMBER
                pesoEditText.layoutParams = TableRow.LayoutParams(
                    0, // Imposta la larghezza della colonna a 0
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f // Imposta il peso della colonna a 1
                )
                pesoEditText.textSize = 12f // Riduce la dimensione del testo
                pesoEditText.setPadding(0, 0, 0, 0) // Rimuove il padding

                // Aggiungi il campo di inserimento del peso alla riga
                tableRow.addView(pesoEditText)

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
            val tablesList = mutableListOf<List<Triple<Int, Int, Int>>>()

            for (i in 0 until container.childCount) {
                val child = container.getChildAt(i)
                if (child is TableLayout) {
                    val tableValues = mutableListOf<Triple<Int, Int, Int>>()
                    for (j in 0 until child.childCount) {
                        val row = child.getChildAt(j)
                        if (row is TableRow) {
                            var serieValue = 0
                            var ripetizioniValue = 0
                            var pesoValue = 0
                            for (k in 0 until row.childCount) {
                                val element = row.getChildAt(k)
                                if (element is TextView && element.tag == "valueTextViewSerie") {
                                    serieValue = element.text.toString().toInt()
                                }
                                if (element is TextView && element.tag == "valueTextViewRipetizioni") {
                                    ripetizioniValue = element.text.toString().toInt()
                                }
                                if (element is EditText) {
                                    pesoValue = if (element.text.toString().isNotEmpty()) {
                                        element.text.toString().toInt()
                                    } else {
                                        0
                                    }
                                }
                            }
                            tableValues.add(Triple(serieValue, ripetizioniValue, pesoValue))
                        }
                    }
                    tablesList.add(tableValues)
                }
            }

            for (i in 0 until DataManagement.getInstance().scheda.Esercizi.size) {
                for (j in 0 until DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi.size) {
                    val (serie, ripetizioni, peso) = tablesList[i][j]
                    DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].serie = serie
                    DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].ripetizioni = ripetizioni
                    DataManagement.getInstance().scheda.Esercizi[i].listaEsercizi[j].peso = peso
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
