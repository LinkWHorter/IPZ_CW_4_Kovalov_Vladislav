package ua.edu.lntu.cw_4.model

sealed class Screen {
    object TaskList : Screen()
    object TaskDetails : Screen()
}