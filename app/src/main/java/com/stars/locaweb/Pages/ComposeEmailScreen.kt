import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stars.locaweb.Utils.EmailItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmailScreen(navController: NavController, emailViewModel: EmailViewModel = viewModel()) {
    var to by remember { mutableStateOf(TextFieldValue("")) }
    var subject by remember { mutableStateOf(TextFieldValue("")) }
    var body by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compose Email") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = to,
                onValueChange = { to = it },
                label = { Text("To") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Body") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    emailViewModel.addSentEmail(
                        EmailItem(
                            subject = subject.text,
                            sender = to.text,
                            preview = body.text.take(50)
                        )
                    )
                    coroutineScope.launch {
                        Toast.makeText(context, "Email sent successfully!", Toast.LENGTH_SHORT).show()
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Send")
            }
        }
    }
}
