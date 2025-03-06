package com.openbarclay.readqueue.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.openbarclay.readqueue.data.db.AppDatabase

@Composable
fun HomeScreen(viewModel: BookListViewModel = viewModel(
    factory = BookListViewModelFactory(AppDatabase.getDatabase(LocalContext.current).bookDao())
)) {
    val books by viewModel.books.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add Book")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(books) { book ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(book.title)
                        Text(book.author)
                        Text(book.status.name)
                    }
                }
            }
        }
    }

//    if (showAddDialog) {
//        AddBookDialog(onDismiss = { showAddDialog = false }, onSave = { title, author ->
//            viewModel.addBook(title, author)
//        })
//    }
}