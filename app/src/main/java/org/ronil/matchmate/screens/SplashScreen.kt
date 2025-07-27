package org.ronil.matchmate.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.theapache64.twyper.Twyper
import com.github.theapache64.twyper.rememberTwyperController


import kotlinx.coroutines.launch
import org.ronil.matchmate.R
import org.ronil.matchmate.utils.AppColors

@Composable
fun SplashScreen2(
    navigate: suspend () -> Unit,
) {
    val items = remember { mutableStateListOf(*('A'..'E').toList().toTypedArray()) }

//  twyperController to swipe cards programmatically
    val twyperController = rememberTwyperController()
    Twyper(
        items = items,
        twyperController = twyperController, // optional
        onItemRemoved = { item, direction ->
            println("Item removed: $item -> $direction")
            items.remove(item)
        },
        onEmpty = { // invoked when the stack is empty
            println("End reached")
        }
    ) { item ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.toString())
        }
    }

}

@Composable
fun SplashScreen(

    navigate: suspend () -> Unit,
) {

    val scale = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            scale.animateTo(
                targetValue = 1.5f,
                animationSpec = tween(
                    durationMillis = 2500,
                    easing = { t -> overshootInterpolation(t, 1.5f) }
                )
            )
            navigate()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .background(AppColors.accentColor),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.logo), // Multiplatform resource reference
            contentDescription = "Logo of the app",
            modifier = Modifier
                .scale(scale.value)
                .clip(RoundedCornerShape(500.dp)) .background(AppColors.accentColor),
            contentScale = ContentScale.Fit
        )
    }


}

fun overshootInterpolation(t: Float, tension: Float): Float {
    return (t - 1).let { it * it * ((tension + 1) * it + tension) + 1 }
}