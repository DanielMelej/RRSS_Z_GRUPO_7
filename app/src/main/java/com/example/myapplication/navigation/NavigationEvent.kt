package com.example.myapplication.navigation

sealed class NavigationEvent {
    data class NavigateTo(
        val screen: Screen,
        val popUpTo: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    object PopBackStack : NavigationEvent()
    object NavigateUp : NavigationEvent()
}