package br.com.tiagosinatra.mytasklist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tiagosinatra.mytasklist.R
import br.com.tiagosinatra.mytasklist.databinding.FragmentDoneBinding
import br.com.tiagosinatra.mytasklist.databinding.FragmentLoginBinding
import br.com.tiagosinatra.mytasklist.helper.FirebaseHelper
import br.com.tiagosinatra.mytasklist.model.Task
import br.com.tiagosinatra.mytasklist.ui.adapter.TaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class DoneFragment : Fragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private val taskList = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTasks()
    }

    private fun getTasks() {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?:"")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        taskList.clear()
                        for (snap in snapshot.children) {
                            val task = snap.getValue(Task::class.java) as Task
                            if (task.status == 2) taskList.add(task)
                        }
                        binding.textInfo.text = ""
                        taskList.reverse()
                        initAdapter()
                    } else {
                        binding.textInfo.text = "Nenhuma tarefa cadastrada"
                    }
                    binding.progressBar.isVisible = false
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun initAdapter(){
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.setHasFixedSize(true)
        taskAdapter = TaskAdapter(requireContext(), taskList) { task, select ->
            optionSelected(task, select)
        }
        binding.rvTask.adapter = taskAdapter
    }

    private fun optionSelected(task: Task, select: Int){
        when(select){
            TaskAdapter.SELECT_REMOVE -> {
                deleteTask(task)
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }TaskAdapter.SELECT_BACK -> {
                task.status = 1
                updateTask(task)
            }
        }
    }

    private fun updateTask(task: Task) {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser()?: "")
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(requireContext(), "Tarefa atualizada om sucesso", Toast.LENGTH_SHORT)
                } else {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), "Erro ao salvar tarefa", Toast.LENGTH_SHORT)
                }
            }.addOnFailureListener {
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Erro ao salvar tarefa", Toast.LENGTH_SHORT)
            }
    }

    private fun deleteTask(task: Task){
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .removeValue()
        taskList.remove(task)
        taskAdapter.notifyDataSetChanged()
    }
}
