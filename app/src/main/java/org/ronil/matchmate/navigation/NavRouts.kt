package org.ronil.matchmate.navigation

import kotlinx.serialization.Serializable

sealed class NavRouts {


    @Serializable
    data object Splash : NavRouts()

    @Serializable
    data object ProfileScreen : NavRouts()

    @Serializable
    data object HomeScreen : NavRouts()


}