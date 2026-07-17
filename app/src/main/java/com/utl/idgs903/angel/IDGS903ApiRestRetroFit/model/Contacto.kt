package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.model

import com.google.gson.annotations.SerializedName

data class Contacto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("email") val email: String? = null
)
