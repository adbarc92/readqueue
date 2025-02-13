package com.openbarclay.readqueue.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
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
//    networkMonitor: NetworkMonitor,
//    timeZoneMonitor: TimeZoneMonitor,
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
//    timeZoneMonitor: TimeZoneMonitor,
    // FIXME: Add relevant data repositories here
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry =
                navController.currentBackStackEntryFlow.collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    // FIXME: currentTopLevelDestination

    // FIXME: Offline
}

@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    // FIXME (Add Jank Stats)
}