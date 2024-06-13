package com.example.fitnessbodybuilding

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

class DataManagement private constructor(
    val db: DatabaseReference = FirebaseDatabase.getInstance().reference,
    var utenti: MutableList<User> = mutableListOf(),
    var esercizi: MutableList<Esercizio> = mutableListOf(),
    var allenamenti: MutableList<Allenamento> = mutableListOf(),
    var scheda: Scheda = Scheda(),
    private var schede: MutableList<Scheda> = mutableListOf(),
    var loggato: User? = null
) {


    private fun loadUsersFromDb() {
        println("Starting to load users from database...")

        db.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("DataSnapshot exists: ${dataSnapshot.exists()}")  // Verifica se il DataSnapshot esiste
                if (!dataSnapshot.exists()) {
                    println("No data found for 'users' node.")
                    return
                }

                val usersList = mutableListOf<User>()
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

    private fun init() {
        loadUsersFromDb()
        loadExerciseFromDb()
        loadSchedaFromDb()
    }

    fun saveScheda() {
        scheda.user = loggato!!
        scheda.id = getInstance().schede.get(getInstance().schede.size - 1).id + 1
        for (i in 0 until getInstance().scheda.Esercizi.size) {
            db.child("scheda")
                .child((getInstance().schede.get(getInstance().schede.size - 1).id + 1).toString())
                .setValue(getInstance().scheda)
                .addOnSuccessListener {
                    println("fatto")
                }
                .addOnFailureListener { e ->
                    println("Error adding user: $e")
                }
        }
    }

    fun loadSchedaFromDb() {
        db.child("scheda").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exList = mutableListOf<Scheda>()
                for (userSnapshot in dataSnapshot.children) {
                    val scd = userSnapshot.getValue(Scheda::class.java)
                    if (scd != null) {
                        exList.add(scd)
                    }
                }
                schede = exList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error reading exercises: ${databaseError.message}")
            }
        })
    }

    fun reDo() {
        utenti = mutableListOf()
        esercizi = mutableListOf()
        allenamenti = mutableListOf()
        scheda = Scheda()
        schede = mutableListOf()
        loggato = null
        init()
    }

    fun getSchedaUser() {
        for (s in schede) {
            if (s.user.id == loggato?.id) {
                getInstance().scheda = s
                getInstance().scheda.id =
                    getInstance().schede.get(getInstance().schede.size - 1).id
            }
        }
    }

    fun deleteScheda() {
        val schedaRef = db.child("scheda").child(scheda.id.toString()) // Get the specific node

        schedaRef.removeValue()
            .addOnSuccessListener {
                println("Elemento con ID ${scheda.id} eliminato con successo.")
            }
            .addOnFailureListener { error ->
                println("Errore nell'eliminazione dell'elemento: ${error.message}")
            }
    }

    fun loadExerciseFromDb() {
        db.child("Esercizio").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exList = mutableListOf<Esercizio>()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadAllenamenti() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Allenamenti")

        // Aggiungiamo un listener per gestire le risposte dal database
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allenamenti = mutableListOf<Allenamento>()

                for (childSnapshot in snapshot.children) {
                    val id = childSnapshot.child("id").getValue(Int::class.java) ?: continue
                    val data = childSnapshot.child("data").getValue(Long::class.java)
                        ?.let { LocalDate.ofEpochDay(it) } ?: continue

                    // Recuperiamo l'utente associato all'allenamento
                    val utenteId =
                        childSnapshot.child("utente").child("id").getValue(Int::class.java)
                            ?: continue
                    val username =
                        childSnapshot.child("utente").child("username").getValue(String::class.java)
                            ?: ""
                    val password =
                        childSnapshot.child("utente").child("password").getValue(String::class.java)
                            ?: ""
                    val email =
                        childSnapshot.child("utente").child("email").getValue(String::class.java)
                            ?: ""
                    val peso =
                        childSnapshot.child("utente").child("peso").getValue(Int::class.java) ?: 0
                    val altezza =
                        childSnapshot.child("utente").child("altezza").getValue(Int::class.java)
                            ?: 0
                    val utente = User(utenteId, username, password, email, peso, altezza)

                    // Recuperiamo la divisione dell'allenamento
                    val divisione = Divisione()

                    // Recuperiamo gli esercizi della divisione
                    val eserciziSnapshot = childSnapshot.child("divisione").child("listaEsercizi")
                    for (esercizioSnapshot in eserciziSnapshot.children) {
                        val esercizioId = esercizioSnapshot.child("esercizio").child("id")
                            .getValue(Int::class.java) ?: continue
                        val nome = esercizioSnapshot.child("esercizio").child("nome")
                            .getValue(String::class.java) ?: ""
                        val descrizione = esercizioSnapshot.child("esercizio").child("descrizione")
                            .getValue(String::class.java) ?: ""
                        val kcal = esercizioSnapshot.child("esercizio").child("kcal")
                            .getValue(Int::class.java) ?: 0
                        val esercizio = Esercizio(esercizioId, nome, descrizione, kcal)
                        val serie = esercizioSnapshot.child("serie").getValue(Int::class.java) ?: 0
                        val ripetizioni =
                            esercizioSnapshot.child("ripetizioni").getValue(Int::class.java) ?: 0
                        val peso = esercizioSnapshot.child("peso").getValue(Int::class.java) ?: 0
                        val svolge = Svolge(esercizio, serie, ripetizioni, peso)
                        divisione.listaEsercizi.add(svolge)
                    }

                    // Creiamo l'oggetto Allenamento e lo aggiungiamo alla lista
                    val allenamento = Allenamento(id, divisione, data, utente)
                    allenamenti.add(allenamento)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.message}")
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

}
