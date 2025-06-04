package com.sanjangeet.bulletinboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sanjangeet.bulletinboard.screens.EditGroupScreen
import com.sanjangeet.bulletinboard.screens.GroupPostsScreen
import com.sanjangeet.bulletinboard.screens.GroupsScreen
import com.sanjangeet.bulletinboard.screens.LoginScreen
import com.sanjangeet.bulletinboard.screens.MainScreen
import com.sanjangeet.bulletinboard.screens.SearchScreen
import com.sanjangeet.bulletinboard.screens.SettingsScreen
import com.sanjangeet.bulletinboard.screens.SignupScreen
import com.sanjangeet.bulletinboard.screens.SignupVerifyOTPScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf<Destination>(Destination.Login) }

    when (currentScreen) {
        Destination.Login -> LoginScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.Signup -> SignupScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.SignupVerifyOTP -> SignupVerifyOTPScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.Main -> MainScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.Search -> SearchScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.Settings -> SettingsScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.Groups -> GroupsScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.GroupPosts -> GroupPostsScreen(modifier = modifier, onNavigate = { currentScreen = it })
        Destination.EditGroup -> EditGroupScreen(modifier = modifier, onNavigate = { currentScreen = it })
    }
}
