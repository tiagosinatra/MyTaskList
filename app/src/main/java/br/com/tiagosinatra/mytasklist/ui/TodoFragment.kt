package br.com.tiagosinatra.mytasklist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.tiagosinatra.mytasklist.R
import br.com.tiagosinatra.mytasklist.databinding.FragmentLoginBinding
import br.com.tiagosinatra.mytasklist.databinding.FragmentTodoBinding
import com.google.firebase.auth.FirebaseAuth

class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiClicks()
    }

    private fun initiClicks(){
        binding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }
}