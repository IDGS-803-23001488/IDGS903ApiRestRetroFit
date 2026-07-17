package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.repository.ResultState
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.viewmodel.ContactoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    viewModel: ContactoViewModel,
    onNavigateBack: () -> Unit,
    contactoId: Int? = null,
    initialNombre: String = "",
    initialTelefono: String = "",
    initialEmail: String = ""
) {
    var nombre by remember { mutableStateOf(initialNombre) }
    var telefono by remember { mutableStateOf(initialTelefono) }
    var email by remember { mutableStateOf(initialEmail) }
    
    var nombreError by remember { mutableStateOf(false) }
    var telefonoError by remember { mutableStateOf(false) }
    var serverError by remember { mutableStateOf<String?>(null) }

    val operationState by viewModel.operationState.collectAsState()
    val isEditing = contactoId != null

    LaunchedEffect(operationState) {
        if (operationState is ResultState.Success) {
            viewModel.resetOperationState()
            onNavigateBack()
        } else if (operationState is ResultState.Error) {
            serverError = (operationState as ResultState.Error).message
            viewModel.resetOperationState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Contacto" else "Nuevo Contacto", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { 
                        nombre = it
                        nombreError = false
                    },
                    label = { Text("Nombre completo *") },
                    isError = nombreError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        errorBorderColor = MaterialTheme.colorScheme.error
                    )
                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { 
                        telefono = it
                        telefonoError = false
                    },
                    label = { Text("Teléfono *") },
                    isError = telefonoError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        errorBorderColor = MaterialTheme.colorScheme.error
                    )
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico (Opcional)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = {
                        val isNombreValid = nombre.trim().isNotEmpty()
                        val isTelefonoValid = telefono.trim().isNotEmpty()
                        
                        if (isNombreValid && isTelefonoValid) {
                            if (isEditing) {
                                viewModel.updateContacto(contactoId!!, nombre.trim(), telefono.trim(), email.trim())
                            } else {
                                viewModel.addContacto(nombre.trim(), telefono.trim(), email.trim())
                            }
                        } else {
                            nombreError = !isNombreValid
                            telefonoError = !isTelefonoValid
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = if (isEditing) "Actualizar" else "Guardar",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }

            if (operationState is ResultState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
    
    if (serverError != null) {
        AlertDialog(
            onDismissRequest = { serverError = null },
            title = { Text("Error") },
            text = { Text(serverError!!) },
            containerColor = MaterialTheme.colorScheme.surface,
            confirmButton = {
                TextButton(onClick = { serverError = null }) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            }
        )
    }
}
