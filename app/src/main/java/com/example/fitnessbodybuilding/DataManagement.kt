package com.example.fitnessbodybuilding

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.collections.mutableListOf as mutableListOf1

class DataManagement private constructor(
    val db: DatabaseReference = FirebaseDatabase.getInstance().reference,
    var utenti: MutableList<User> = mutableListOf1(),
    var esercizi: MutableList<Esercizio> = mutableListOf1(),
    var allenamenti: MutableList<Allenamento> = mutableListOf1(),
    var scheda: Scheda = Scheda(),
    var schede: MutableList<Scheda> = mutableListOf1(),
    var loggato: User? = null
) {


    private fun loadUsersFromDb() {
        println("Starting to load users from database...")
        utenti = mutableListOf1()
        db.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("DataSnapshot exists: ${dataSnapshot.exists()}")  // Verifica se il DataSnapshot esiste
                if (!dataSnapshot.exists()) {
                    println("No data found for 'users' node.")
                    return
                }

                val usersList = mutableListOf1<User>()
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        usersList.add(user)
                        println("User added: $user")  // Log dell'utente aggiunto
                    } else {
                        println("User is null for snapshot: ${userSnapshot.key}")  // Log se l'utente è null
                    }
                }
                utenti = usersList
                println("Users loaded successfully: $utenti")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error reading users: ${databaseError.message}")
            }
        })
    }

    fun insertUser(user: User) {
        db.child("users").child(user.id.toString()).setValue(user)
            .addOnSuccessListener {
                println("User added with ID: ${user.id}")
                loggato = user
                utenti.add(user)
            }
            .addOnFailureListener { e ->
                println("Error adding user: $e")
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getKcal(inizio: LocalDate, fine: LocalDate): String {

        var calorieTotali = 0
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        for (allenamento in allenamenti) {
            if (allenamento != null && allenamento.utente.id == loggato!!.id) {
                val dataAllenamento = LocalDate.parse(allenamento.data.toString(), formatter)
                if (!dataAllenamento.isBefore(inizio) && !dataAllenamento.isAfter(fine)) {
                    for (esercizioEseguito in allenamento.divisione.listaEsercizi) {
                        val kcalPerEsercizio = esercizioEseguito.Esercizio.kcal
                        val ripetizioni = esercizioEseguito.ripetizioni
                        val serie = esercizioEseguito.serie
                        calorieTotali += kcalPerEsercizio * ripetizioni * serie
                    }
                }
            }
        }
        return calorieTotali.toString()
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val foundUser = utenti.find { it.email == email && it.password == password }
        if (foundUser != null) {
            loggato = foundUser
            callback(true, null)
        } else {
            callback(false, "Utente non trovato o password errata")
        }
    }

    companion object {
        @Volatile
        private var instance: DataManagement? = null

        fun getInstance(): DataManagement =
            instance ?: synchronized(this) {
                instance ?: DataManagement().also {
                    instance = it
                    it.init()  // Inizializza l'istanza appena creata
                }
            }
    }

    fun init() {
        loadUsersFromDb()
        loadExerciseFromDb()
        loadSchedaFromDb()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loadAllenamenti()
        }
    }

    fun saveScheda() {
        scheda.user = loggato!!
        scheda.id = getInstance().schede.size
        db.child("scheda").child(scheda.id.toString()).setValue(scheda)
            .addOnSuccessListener {
                println("Scheda added successfully")
            }
            .addOnFailureListener { e ->
                println("Error adding scheda: $e")
            }
        loadSchedaFromDb()
    }


    fun loadSchedaFromDb() {
        schede = mutableListOf1()
        db.child("scheda").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val scd = userSnapshot.getValue(Scheda::class.java)
                    if (scd != null) {
                        schede.add(scd)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error reading exercises: ${databaseError.message}")
            }
        })
    }

    fun setSchedaUser() {
        for (s in schede) {
            if (s.user.id == loggato?.id) {
                scheda = s
            }
        }
    }

    fun deleteScheda() {
        val schedaRef = db.child("scheda").child(scheda.id.toString()) // Get the specific node
        scheda = Scheda()
        schedaRef.removeValue()
            .addOnSuccessListener {
                println("Elemento con ID ${scheda.id} eliminato con successo.")
            }
            .addOnFailureListener { error ->
                println("Errore nell'eliminazione dell'elemento: ${error.message}")
            }
    }

    fun loadExerciseFromDb() {
        esercizi = mutableListOf1()
        db.child("Esercizio").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exList = mutableListOf1<Esercizio>()
                for (userSnapshot in dataSnapshot.children) {
                    val esercizio = userSnapshot.getValue(Esercizio::class.java)
                    if (esercizio != null) {
                        exList.add(esercizio)
                    }
                }
                esercizi = exList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error reading exercises: ${databaseError.message}")
            }
        })
    }

    fun updateUser(user: User) {
        db.child("users").child(user.id.toString()).setValue(user)
            .addOnSuccessListener {
                println("User updated with ID: ${user.id}")
                val index = utenti.indexOfFirst { it.id == user.id }
                if (index != -1) {
                    utenti[index] = user
                }
                loggato = user // Update the logged in user
            }
            .addOnFailureListener { e ->
                println("Error updating user: $e")
            }
    }

    fun getLastWorkout(): Allenamento? {
        for (allenamento in allenamenti) {
            if (allenamento.utente.id == loggato?.id) {
                return allenamento
            }
        }
        return null
    }

    fun loadAllenamenti() {
        allenamenti = mutableListOf1()
        db.child("Allenamenti").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exList = mutableListOf1<Allenamento>()
                for (allenamentiSnapshot in dataSnapshot.children) {
                    val allenamento = allenamentiSnapshot.getValue(Allenamento::class.java)
                    if (allenamento != null) {
                        exList.add(allenamento)
                    }
                }
                allenamenti = exList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error reading exercises: ${databaseError.message}")
            }
        })
    }


    fun insertAllenamento(allenamento: Allenamento) {
        val db = FirebaseDatabase.getInstance().getReference("Allenamenti")

        // Convertiamo l'oggetto User in una mappa di valori chiave/valore
        val userMap = mapOf(
            "id" to allenamento.utente.id,
            "username" to allenamento.utente.username,
            "password" to allenamento.utente.password,
            "email" to allenamento.utente.email,
            "peso" to allenamento.utente.peso,
            "altezza" to allenamento.utente.altezza
        )

        // Creiamo la mappa per l'oggetto Allenamento
        val allenamentoMap = mapOf(
            "id" to allenamento.id,
            "divisione" to allenamento.divisione,
            "data" to allenamento.data.toString(),  // Convertiamo LocalDate a String per Firebase
            "utente" to userMap  // Inseriamo la mappa dell'utente nell'oggetto Allenamento
        )

        // Inseriamo l'oggetto Allenamento nel database Firebase
        db.child(allenamento.id.toString()).setValue(allenamentoMap)
            .addOnSuccessListener {
                println("Allenamento added with ID: ${allenamento.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding Allenamento: $e")
            }
    }

    @SuppressLint("NewApi")
    fun fillHighlightedDays(month: Int): MutableList<Int> {
        val giorni: MutableList<Int> = mutableListOf1()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ITALIAN)

        for (training in allenamenti) {
            if (training.utente.id == loggato?.id) {
                val data = LocalDate.parse(training.data, formatter)
                if (data.monthValue == month+1)
                    giorni.add(data.dayOfMonth)
            }
        }
        return giorni
    }


}
