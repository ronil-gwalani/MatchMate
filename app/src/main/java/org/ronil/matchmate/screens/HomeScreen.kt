package org.ronil.matchmate.screens

import android.os.Process
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.ronil.matchmate.R
import org.ronil.matchmate.utils.AppColors
import org.ronil.matchmate.utils.showToast
import org.ronil.matchmate.viewmodels.HomeVM

sealed class HomeRouts {
    @Serializable
    data object Matches : HomeRouts()

    @Serializable
    data object Likes : HomeRouts()

    @Serializable
    data object Profile : HomeRouts()

}

data class BottomItemModel(val title: String, @DrawableRes val icon: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeVM = koinViewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current

    BackHandler {

        if (viewModel.selectedItemIndex == 0) {
            if (viewModel.backPressedOnce) {
                Process.killProcess(Process.myPid())
            } else {
                viewModel.backPressedOnce = true
                context.showToast(context.getString(R.string.press_back_again_to_exit))
                viewModel.viewModelScope.launch {
                    delay(2000) // Reset after 2 seconds
                    viewModel.backPressedOnce = false
                }
            }

        }
    }
    Scaffold(
        topBar = {
            // Top Bar
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.accentColor),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(

                            painter = painterResource(R.drawable.logo_trans),
                            contentDescription = stringResource(R.string.logo),
                            modifier = Modifier.size(60.dp), contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.app_name),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            context.showToast(context.getString(R.string.notification_feature_coming_soon))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Filter",
                            tint = Color.White
                        )
                    }
                },

                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            NavigationBar(containerColor = AppColors.whiteColor, tonalElevation = 2.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    viewModel.items.forEachIndexed { index, bottomItemModel ->

                        Column(
                            modifier = Modifier
                                .background(
                                    shape = CircleShape,
                                    color = Color.Transparent,
                                )
//                            .fillMaxHeight()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    if (viewModel.selectedItemIndex == index) {
                                        return@clickable
                                    }
                                    viewModel.selectedItemIndex = index
                                    when (index) {
                                        0 -> {
                                            navController.navigateTo(HomeRouts.Likes)
                                        }

                                        1 -> {
                                            navController.navigateTo(HomeRouts.Matches)
                                        }

                                        2 -> {
                                            navController.navigateTo(HomeRouts.Profile)
                                        }

                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = bottomItemModel.icon),
                                contentDescription = "Icon",
                                tint = if (viewModel.selectedItemIndex == index) AppColors.accentColor else AppColors.black,
                                modifier = Modifier
                                    .background(
                                        shape = CircleShape,
                                        color = if (index == viewModel.selectedItemIndex) AppColors.accentColor.copy(
                                            alpha = 0.1f
                                        ) else Color.Transparent,
                                    )
                                    .padding(5.dp)
                                    .size(30.dp)
//                                    .padding(5.dp)

                            )
                            Text(
                                text = bottomItemModel.title, style = TextStyle(
                                    fontWeight = if (index == viewModel.selectedItemIndex) FontWeight.ExtraBold else FontWeight.Normal,
                                    color = if (index == viewModel.selectedItemIndex) AppColors.accentColor else AppColors.black,
                                )
                            )
                            if (index == viewModel.selectedItemIndex) {
                                Spacer(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(4.dp)
                                        .clip(
                                            RoundedCornerShape(10.dp)
                                        )
                                        .background(AppColors.accentColor)
                                )
                            }
                        }

                    }
                }
            }
        }
    ) {

        Box(modifier = Modifier.padding(it)) {
            NavHost(
                contentAlignment = Alignment.TopStart,
                navController = navController,
                startDestination = HomeRouts.Likes,
//                enterTransition = { MyAnimation.mySlideIn() },
//                exitTransition = { MyAnimation.mySlideOut() },
//                popEnterTransition = { MyAnimation.mySlideIn() },
//                popExitTransition = { MyAnimation.mySlideOut() },
                builder = {
                    composable<HomeRouts.Likes>(
                    ) {
                        LikesScreen(fetchDataFromServer = {
                            viewModel.reCallApi()
                        }) {
                            navController.navigateTo(HomeRouts.Matches)
                            viewModel.selectedItemIndex = 1
                        }
                    }
                    composable<HomeRouts.Matches>(
                    ) {
                        MatchesScreen(backPress = {
                            navController.navigateTo(HomeRouts.Likes)
                            viewModel.selectedItemIndex = 0
                        })
                    }
                    composable<HomeRouts.Profile>(
                    ) {
                        ProfileScreen(false, backPress = {
                            navController.navigateTo(HomeRouts.Likes)
                            viewModel.selectedItemIndex = 0
                        }) {
                            context.showToast(context.getString(R.string.information_updated_successfully))
                            viewModel.selectedItemIndex = 0
                            navController.navigateTo(HomeRouts.Matches)
                        }
                    }

                })


        }

    }


}

private fun NavHostController.navigateTo(route: HomeRouts) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}