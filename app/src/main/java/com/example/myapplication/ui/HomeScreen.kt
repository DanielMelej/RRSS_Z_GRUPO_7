import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R  // importa el paquete R

@Composable
fun HomeScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Logo
            Image(
                painter = painterResource(id = R.drawable.logo_tomatito),
                contentDescription = "Logo Tomatado",
                modifier = Modifier.size(120.dp) // tamaño en pixeles dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Título
            Text(
                text = "TOMATADO",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Subtítulo gracioso
            Text(
                text = "Lanza un Tomatazo! 🍅",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 🔹 Botón principal
            Button(onClick = { /* acción futura */ }) {
                Text("Login")
            }

            Button(onClick = { /* acción futura */ }) {
                Text("Registro")
            }
        }
    }
}