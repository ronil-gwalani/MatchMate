package org.ronil.matchmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.ronil.matchmate.navigation.SetUpNavGraph
import org.ronil.matchmate.ui.MatchMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchMateTheme {
                SetUpNavGraph()
            }
        }
    }
}


