import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp)
        ) {
            Text(text = "Dark Theme")
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeChange(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sair")
            }
        }
    }
}
