package com.example.fitnessbodybuilding

import android.util.Patterns

data class User(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    var email: String = "",
    var peso: Int=0,
    var altezza: Int=0
) {

}

