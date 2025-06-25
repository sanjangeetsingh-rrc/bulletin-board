package com.sanjangeet.bulletinboard.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.database.AppDatabase
import com.sanjangeet.bulletinboard.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.auth.LoginRenewRequest
import com.sanjangeet.bulletinboard.navigation.MainTab
import com.sanjangeet.bulletinboard.screens.main.HomeScreen
import com.sanjangeet.bulletinboard.screens.main.JoinedGroupsScreen
import com.sanjangeet.bulletinboard.screens.main.MyGroupsScreen
import com.sanjangeet.bulletinboard.screens.main.SearchScreen
import com.sanjangeet.bulletinboard.screens.main.SettingsScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier, appState: AppState) {
    val context = LocalContext.current
    val appDatabase = remember { AppDatabase.getInstance(context) }

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

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination?.route

            NavigationBar {
                MainTab.all.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination == screen.route,
                        onClick = {
                            if (currentDestination != screen.route) {
                                navController.navigate(screen.route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainTab.Home.route) {
                HomeScreen(appState = appState)
            }
            composable(MainTab.MyGroups.route) {
                MyGroupsScreen(appState = appState)
            }
            composable(MainTab.JoinedGroups.route) {
                JoinedGroupsScreen(appState = appState)
            }
            composable(MainTab.Search.route) {
                SearchScreen(appState = appState)
            }
            composable(MainTab.Settings.route) {
                SettingsScreen(appState = appState)
            }
        }
    }
}
