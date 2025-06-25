package com.sanjangeet.bulletinboard.screens.main

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.user.PasswordChangeRequest
import com.sanjangeet.bulletinboard.api.models.user.UserUpdateRequest
import com.sanjangeet.bulletinboard.database.AppDatabase
import com.sanjangeet.bulletinboard.navigation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import com.sanjangeet.bulletinboard.DarkModeManager

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, appState: AppState) {
    val context = LocalContext.current
    val appDatabase = remember { AppDatabase.getInstance(context) }
    val isDarkMode by DarkModeManager.isDarkMode(context).collectAsState(initial = false)

    var fullName by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val response = Client.userService.userInfo("Bearer ${appState.token}")
            fullName = response.full_name
        } catch (e: Exception) {
            // Do nothing for now
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 32.sp
        )
        HorizontalDivider()
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = Client.userService.updateUserInfo(
                            UserUpdateRequest(fullName),
                            "Bearer ${appState.token}"
                        )
                        if (response.isSuccessful) {
                            appState.isSnackbarMessageError = false
                            appState.snackbarHostState.showSnackbar("Updated successfully")
                            appState.isSnackbarMessageError = true
                        } else {
                            appState.snackbarHostState.showSnackbar("Something went wrong")
                        }
                    } catch (e: Exception) {
                        appState.snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
            )
        ) {
            Text("Update")
        }
        HorizontalDivider()
        OutlinedTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Old Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (oldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (oldPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (oldPasswordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (newPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (newPasswordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password") },
            modifier = Modifier.fillMaxWidth(),
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
            }
        )
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    if (newPassword == confirmPassword) {
                        try {
                            val response = Client.userService.changePassword(PasswordChangeRequest(oldPassword, newPassword), "Bearer ${appState.token}")
                            if (response.isSuccessful) {
                                appState.isSnackbarMessageError = false
                                appState.snackbarHostState.showSnackbar("Password changed successfully")
                                appState.isSnackbarMessageError = true
                            } else {
                                try {
                                    val errorBody = response.errorBody()?.string()
                                    val json = JSONObject(errorBody ?: "")
                                    val error = json.opt("error")
                                    when (error) {
                                        is String -> appState.snackbarHostState.showSnackbar(error)
                                        is org.json.JSONArray -> for (i in 0 until error.length()) {
                                            appState.snackbarHostState.showSnackbar(error.getString(i))
                                        }
                                        else -> appState.snackbarHostState.showSnackbar("Something went wrong")
                                    }
                                } catch (e: Exception) {
                                    appState.snackbarHostState.showSnackbar("Something went wrong")
                                }
                            }
                        } catch (e: Exception) {
                            appState.snackbarHostState.showSnackbar("Something went wrong")
                        }
                    } else {
                        appState.snackbarHostState.showSnackbar("Passwords don't match")
                        newPassword = ""
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
            Text("Change Password")
        }
        HorizontalDivider()
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    DarkModeManager.toggleDarkMode(context)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
            )
        ) {
            Text(if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode")
        }
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    appDatabase.tokenDao().delete()
                    appState.token = ""
                    appState.currentDestination = Destination.Login
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
            )
        ) {
            Text("Logout")
        }
    }
}
