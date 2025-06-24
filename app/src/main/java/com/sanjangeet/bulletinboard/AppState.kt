package com.sanjangeet.bulletinboard

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sanjangeet.bulletinboard.navigation.Destination

class AppState {
    var token by mutableStateOf<String>("")
    var currentDestination by mutableStateOf<Destination>(Destination.Login)
    var snackbarHostState by mutableStateOf<SnackbarHostState>(SnackbarHostState())
}
