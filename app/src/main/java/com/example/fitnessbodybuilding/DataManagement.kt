package com.example.fitnessbodybuilding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataManagement(
    val utenti: MutableList<User> = mutableListOf(),
    val db: DatabaseReference = FirebaseDatabase.getInstance().reference
) {
    fun insertUser(user: User) {
        db.child("users").child(user.id.toString()).setValue(user)
            .addOnSuccessListener {
                println("User added with ID: ${user.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding user: $e")
            }
        utenti.add(user)
    }
}