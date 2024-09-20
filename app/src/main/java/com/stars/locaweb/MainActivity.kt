package com.stars.locaweb

import CalendarScreen
import ComposeEmailScreen
import EmailDetailScreen
import EmailListScreen
import EmailViewModel
import LocawebTheme
import LoginScreen
import RegisterScreen
import SentEmailScreen
import SettingsScreen
import SpamScreen
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            LocawebTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val emailViewModel: EmailViewModel = viewModel()

                    AppNavigation(
                        navController = navController,
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { newTheme -> isDarkTheme = newTheme },
                        emailViewModel = emailViewModel
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    emailViewModel: EmailViewModel
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("emailList") {
            EmailListScreen(navController)
        }
        composable("composeEmail") {
            ComposeEmailScreen(navController, emailViewModel)
        }
        composable("sentEmail") {
            SentEmailScreen(navController, emailViewModel)
        }
        composable("calendar") {
            CalendarScreen(navController)
        }

        composable("settings") {
            SettingsScreen(navController, isDarkTheme, onThemeChange)
        }
        composable("spamEmail") {
            SpamScreen(navController, emailViewModel)
        }
        composable("emailDetail/{emailItemJson}") { backStackEntry ->
            val emailItemJson = backStackEntry.arguments?.getString("emailItemJson")
            emailItemJson?.let {
                EmailDetailScreen(navController, it)
            }
        }
        composable("main") {
            MainScreen(navController)
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Bem-vindo ao aplicativo!")
    }
}