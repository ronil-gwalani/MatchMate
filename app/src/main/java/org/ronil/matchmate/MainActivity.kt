package org.ronil.matchmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ccom.alexstyl.swipeablecard.Direction
import ccom.alexstyl.swipeablecard.rememberSwipeableCardState
import ccom.alexstyl.swipeablecard.swipableCard
import org.koin.compose.getKoin
import org.ronil.matchmate.navigation.SetUpNavGraph
import org.ronil.matchmate.repository.NetworkRepository
import org.ronil.matchmate.repository.RoomRepository
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val repo= getKoin().get<NetworkRepository>()
    val repo2= getKoin().get<RoomRepository>()
    val list=(1..10).toList()

    LaunchedEffect(Unit) {
//        repo.getAllUsers(repo2)
    }

    val states = list
        .map { it to rememberSwipeableCardState() }

    val scope = rememberCoroutineScope()
    Box(Modifier
        .padding(24.dp)
        .fillMaxSize()
        .aspectRatio(1f)
//        .align(Alignment.Center)
    ) {
        states.forEach { (matchProfile, state) ->
            if (state.swipedDirection == null) {
            }
            LaunchedEffect(matchProfile, state.swipedDirection) {
                if (state.swipedDirection != null) {
//                    hint = "You swiped ${stringFrom(state.swipedDirection!!)}"
                }
            }
        }
    }
}

