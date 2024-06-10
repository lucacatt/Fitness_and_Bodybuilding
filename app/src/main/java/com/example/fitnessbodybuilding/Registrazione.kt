package com.example.fitnessbodybuilding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Registrazione.newInstance] factory method to
 * create an instance of this fragment.
 */
class Registrazione : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var dataManagement: DataManagement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        dataManagement = DataManagement()

        findViewById<Button>(R.id.btnConferma).setOnClickListener {
            try {
                if (checkTexts()) {
                    val user = User(
                        id = dataManagement.utenti.size + 1,
                        username = findViewById<EditText>(R.id.txtUser).text.toString(),
                        password = findViewById<EditText>(R.id.txtPass).text.toString(),
                        email = findViewById<EditText>(R.id.txtEmail).text.toString()
                    )
                    dataManagement.insertUser(user)
                    Toast.makeText(this, "Registrazione avvenuta", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Campi mancanti o errati", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                println("Insertion failed: ${e.message}")
            }
        }
    }

    fun checkTexts(): Boolean {
        return !findViewById<EditText>(R.id.txtEmail).text.toString()
            .isBlank() && !findViewById<EditText>(R.id.txtUser).text.toString()
            .isBlank() && !findViewById<EditText>(R.id.txtPass).text.toString().isBlank()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrazione, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Registrazione.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Registrazione().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}