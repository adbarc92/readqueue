package com.openbarclay.readqueue.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.openbarclay.readqueue.data.db.entity.Book
import com.openbarclay.readqueue.data.db.entity.BookStatus

@Composable
fun HomeScreen(viewModel: BookListViewModel = hiltViewModel()) {
    val books by viewModel.books.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var statusDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add Book")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(books) { book ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { statusDropdownExpanded = true }) {
                    Column {
                        Text(book.title)
                        Text(book.author)
                        BookStatusDropdown(
                            expanded = statusDropdownExpanded,
                            book = book,
                            onExpandedChange = { newExpanded ->
                                statusDropdownExpanded = newExpanded
                            }
                        ) { newStatus ->
                            viewModel.updateStatus(
                                book.id,
                                newStatus,
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddBookDialog(onDismiss = { showAddDialog = false }, onSave = { title, author ->
            viewModel.addBook(title, author)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStatusDropdown(
    expanded: Boolean,
    book: Book,
    onExpandedChange: (Boolean) -> Unit,
    onStatusChange: (BookStatus) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        TextField(
            value = book.status.name,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(onDismissRequest = { onExpandedChange(false) }, expanded = expanded) {
            BookStatus.entries.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name) },
                    onClick = {
                        onStatusChange(status)
                        onExpandedChange(false)
                    },
                    trailingIcon = {
                        if (book.status == status) {
                            Icon(Icons.Default.Check, "Current book status")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AddBookDialog(onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Book") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(title, author)
                    onDismiss()
                }
            ) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}