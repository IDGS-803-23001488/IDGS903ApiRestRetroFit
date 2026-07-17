package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.navigation
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.screens.AddEditScreen
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.screens.MainScreen
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.viewmodel.ContactoViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object AddEdit : Screen("add_edit?id={id}&nombre={nombre}&telefono={telefono}&email={email}") {
        fun createRoute(id: Int = -1, nombre: String = "", telefono: String = "", email: String = ""): String {
            return "add_edit?id=$id&nombre=$nombre&telefono=$telefono&email=$email"
        }
    }
}

@Composable
fun AppNavigation(viewModel: ContactoViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, 
        startDestination = Screen.Main.route,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300))
        }
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                onNavigateToAdd = { navController.navigate(Screen.AddEdit.createRoute()) },
                onNavigateToEdit = { contacto ->
                    navController.navigate(
                        Screen.AddEdit.createRoute(
                            id = contacto.id,
                            nombre = contacto.nombre,
                            telefono = contacto.telefono,
                            email = contacto.email ?: ""
                        )
                    )
                }
            )
        }
        
        composable(
            route = Screen.AddEdit.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType; defaultValue = -1 },
                navArgument("nombre") { type = NavType.StringType; defaultValue = "" },
                navArgument("telefono") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val telefono = backStackEntry.arguments?.getString("telefono") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            
            AddEditScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                contactoId = if (id != -1) id else null,
                initialNombre = nombre,
                initialTelefono = telefono,
                initialEmail = email
            )
        }
    }
}
