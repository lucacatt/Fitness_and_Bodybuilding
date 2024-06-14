package com.example.fitnessbodybuilding

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup

class PaginaAllenamentoActivity : AppCompatActivity() {

    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var interoRicevuto = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_allenamento)

        // Recupera l'intero passato con l'intent
        interoRicevuto = intent.getIntExtra("chiaveIntero", 0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.startStop).setOnClickListener { startStopTimer() }
        findViewById<Button>(R.id.resetButton).setOnClickListener { resetTimer() }
        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        // Aggiungi la tabella dinamicamente
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        generateTablesWithEx(tableLayout, interoRicevuto)
    }

    private fun generateTablesWithEx(container: TableLayout, index: Int) {
        container.removeAllViews()

        // Aggiunge l'intestazione del giorno
        val dayTableRow = TableRow(this).apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
        }

        val dayTextView = TextView(this).apply {
            text = "Giorno ${index + 1}"
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
        }

        dayTableRow.addView(dayTextView)
        container.addView(dayTableRow)

        // Crea una nuova riga per l'intestazione
        val headerRow = TableRow(this).apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
        }

        // Aggiunge l'intestazione alla tabella
        val headerTextView1 = TextView(this).apply {
            text = "Esercizio"
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }

        headerRow.addView(headerTextView1)

        val headerTextView2 = TextView(this).apply {
            text = "Serie"
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }

        headerRow.addView(headerTextView2)

        val headerTextView3 = TextView(this).apply {
            text = "Ripetizioni"
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }

        headerRow.addView(headerTextView3)

        val headerTextView4 = TextView(this).apply {
            text = "Peso"
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }

        headerRow.addView(headerTextView4)
        container.addView(headerRow)

        // Aggiunge righe e dati alla tabella
        val exercises = DataManagement.getInstance().scheda.Esercizi[index].listaEsercizi
        if (exercises.isNotEmpty()) {
            val exercise = exercises[0] // Prende solo il primo esercizio della lista

            val exerciseTableRow = TableRow(this).apply {
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER
            }

            val exerciseNameTextView = TextView(this).apply {
                text = exercise.Esercizio.nome
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                ) // Imposta la larghezza della colonna
            }

            exerciseTableRow.addView(exerciseNameTextView)

            val seriesTextView = TextView(this).apply {
                text = "Serie: ${exercise.serie}"
                gravity = Gravity.CENTER
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                ) // Imposta la larghezza della colonna
                textSize = 12f
            }

            exerciseTableRow.addView(seriesTextView)

            val repetitionsTextView = TextView(this).apply {
                text = "Ripetizioni: ${exercise.ripetizioni}"
                gravity = Gravity.CENTER
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                ) // Imposta la larghezza della colonna
                textSize = 12f
            }

            exerciseTableRow.addView(repetitionsTextView)

            val weightTextView = TextView(this).apply {
                text = "Peso: ${exercise.peso}"
                gravity = Gravity.CENTER
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                ) // Imposta la larghezza della colonna
                textSize = 12f
            }

            exerciseTableRow.addView(weightTextView)

            container.addView(exerciseTableRow)
        }
    }

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        findViewById<TextView>(R.id.timeTV).text = getTimeStringFromDouble(time)
    }

    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        Log.d("PaginaAllenamento", "Avvio del timer")
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        findViewById<Button>(R.id.startStop).text = "Stop"
        timerStarted = true
    }

    private fun stopTimer() {
        Log.d("PaginaAllenamento", "Fermata del timer")
        stopService(serviceIntent)
        findViewById<Button>(R.id.startStop).text = "Inizia"
        timerStarted = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            Log.d("PaginaAllenamento", "Tempo ricevuto: $time")
            findViewById<TextView>(R.id.timeTV).text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

}