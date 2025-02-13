package com.openbarclay.readqueue.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberRqAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): RqAppState {
    NavigationTrackingSideEffect(navController)
    return remember(navController) {
        RqAppState(navController = navController, coroutineScope = coroutineScope)
    }
}

@Stable
class RqAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
//    networkMonitor: NetworkMonitor,
//    userNewsResourceRepository: UserNewsResourceRepository,
//    timeZoneMonitor: TimeZoneMonitor,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)
}

@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    // FIXME (Add Jank Stats)
}