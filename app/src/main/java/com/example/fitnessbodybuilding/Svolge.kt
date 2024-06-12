package com.example.fitnessbodybuilding

data class Svolge (
    val Esercizio: Esercizio,
    var serie: Int = 0,
    val ripetizioni: Int = 0,
    val peso: Int = 0
) {
}