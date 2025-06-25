package com.sanjangeet.bulletinboard.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.screens.MainScreen
import com.sanjangeet.bulletinboard.screens.auth.LoginScreen
import com.sanjangeet.bulletinboard.screens.auth.SignupScreen
import com.sanjangeet.bulletinboard.screens.auth.ResetPasswordScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val appState by remember { mutableStateOf(AppState()) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(appState.snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    containerColor = if (appState.isSnackbarMessageError) Color(0xFFD00020) else Color(0xFF2E7D32),
                    contentColor = Color.White,
                    action = {
                        TextButton(onClick = { data.dismiss() }) {
                            Text("Dismiss", color = Color.White)
                        }
                    }
                ) {
                    Text(data.visuals.message)
                }
            }
        }
    ) {
        when (appState.currentDestination) {
            Destination.Main -> MainScreen(modifier, appState)

            // Auth
            Destination.Login -> LoginScreen(modifier, appState)
            Destination.Signup -> SignupScreen(modifier, appState)
            Destination.ResetPassword -> ResetPasswordScreen(modifier, appState)
        }
    }
}
