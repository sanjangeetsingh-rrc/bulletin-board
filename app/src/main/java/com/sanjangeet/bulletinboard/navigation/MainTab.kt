package com.sanjangeet.bulletinboard.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainTab(val route: String, val label: String, val icon: ImageVector) {
    object Home : MainTab("home", "Home", Icons.Default.Home)
    object MyGroups : MainTab("my_groups", "My Groups", Icons.Default.GroupWork)
    object JoinedGroups : MainTab("joined_groups", "Joined\nGroups", Icons.Default.Groups)
    object Search : MainTab("search", "Search", Icons.Default.Search)
    object Settings : MainTab("settings", "Settings", Icons.Default.Settings)

    companion object {
        val all = listOf(Home, MyGroups, JoinedGroups, Search, Settings)
    }
}
