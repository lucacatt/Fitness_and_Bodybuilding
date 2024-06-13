package com.example.fitnessbodybuilding

import java.time.LocalDate

class Allenamento (
    val id: Int,
    val divisione: Divisione,
    val data: LocalDate,
    val utente: User
){
}