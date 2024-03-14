package ua.edu.lntu.cw_4.model

sealed class Screen {
    object TaskDetails : Screen()
    object TaskList : Screen()
}