package com.openbarclay.readqueue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.openbarclay.readqueue.navigation.AppNavigation
import com.openbarclay.readqueue.ui.theme.ReadQueueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadQueueTheme {
                AppNavigation()
            }
        }
    }
}