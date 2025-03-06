package com.openbarclay.readqueue.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//    Scaffold(
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentRoute = navBackStackEntry?.destination?.route
//
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                    label = { Text("Home") },
//                    selected = currentRoute == "home",
//                    onClick = { navController.navigate("home") { popUpTo(navController.graph.startDestinationId) } }
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Schedule") },
//                    label = { Text("Schedule") },
//                    selected = currentRoute == "schedule",
//                    onClick = { navController.navigate("schedule") }
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Stats") },
//                    label = { Text("Stats") },
//                    selected = currentRoute == "stats",
//                    onClick = { navController.navigate("stats") }
//                )
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
//                    label = { Text("Settings") },
//                    selected = currentRoute == "settings",
//                    onClick = { navController.navigate("settings") }
//                )
//            }
//        }
//    ) { padding ->
//        NavHost(navController, startDestination = "home", modifier = Modifier.padding(padding)) {
//            composable("home") { HomeScreen() }
//            composable("schedule") { ScheduleScreen() }
//            composable("stats") { StatsScreen() }
//            composable("settings") { SettingsScreen() }
//            composable("bookDetails/{bookId}") { backStackEntry ->
//                BookDetailsScreen(bookId = backStackEntry.arguments?.getString("bookId"))
//            }
//        }
//    }
//}