package com.example.fitnessbodybuilding

data class Svolge(
    var Esercizio: Esercizio = Esercizio(),
    var serie: Int = 0,
    var ripetizioni: Int = 0,
    var peso: Int = 0
) {
}