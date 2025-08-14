package com.example.firstcompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Login")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.primary,
                titleContentColor = colorScheme.onPrimary
            )
        )
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text("Enter Username") },
                isError = usernameError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
            if (usernameError.isNotEmpty()) {
                Text(usernameError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Enter Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            if (passwordError.isNotEmpty()) {
                Text(passwordError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            Button(
                onClick = {
                    usernameError = when {
                        username.isBlank() -> "Username required"
                        username.length < 4 -> "Username must be at least 4 characters"
                        !username.matches(Regex("^[a-zA-Z0-9]+$")) -> "Username must be letters or numbers only"
                        else -> ""
                    }
                    passwordError = when {
                        password.isBlank() -> "Password required"
                        password.length < 6 -> "Password must be at least 6 characters"
                        !password.matches(Regex(".*\\d.*")) -> "Password must contain at least one digit"
                        else -> ""
                    }
                    if (usernameError.isEmpty() && passwordError.isEmpty()) {
                        Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
                        // Navigate to HomeActivity
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Please fix errors", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Login")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
