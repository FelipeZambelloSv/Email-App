import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.stars.locaweb.Utils.EmailItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEmailScreen(navController: NavController, emailViewModel: EmailViewModel = viewModel()) {
    val sentEmailList by emailViewModel.getSentEmails().collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sent Emails") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
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
            items(sentEmailList) { email ->
                SendEmailListItem(email, onClick = {
                    val emailJson = Gson().toJson(email)
                    navController.navigate("emailDetail/${Uri.encode(emailJson)}")
                })
            }
        }
    }
}

@Composable
fun SendEmailListItem(email: EmailItem, onClick: () -> Unit) {
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