package com.sanjangeet.bulletinboard.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.auth.SignupRequestOtpRequest
import com.sanjangeet.bulletinboard.api.models.user.PasswordResetRequest
import com.sanjangeet.bulletinboard.navigation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

@Composable
fun ResetPasswordScreen(modifier: Modifier = Modifier, appState: AppState) {
    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

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
            value = otp,
            onValueChange = { otp = it },
            label = { Text("OTP") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                TextButton(
                    modifier = Modifier
                        .padding(2.dp),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val response =
                                Client.authService.requestOtp(SignupRequestOtpRequest(email))
                            if (response.isSuccessful) {
                                appState.isSnackbarMessageError = false
                                appState.snackbarHostState.showSnackbar("OTP sent to $email")
                                appState.isSnackbarMessageError = true
                            } else {
                                when (response.code()) {
                                    400 -> appState.snackbarHostState.showSnackbar("Invalid email")
                                    429 -> appState.snackbarHostState.showSnackbar("Please wait for 2 minutes before trying again")
                                    else -> appState.snackbarHostState.showSnackbar("Something went wrong")
                                }
                            }
                        }
                    }
                ) {
                    Text("Send", fontSize = 15.sp)
                }
            },
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
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    if (password == confirmPassword) {
                        try {
                            val response = Client.userService.resetPassword(PasswordResetRequest(email, otp, password))

                            if (response.isSuccessful) {
                                appState.isSnackbarMessageError = false
                                appState.snackbarHostState.showSnackbar("Password reset successful")
                                appState.isSnackbarMessageError = true
                            } else {
                                val errorBody = response.errorBody()?.string()
                                try {
                                    val json = JSONObject(errorBody ?: "")
                                    val error = json.opt("error")
                                    when (error) {
                                        is String -> appState.snackbarHostState.showSnackbar(error)
                                        is org.json.JSONArray -> for (i in 0 until error.length()) {
                                            appState.snackbarHostState.showSnackbar(error.getString(i))
                                        }
                                        else -> appState.snackbarHostState.showSnackbar("Something went wrong")
                                    }
                                } catch (ex: Exception) {
                                    appState.snackbarHostState.showSnackbar("Something went wrong")
                                }
                            }
                            password = ""
                            confirmPassword = ""
                        } catch (e: Exception) {
                            appState.snackbarHostState.showSnackbar("Something went wrong")
                        }
                    } else {
                        appState.snackbarHostState.showSnackbar("Passwords do not match")
                        password = ""
                        confirmPassword = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
            )
        ) {
            Text("Reset Password", fontSize = 16.sp)
        }
        Spacer(Modifier.height(8.dp))
        TextButton(
            onClick = { appState.currentDestination = Destination.Login }
        ) {
            Text("Cancel", fontSize = 16.sp)
        }
    }
}
