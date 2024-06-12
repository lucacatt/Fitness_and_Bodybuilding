package com.example.fitnessbodybuilding

data class Scheda(
    val id: Int = 0,
    val user: User,
    val Esercizi: MutableList<Divisione> = mutableListOf(),
) {
}