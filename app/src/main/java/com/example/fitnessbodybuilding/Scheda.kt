package com.example.fitnessbodybuilding

data class Scheda(
    var id: Int = 0,
    var user: User = User(),
    var Esercizi: MutableList<Divisione> = mutableListOf(),
) {
}