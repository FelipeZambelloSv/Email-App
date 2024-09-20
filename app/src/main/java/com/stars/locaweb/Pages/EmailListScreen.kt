import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.stars.locaweb.Utils.EmailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailListScreen(navController: NavController) {
    val emailList = listOf(
        EmailItem("Bem vindo ao seu Email", "fiap@service.com", "olaa! Obrigado por se inscrever :)"),
        EmailItem("Seu email esta pronto", "fiap@service.com", "Seu email já esta pronto pra ser usado."),
        EmailItem("Senha atualizada", "fiap@service.com", "Sua senha já foi redefinida com sucesso."),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inbox") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("composeEmail")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Compose Email",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("sentEmail")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Sent Email",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("spamEmail")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Spam Emails",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("calendar")
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("settings")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(emailList) { email ->
                EmailListItem(email, onClick = {
                    val emailJson = Gson().toJson(email)
                    navController.navigate("emailDetail/${Uri.encode(emailJson)}")
                })
            }
        }
    }
}

@Composable
fun EmailListItem(email: EmailItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = email.subject, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = email.sender, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = email.preview, maxLines = 2, fontSize = 14.sp)
        }
    }
}
