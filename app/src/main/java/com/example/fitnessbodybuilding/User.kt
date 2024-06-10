package com.example.fitnessbodybuilding

class User(
    var id: Int,
    var username: String,
    var password: String,
    email: String
) {
    var email: String = email
        set(value) {
            if (isValidEmail(value)) {
                field = value
            } else {
                throw IllegalArgumentException("Invalid email format")
            }
        }

    init {
        if (!isValidEmail(email)) {
            throw IllegalArgumentException("Invalid email format")
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

