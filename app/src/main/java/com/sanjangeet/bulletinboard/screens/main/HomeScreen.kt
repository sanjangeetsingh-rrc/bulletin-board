package com.sanjangeet.bulletinboard.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sanjangeet.bulletinboard.AppState
import com.sanjangeet.bulletinboard.api.models.post.PostResponse
import com.sanjangeet.bulletinboard.api.Client
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier, appState: AppState) {
    var posts by remember { mutableStateOf<List<PostResponse>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            val fetchedPosts = Client.postService.getAllPosts("Bearer ${appState.token}")
            posts = fetchedPosts
        } catch (e: Exception) {
            appState.snackbarHostState.showSnackbar("Something went wrong")
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(posts) { post ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Group: ${post.groupId ?: "Unknown"}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
