package com.sanjangeet.bulletinboard.screens.main

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.api.Client
import com.sanjangeet.bulletinboard.api.models.group.GroupResponse
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SearchScreen(modifier: Modifier = Modifier, appState: AppState) {
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var groups by remember { mutableStateOf<List<GroupResponse>>(emptyList()) }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { newValue ->
                query = newValue
                coroutineScope.launch {
                    try {
                        val response = Client.groupService.searchGroups(
                            query = newValue.text,
                            authHeader = "Bearer ${appState.token}"
                        )
                        groups = response
                    } catch (e: Exception) {
                            appState.snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            },
            label = { Text("Search Groups") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(groups) { group ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = group.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Admin: ${group.admin_name ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Description: ${group.description ?: "-"}", style = MaterialTheme.typography.bodyMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { /* TODO */ }) {
                            Text(text = "Join")
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }
}
