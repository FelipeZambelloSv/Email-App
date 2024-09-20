import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stars.locaweb.DataLayer.AppDatabase
import com.stars.locaweb.DataLayer.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirme a Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword && email.isNotEmpty() && name.isNotEmpty()) {
                    val db = AppDatabase.getDatabase(context)
                    val userDao = db.userDao()

                    CoroutineScope(Dispatchers.IO).launch {
                        val newUser = User(name = name, email = email, password = password)
                        userDao.insertUser(newUser)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        }
                    }
                } else {
                    Toast.makeText(context, "Preencha os campos corretamente.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Já tem uma conta? Faça login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}
