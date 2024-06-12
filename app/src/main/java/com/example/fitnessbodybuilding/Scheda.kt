package com.example.fitnessbodybuilding

data class Scheda(
    val id: Int = 0,
    val user: User = User(),
    val Esercizi: MutableList<Divisione> = mutableListOf(),
) {
}