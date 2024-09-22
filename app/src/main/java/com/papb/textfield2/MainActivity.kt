package com.papb.textfield2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papb.textfield2.ui.theme.TextField2Theme
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextField2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CardGreetingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CardGreetingScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var inputName by remember { mutableStateOf("") }
    var inputNim by remember { mutableStateOf("") }

    // UI state untuk button
    val isFormComplete = inputName.isNotBlank() && inputNim.isNotBlank()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Halo $name!", fontSize = 24.sp)
                Text(text = "NIM Anda: $nim", fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = inputName,
            onValueChange = { inputName = it },
            label = { Text("Masukkan nama Anda") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Nama")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = inputNim,
            onValueChange = { inputNim = it },
            label = { Text("Masukkan NIM Anda") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "NIM")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                name = inputName
                nim = inputNim
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormComplete, // Tombol hanya aktif jika form lengkap
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.Gray, // Warna abu-abu saat dinonaktifkan
                disabledContentColor = Color.LightGray
            )
        ) {
            Text("Kirim")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button with long press detection using pointerInput
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (isFormComplete) {
                                Toast.makeText(context, "Nama: $name, NIM: $nim", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
                .padding(vertical = 8.dp)
                .background(
                    if (isFormComplete) MaterialTheme.colorScheme.primary else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                "Tampilkan Nama & NIM",
                color = if (isFormComplete) MaterialTheme.colorScheme.onPrimary else Color.LightGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardGreetingPreview() {
    TextField2Theme {
        CardGreetingScreen()
    }
}
