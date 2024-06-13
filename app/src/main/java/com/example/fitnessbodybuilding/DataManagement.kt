package com.example.fitnessbodybuilding

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DataManagement private constructor(
    val db: DatabaseReference = FirebaseDatabase.getInstance().reference,
    var utenti: MutableList<User> = mutableListOf(),
    var esercizi: MutableList<Esercizio> = mutableListOf(),
    var scheda: Scheda = Scheda(),
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
                loggato=user
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
    }

    fun loadExerciseFromDb() {
        db.child("Esercizio").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot)  {
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
    fun loadAllenamenti(callback: (List<Allenamento>) -> Unit) {
        val db = FirebaseDatabase.getInstance().getReference("Allenamenti")
        val allenamentiList = mutableListOf<Allenamento>()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val id = dataSnapshot.child("id").getValue(Int::class.java) ?: 0
                    val divisioneNome = dataSnapshot.child("divisione").getValue(String::class.java) ?: ""
                    val divisione = Divisione(nome = divisioneNome)
                    val dataString = dataSnapshot.child("data").getValue(String::class.java) ?: ""
                    val data = LocalDate.parse(dataString, formatter)

                    val allenamento = Allenamento(id, divisione, data)
                    allenamentiList.add(allenamento)
                }
                callback(allenamentiList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error loading Allenamenti: $error")
            }
        })
    }
    fun insertAllenamento(allenamento: Allenamento) {
        val db = FirebaseDatabase.getInstance().getReference("Allenamenti")
        db.child(allenamento.id.toString()).setValue(allenamento)
            .addOnSuccessListener {
                println("Allenamento added with ID: ${allenamento.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding Allenamento: $e")
            }
    }

}
