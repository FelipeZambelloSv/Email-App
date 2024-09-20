import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class EmailDetail(val subject: String, val sender: String, val preview: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailScreen(navController: NavController, emailItemJson: String) {
    val decodedEmailItemJson = Uri.decode(emailItemJson)
    val type: Type = object : TypeToken<EmailDetail>() {}.type
    val email: EmailDetail? = Gson().fromJson(decodedEmailItemJson, type)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Email Detail") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("composeEmail")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Reply",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            email?.let {
                Text(
                    text = "From: ${it.sender}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Subject: ${it.subject}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it.preview,
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            } ?: run {
                Text(text = "No email selected", fontSize = 16.sp)
            }
        }
    }
}

