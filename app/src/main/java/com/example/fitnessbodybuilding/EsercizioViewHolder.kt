package com.example.fitnessbodybuilding

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EsercizioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val esercizioTextView: TextView = itemView.findViewById(R.id.esercizioTextView)
    val serieTextView: TextView = itemView.findViewById(R.id.serieTextView)
    // ... (add other views from your item layout)
}