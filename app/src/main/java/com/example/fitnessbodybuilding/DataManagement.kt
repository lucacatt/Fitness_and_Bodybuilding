package com.example.fitnessbodybuilding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DataManagement private constructor(
    val db: DatabaseReference = FirebaseDatabase.getInstance().reference,
    var utenti: MutableList<User> = mutableListOf(),
    var esercizi: MutableList<Esercizio> = mutableListOf(),
    var loggato: User? = null
) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        loadUsersFromDb()
    }

    private fun loadUsersFromDb() {
        db.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usersList = mutableListOf<User>()
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        usersList.add(user)
                    }
                }
                utenti = usersList
                println("Users loaded: $utenti")
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
                utenti.add(user)
            }
            .addOnFailureListener { e ->
                println("Error adding user: $e")
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        loadUsersFromDb()
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

    private fun loadExerciseFromDb() {
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
}
