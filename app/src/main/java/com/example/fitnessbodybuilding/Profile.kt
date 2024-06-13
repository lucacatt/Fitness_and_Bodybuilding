package com.example.fitnessbodybuilding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnessbodybuilding.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
        loadUserInfo()
        binding.updateButton.setOnClickListener {
            updateUserInfo()
        }
        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun loadUserInfo() {
        val user = DataManagement.getInstance().loggato
        if (user != null) {
            val username = user.username
            val email = user.email
            val height = user.altezza
            val weight = user.peso

            // Popola i campi con le informazioni dell'utente
            binding.usernameEditText.setText(username)
            binding.emailEditText.setText(email)
            binding.heightEditText.setText(height?.toString() ?: "")
            binding.weightEditText.setText(weight?.toString() ?: "")
        } else {
            Log.e("Profile", "No logged in user found")
        }
    }
    private fun updateUserInfo() {
        val username = binding.usernameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val height = binding.heightEditText.text.toString().toIntOrNull()
        val weight = binding.weightEditText.text.toString().toIntOrNull()

        val user = DataManagement.getInstance().loggato
        if (user != null) {
            user.username = username
            user.email = email
            if (height != null && weight != null) {
                user.altezza = height
                user.peso = weight
            }
            DataManagement.getInstance().updateUser(user)
            Toast.makeText(requireContext(), "User updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "No logged in user found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun logout() {
        val view = this.view
        if (view is ViewGroup) {
            view.removeAllViews()
        }
        DataManagement.getInstance().reDo()
        findNavController().navigate(R.id.introductionFragment)
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
