@file:OptIn(ExperimentalMaterial3Api::class)

package ua.edu.lntu.cw_4

import android.annotation.SuppressLint
import androidx.compose.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.ui.tooling.preview.Preview
import ua.edu.lntu.cw_4.ui.theme.IPZ_CW_4_Kovalov_VladislavTheme
import ua.edu.lntu.cw_4.model.Screen
import ua.edu.lntu.cw_4.model.Screen.*
import ua.edu.lntu.cw_4.model.Task

// Створення початкових даних
val tasks = listOf(
    Task(1, "Завдання 1", "Опис завдання 1", "14/03/2024", "Активне завдання"),
    Task(2, "Завдання 2", "Опис завдання 2", "15/03/2024", "Виконане завдання"),
    Task(3, "Завдання 3", "Опис завдання 3", "16/03/2024", "Активне завдання")
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskListScreen(onTaskSelected: (Int) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Список завдань") },
                modifier = Modifier.background(Color.Blue)
            )
        }
    ) {
        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task, onTaskSelected = onTaskSelected)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskSelected: (Int) -> Unit) {
    val backgroundColor = if (task.status == "Активне завдання") Color.Green else Color.Gray
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = backgroundColor)
            .clickable { onTaskSelected(task.id) }
    ) {
        Text(text = task.title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskDetailsScreen(taskId: Int, onBackClicked: () -> Unit, onTaskDone: (Int) -> Unit) {
    val task = tasks.find { it.id == taskId } ?: return
    var doneButtonVisible by remember { mutableStateOf(task.status == "Активне завдання") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = task.title) },
                modifier = Modifier.background(Color.Blue),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Опис: ${task.description}")
            Text(text = "Дата: ${task.date}")

            if (doneButtonVisible) {
                Button(onClick = {
                    onTaskDone(task.id)
                    doneButtonVisible = false
                }) {
                    Text(text = "Done")
                }
            }
        }
    }
}

@Composable
fun IPZ_CW_4_Kovalov_VladislavApp() {
    var currentScreen by remember { mutableStateOf(TaskList) }
    var selectedTaskId by remember { mutableStateOf(-1) }

    when (currentScreen) {
        is TaskList -> {
            TaskListScreen { taskId ->
                selectedTaskId = taskId
                currentScreen = TaskDetails
            }
        }
        is TaskDetails -> {
            TaskDetailsScreen(taskId = selectedTaskId,
                onBackClicked = { currentScreen = TaskList },
                onTaskDone = { taskId ->
                    tasks.find { it.id == taskId }?.status = "Виконане завдання"
                    currentScreen = TaskList
                })
        }
    }
}