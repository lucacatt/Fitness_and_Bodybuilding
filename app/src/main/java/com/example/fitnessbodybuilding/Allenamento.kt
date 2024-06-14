package com.example.fitnessbodybuilding

import java.time.LocalDate

class Allenamento (
    val id: Int = 0,
    val divisione: Divisione = Divisione(),
    val data: String = "",
    val utente: User = User()
){
}