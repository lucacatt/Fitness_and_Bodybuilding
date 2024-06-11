package com.example.fitnessbodybuilding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnessbodybuilding.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataManagement: DataManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManagement = DataManagement.getInstance()
        loadUserInfo()
    }

    private fun loadUserInfo() {

        val username = dataManagement.loggato?.username
        val email = dataManagement.loggato?.email
        val height = dataManagement.loggato?.altezza
        val weight = dataManagement.loggato?.peso

        // Popola i campi con le informazioni dell'utente
        binding.usernameEditText.setText(username)
        binding.emailEditText.setText(email)
        if (height != null && weight!=null ) {
            binding.heightEditText.setText(height)
            binding.weightEditText.setText(weight)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
