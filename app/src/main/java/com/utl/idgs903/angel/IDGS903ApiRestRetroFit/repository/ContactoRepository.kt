package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.repository

import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.api.ApiService
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.model.Contacto
import java.io.IOException
import retrofit2.HttpException
import com.google.gson.JsonSyntaxException

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
}

class ContactoRepository(private val apiService: ApiService) {

    suspend fun getContactos(): ResultState<List<Contacto>> = safeApiCall {
        val response = apiService.getContactos()
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw Exception("Error del servidor: ${response.code()}")
        }
    }

    suspend fun createContacto(contacto: Contacto): ResultState<Contacto> = safeApiCall {
        val response = apiService.createContacto(contacto)
        if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Exception("Error del servidor: ${response.code()}")
        }
    }

    suspend fun updateContacto(id: Int, contacto: Contacto): ResultState<Contacto> = safeApiCall {
        val response = apiService.updateContacto(id, contacto)
        if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Exception("Error del servidor: ${response.code()}")
        }
    }

    suspend fun deleteContacto(id: Int): ResultState<Unit> = safeApiCall {
        val response = apiService.deleteContacto(id)
        if (!response.isSuccessful) {
            throw Exception("Error del servidor: ${response.code()}")
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultState<T> {
        return try {
            ResultState.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultState.Error("No existe conexión a Internet o el servidor no responde.")
                is HttpException -> ResultState.Error("Error interno del servidor HTTP ${throwable.code()}")
                is JsonSyntaxException -> ResultState.Error("Error al convertir los datos JSON.")
                else -> ResultState.Error(throwable.message ?: "Error desconocido.")
            }
        }
    }
}
