package com.example.fitnessbodybuilding

data class Scheda(
    val id: Int = 0,
    val user: User = User(),
    var Esercizi: MutableList<Divisione> = mutableListOf(),
) {
}