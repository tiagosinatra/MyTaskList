package br.com.tiagosinatra.mytasklist.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import br.com.tiagosinatra.mytasklist.R
import br.com.tiagosinatra.mytasklist.databinding.FragmentLoginBinding
import br.com.tiagosinatra.mytasklist.databinding.FragmentRecoveryAccountBinding
import br.com.tiagosinatra.mytasklist.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryAccountFragment : Fragment() {

    private var _binding: FragmentRecoveryAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoveryAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks(){
        binding.btnEnviar.setOnClickListener{validateData()}
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()){
                binding.progressBar.isVisible = true
                recoveryAccountUser(email)
        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail.", Toast.LENGTH_SHORT)
        }
    }

    private fun recoveryAccountUser(email : String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                   Toast.makeText(requireContext(), "Pronto, acabamos de enviar um link para seu e-mail", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(requireContext(), FirebaseHelper.validError(task.exception?.message ?: ""), Toast.LENGTH_SHORT).show()
                }
                    binding.progressBar.isVisible = false
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}