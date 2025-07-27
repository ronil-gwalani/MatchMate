package org.ronil.matchmate.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.getKoin
import org.ronil.matchmate.screens.HomeScreen
import org.ronil.matchmate.screens.ProfileScreen
import org.ronil.matchmate.screens.SplashScreen

import org.ronil.matchmate.utils.AppConstants
import org.ronil.matchmate.utils.LocalPreferenceManager
import org.ronil.matchmate.utils.LocalSnackBarProvider
import org.ronil.matchmate.utils.MyAnimation
import org.ronil.matchmate.utils.PreferenceManager
import org.ronil.matchmate.utils.ShackBarState


@Composable
fun SetUpNavGraph() {

    val navController = rememberNavController()
    val preferenceManager = getKoin().get<PreferenceManager>()
    val snackBar by remember { mutableStateOf(ShackBarState()) }

    CompositionLocalProvider(LocalSnackBarProvider provides snackBar) {
        CompositionLocalProvider(LocalPreferenceManager provides preferenceManager) {
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
//                    .imePadding() // This ensures proper padding when the keyboard is visible
//                    .navigationBarsPadding() // Respect bottom nav bar
                    .statusBarsPadding(),
                navController = navController,
                startDestination = NavRouts.Splash,
                enterTransition = { MyAnimation.myEnterAnimation() },
                exitTransition = { MyAnimation.myExitAnimation() },
                popEnterTransition = { MyAnimation.myEnterAnimation() },
                popExitTransition = { MyAnimation.myExitAnimation() },
            ) {

                composable<NavRouts.Splash> {
                    SplashScreen {
                        val screen =
                            if (!preferenceManager.getString(AppConstants.Preferences.NAME)
                                    .isNullOrEmpty() || preferenceManager.getBoolean(AppConstants.Preferences.LOGIN_SKIPPED)
                            ) NavRouts.HomeScreen else NavRouts.ProfileScreen
                        navController.navigateTo(screen, finish = true)
                    }

                }
                composable<NavRouts.ProfileScreen> {
                    ProfileScreen(backPress = {
                        navController.finish()
                    }) {
                        navController.navigateTo(NavRouts.HomeScreen, finish = true)
                    }
                }
                composable<NavRouts.HomeScreen> {
                    HomeScreen()
                }


            }
        }
    }

}