package com.sanjangeet.bulletinboard.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.group.GroupResponse

@Composable
fun MyGroupsScreen(modifier: Modifier = Modifier, appState: AppState) {
    var groups by remember { mutableStateOf<List<GroupResponse>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val response = Client.groupService.getMyGroups(authHeader = "Bearer ${appState.token}")
            groups = response
        } catch (e: Exception) {
            appState.snackbarHostState.showSnackbar("Something went wrong")
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(groups) { group ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = group.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Admin: ${group.admin_name ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Description: ${group.description ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            HorizontalDivider()
        }
    }
}
