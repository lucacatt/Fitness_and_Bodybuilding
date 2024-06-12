package com.example.fitnessbodybuilding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EsercizioAdapter(
    private val esercizi: List<Esercizio>,
    private val listener: OnEsercizioClickListener
) :
    RecyclerView.Adapter<EsercizioAdapter.EsercizioViewHolder>() {

    // ViewHolder class to hold references to views in the item layout
    class EsercizioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val esercizioTextView: TextView = itemView.findViewById(R.id.esercizioTextView)
        val serieTextView: TextView = itemView.findViewById(R.id.serieTextView)
        val ripetizioniTextView: TextView = itemView.findViewById(R.id.ripetizioniTextView)
        val recuperoTextView: TextView = itemView.findViewById(R.id.recuperoTextView)
    }

    // Create a new ViewHolder for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EsercizioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_esercizio, parent, false) // Use your item layout
        return EsercizioViewHolder(itemView)
    }

    // Bind data from the Esercizio object to the views in the ViewHolder
    override fun onBindViewHolder(holder: EsercizioViewHolder, position: Int) {
        val esercizio = esercizi[position]
        holder.esercizioTextView.text = esercizio.nome
        holder.serieTextView.text = "Kcal: ${esercizio.kcal}"
        holder.ripetizioniTextView.text = "Descrizione: ${esercizio.descrizione}"
        holder.recuperoTextView.text = "Recupero: 60 sec"
        // Set the click listener on the item view
        holder.itemView.setOnClickListener {
            listener.onEsercizioClick(esercizio.nome) // Pass the exercise name as a String
        }
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int {
        return esercizi.size
    }
}