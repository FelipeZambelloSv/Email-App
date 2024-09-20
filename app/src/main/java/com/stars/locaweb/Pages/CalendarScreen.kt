import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavController, viewModel: CalendarViewModel = viewModel()) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var taskText by remember { mutableStateOf(TextFieldValue("")) }
    val tasks by viewModel.tasks.collectAsState()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Select Date")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exibir tarefas da data selecionada
            Text(
                text = "Tasks for $selectedDate",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )

            tasks[selectedDate]?.let { taskList ->
                LazyColumn {
                    items(taskList) { task ->
                        Text(text = "- $task", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } ?: Text(text = "No tasks for this date", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = taskText,
                onValueChange = { taskText = it },
                label = { Text("New Task") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Button(
                onClick = {
                    if (taskText.text.isNotEmpty()) {
                        viewModel.addTask(selectedDate, taskText.text)
                        taskText = TextFieldValue("") // Limpa o campo de texto
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Add Task")
            }
        }
    }
}