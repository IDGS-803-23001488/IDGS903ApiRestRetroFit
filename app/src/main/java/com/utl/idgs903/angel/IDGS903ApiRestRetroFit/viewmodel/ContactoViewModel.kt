package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.api.RetrofitClient
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.model.Contacto
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.repository.ContactoRepository
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.repository.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactoViewModel : ViewModel() {
    private val repository = ContactoRepository(RetrofitClient.apiService)

    private val _contactosState = MutableStateFlow<ResultState<List<Contacto>>>(ResultState.Loading)
    val contactosState: StateFlow<ResultState<List<Contacto>>> = _contactosState.asStateFlow()

    private val _operationState = MutableStateFlow<ResultState<Unit>?>(null)
    val operationState: StateFlow<ResultState<Unit>?> = _operationState.asStateFlow()

    init {
        getContactos()
    }

    fun getContactos() {
        viewModelScope.launch {
            _contactosState.value = ResultState.Loading
            _contactosState.value = repository.getContactos()
        }
    }

    fun addContacto(nombre: String, telefono: String, email: String) {
        viewModelScope.launch {
            _operationState.value = ResultState.Loading
            val result = repository.createContacto(Contacto(nombre = nombre, telefono = telefono, email = email))
            if (result is ResultState.Success) {
                _operationState.value = ResultState.Success(Unit)
                getContactos() // Recargar lista
            } else if (result is ResultState.Error) {
                _operationState.value = ResultState.Error(result.message)
            }
        }
    }

    fun updateContacto(id: Int, nombre: String, telefono: String, email: String) {
        viewModelScope.launch {
            _operationState.value = ResultState.Loading
            val result = repository.updateContacto(id, Contacto(id = id, nombre = nombre, telefono = telefono, email = email))
            if (result is ResultState.Success) {
                _operationState.value = ResultState.Success(Unit)
                getContactos() // Recargar lista
            } else if (result is ResultState.Error) {
                _operationState.value = ResultState.Error(result.message)
            }
        }
    }

    fun deleteContacto(id: Int) {
        viewModelScope.launch {
            _operationState.value = ResultState.Loading
            val result = repository.deleteContacto(id)
            if (result is ResultState.Success) {
                _operationState.value = ResultState.Success(Unit)
                getContactos() // Recargar lista
            } else if (result is ResultState.Error) {
                _operationState.value = ResultState.Error(result.message)
            }
        }
    }

    fun resetOperationState() {
        _operationState.value = null
    }
}
