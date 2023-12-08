package com.example.homework15

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework15.databinding.FragmentMainChatPageBinding
import kotlinx.coroutines.launch

class MainChatPageFragment :
    BaseFragment<FragmentMainChatPageBinding>(FragmentMainChatPageBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatRecycleAdapter

    override fun setUp() {
        setUpChatRecycleAdapter()
    }

    override fun setUpOnClickListeners() {
        setUpIconClickListener()
        setUpTextWatcher()
    }

    private fun setUpChatRecycleAdapter() {
        adapter = ChatRecycleAdapter()
        binding.chatRecycleAdapter.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRecycleAdapter.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { userList ->
                    adapter.submitList(userList)
                }
            }
        }
    }

    private fun setUpIconClickListener() {
        binding.ivIcon.setOnClickListener {
            filterList(binding.editTextFilter.text?.toString())
        }
    }

    private fun setUpTextWatcher() {
        binding.editTextFilter.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                filterList(null)
            }
        }
    }

    private fun filterList(filterText: String?) {
        viewModel.filterList(filterText)
    }
}