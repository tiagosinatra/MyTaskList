package br.com.tiagosinatra.mytasklist.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import br.com.tiagosinatra.mytasklist.R
import br.com.tiagosinatra.mytasklist.databinding.FragmentLoginBinding
import br.com.tiagosinatra.mytasklist.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty()){
            if (password.isNotEmpty()){
                binding.progressBar.isVisible = true
                registerUser(email, password)


            } else {
                Toast.makeText(requireContext(), "Informe sua senha.", Toast.LENGTH_SHORT)
            }
        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail.", Toast.LENGTH_SHORT)
        }
    }

    private fun registerUser(email: String, password: String){

    }

}