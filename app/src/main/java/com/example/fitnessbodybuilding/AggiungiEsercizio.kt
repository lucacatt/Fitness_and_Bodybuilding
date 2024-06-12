package com.example.fitnessbodybuilding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbodybuilding.databinding.FragmentAggiungiEsercizioBinding

class AggiungiEsercizio : Fragment(), OnEsercizioClickListener {
    private val args: AggiungiEsercizioArgs by navArgs()
    private lateinit var binding: FragmentAggiungiEsercizioBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EsercizioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAggiungiEsercizioBinding.inflate(inflater, container, false)
        val view = binding.root

        val giorno = args.giorno // Retrieve the 'giorno' argument
        val esercizi = DataManagement.getInstance().esercizi

        // Set up RecyclerView
        recyclerView = binding.viewEs // Assuming you have a RecyclerView in your layout
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EsercizioAdapter(esercizi, this)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreaScheda.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AggiungiEsercizio().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onEsercizioClick(esercizio: String) {
        findNavController().navigate(
            R.id.action_aggiungiEsercizio_to_creazioneScheda,
            Bundle().apply {
                putInt("giorno", args.giorno)
                putString("esercizio", esercizio)
            }
        )
    }
}

interface OnEsercizioClickListener {
    fun onEsercizioClick(esercizio: String)
}


