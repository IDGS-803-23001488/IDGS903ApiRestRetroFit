package com.utl.idgs903.angel.IDGS903ApiRestRetroFit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.navigation.AppNavigation
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.theme.IDGS903ApiRestRetroFitTheme
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.viewmodel.ContactoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ContactoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IDGS903ApiRestRetroFitTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
