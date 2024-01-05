package com.brian.hola.features.auth.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brian.hola.R
import com.brian.hola.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.SignupBtn.setOnClickListener {

            findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
        }

        binding.loginLinkTxt.setOnClickListener {

            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        return view
    }

}