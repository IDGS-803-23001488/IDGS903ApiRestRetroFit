package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.api

import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.model.Contacto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/contactos")
    suspend fun getContactos(): Response<List<Contacto>>

    @POST("api/contactos")
    suspend fun createContacto(@Body contacto: Contacto): Response<Contacto>

    @PUT("api/contactos/{id}")
    suspend fun updateContacto(@Path("id") id: Int, @Body contacto: Contacto): Response<Contacto>

    @DELETE("api/contactos/{id}")
    suspend fun deleteContacto(@Path("id") id: Int): Response<Unit>
}
