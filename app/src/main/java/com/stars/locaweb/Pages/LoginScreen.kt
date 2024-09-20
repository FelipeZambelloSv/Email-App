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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val db = AppDatabase.getDatabase(context)
                val userDao = db.userDao()

                CoroutineScope(Dispatchers.IO).launch {
                    val user = userDao.getUser(email, password)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            navController.navigate("emailList") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            errorMessage = "Credenciais inválidas"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }


        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text("Não tem uma conta? Cadastre-se")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
