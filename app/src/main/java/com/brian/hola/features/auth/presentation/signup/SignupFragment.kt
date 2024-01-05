package com.brian.hola.features.auth.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.brian.hola.R
import com.brian.hola.databinding.FragmentSignupBinding
import com.brian.hola.features.auth.domain.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginLinkTxt.setOnClickListener {

            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.SignupBtn.setOnClickListener {

            authViewModel.signUp(
                binding.signupEmail.text.toString(),
                binding.signupPassword.text.toString(),
                binding.signupConfirmPassword.text.toString(),
                binding.signupUsername.text.toString(),
            )
        }
        collectLatestStates()
    }

    private fun collectLatestStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                authViewModel.authUiState.collectLatest { state ->
                    binding.SignupBtn.isEnabled = !state.isLoading

                    if (state.isSuccessful) {
                        findNavController().navigate(
                            SignupFragmentDirections.actionSignupFragmentToHomeFragment(
                                state.userId!!
                            )
                        )
                    }

                    state.error?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                    authViewModel.resetState()
                }
            }
        }
    }

}