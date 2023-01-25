package com.fcenesiz.ktorrabbitsapp.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcenesiz.ktorrabbitsapp.data.Rabbit
import com.fcenesiz.ktorrabbitsapp.data.RabbitsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val rabbitsApi: RabbitsApi
) : ViewModel() {

    var state = MutableStateFlow(RabbitState())
        private set

    init {
        getRandomRabbits()
    }

    fun getRandomRabbits() {
        viewModelScope.launch {
            try {
                state.value = state.value.copy(isLoading = true)
                state.value = state.value.copy(
                    rabbit = rabbitsApi.getRandomRabbit(),
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "getRandomRabbits: ", e)
                state.value = state.value.copy(isLoading = false)
            }
        }
    }

    data class RabbitState(
        val rabbit: Rabbit? = null,
        val isLoading: Boolean = false
    )

}