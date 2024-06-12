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
    private var param1: String? = null
    private var param2: String? = null
    private val scheda: Scheda = Scheda()

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
        val view = inflater.inflate(R.layout.fragment_creazione_scheda, container, false)

        val button: Button = view.findViewById(R.id.btnCadenza)
        val tableContainer: ViewGroup = view.findViewById(R.id.tableContainer)

        button.setOnClickListener {
            generateTables(tableContainer, view)
        }
        return view
    }

    private fun generateTables(container: ViewGroup, view: View) {
        container.removeAllViews()
        val giorni: EditText = view.findViewById(R.id.txtSplit)
        if (giorni.text.toString() != "")
            for (i in 1..giorni.text.toString().toInt()) {
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
                headerTextView.text = "Giorno $i"
                headerTextView.gravity = Gravity.CENTER
                headerTextView.setPadding(8, 8, 8, 8)
                headerTextView.textSize = 18f
                headerTextView.setTypeface(null, Typeface.BOLD)

                headerRow.addView(headerTextView)
                tableLayout.addView(headerRow)

                // Aggiunge righe e dati alla tabella
                for (j in scheda.Esercizi) {
                    val tableRow = TableRow(requireContext())
                    tableRow.layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    tableRow.gravity = Gravity.CENTER

                    val textView = TextView(requireContext())
                    textView.text = "Dato $j"
                    textView.gravity = Gravity.CENTER
                    textView.setPadding(8, 8, 8, 8)

                    tableRow.addView(textView)
                    tableLayout.addView(tableRow)
                }

                // Aggiunge la tabella al container
                container.addView(tableLayout)

                // Crea un nuovo bottone centrato
                val button = Button(requireContext())
                button.text = "Bottone $i"
                val buttonParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                buttonParams.gravity = Gravity.CENTER
                button.layoutParams = buttonParams
                button.setPadding(16, 16, 16, 16)

                // Aggiunge il bottone al container
                container.addView(button)
            }
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