package com.example.homework15

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ChatViewModel : ViewModel() {
    private val _dataFlow = MutableStateFlow<List<UserInformation>>(emptyList())
    val dataFlow: StateFlow<List<UserInformation>> = _dataFlow.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getConversations()
                Log.d("ChatViewModel", "Fetched data: $response")
                _dataFlow.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ChatViewModel", "Error fetching data: $e")
            }
        }
    }

    fun filterList(filterText: String?) {
        viewModelScope.launch {
            if (filterText.isNullOrBlank()) {
                fetchData()
            } else {
                val filteredList = _dataFlow.value.filter { user ->
                    user.owner.lowercase(Locale.ROOT)
                        .contains(filterText.lowercase(Locale.ROOT))
                }
                _dataFlow.value = filteredList
            }
        }
    }
}
