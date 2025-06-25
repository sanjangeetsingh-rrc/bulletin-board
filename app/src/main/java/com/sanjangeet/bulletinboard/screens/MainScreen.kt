package com.sanjangeet.bulletinboard.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.database.AppDatabase
import com.sanjangeet.bulletinboard.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.auth.LoginRenewRequest
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier, appState: AppState) {
    val context = LocalContext.current
    val appDatabase = remember { AppDatabase.getInstance(context) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val tokenEntity = withContext(Dispatchers.IO) {
            appDatabase.tokenDao().get()
        }
        val refreshToken = tokenEntity?.refresh

        if (refreshToken == null) {
            appState.currentDestination = Destination.Login
        } else {
            if (appState.token == "") {
                try {
                    var response = Client.authService.loginRenew(LoginRenewRequest(refreshToken))
                    appState.token = response.access
                } catch (e: Exception) {
                    appDatabase.tokenDao().delete()
                    appState.currentDestination = Destination.Login
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            coroutineScope.launch {
                appDatabase.tokenDao().delete()
                appState.token = ""
                appState.currentDestination = Destination.Login
            }
        }) {Text(text = "Logout")}
    }
}
