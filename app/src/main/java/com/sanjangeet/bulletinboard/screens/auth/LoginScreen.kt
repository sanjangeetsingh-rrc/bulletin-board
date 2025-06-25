package com.sanjangeet.bulletinboard.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjangeet.bulletinboard.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.sanjangeet.bulletinboard.navigation.Destination
import com.sanjangeet.bulletinboard.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.auth.LoginRequest
import com.sanjangeet.bulletinboard.database.entities.TokenEntity

@Composable
fun LoginScreen(modifier: Modifier = Modifier, appState: AppState) {
    val context = LocalContext.current
    val appDatabase = remember { AppDatabase.getInstance(context) }
    var displayScreen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val tokenEntity = withContext(Dispatchers.IO) {
            appDatabase.tokenDao().get()
        }
        val refreshToken = tokenEntity?.refresh

        if (refreshToken != null) {
            appState.currentDestination = Destination.Main
        } else {
            displayScreen = true
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = displayScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            var response =
                                Client.authService.login(LoginRequest(email, password))
                            appDatabase.tokenDao()
                                .insert(TokenEntity(refresh = response.refresh))
                            appState.token = response.access
                            appState.currentDestination = Destination.Main
                        } catch (e: Exception) {
                            if (e.message.toString() == "HTTP 400 Bad Request") {
                                appState.snackbarHostState.showSnackbar("Invalid Credentials")
                                password = ""
                            } else {
                                appState.snackbarHostState.showSnackbar("Something went wrong")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
                )
            ) {
                Text("Login", fontSize = 16.sp)
            }
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { appState.currentDestination = Destination.Signup },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                )
            ) {
                Text("Sign Up", fontSize = 16.sp)
            }
            Spacer(Modifier.height(4.dp))
            TextButton(onClick = { appState.currentDestination = Destination.ResetPassword }) {
                Text("Forgot Password?", fontSize = 15.sp)
            }
        }
    }
}
