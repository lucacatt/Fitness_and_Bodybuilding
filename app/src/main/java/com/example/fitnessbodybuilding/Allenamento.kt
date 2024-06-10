package com.example.fitnessbodybuilding

import android.provider.ContactsContract.RawContacts.Data

data class Allenamento (
    val id:Int =0,
    val id_user: Int =0,
    val id_scheda: Int=0,
    val data: Data
) {
}