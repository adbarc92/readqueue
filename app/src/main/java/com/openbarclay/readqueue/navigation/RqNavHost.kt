package com.openbarclay.readqueue.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.openbarclay.readqueue.ui.RqAppState

@Composable
fun RqNavHost(
    appState: RqAppState,
    // FIXME: snackbar
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        modifier = modifier,
//        startDestination =
    ) {

    }
}