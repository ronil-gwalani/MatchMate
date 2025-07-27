package org.ronil.matchmate.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.compositionLocalOf


import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


val json = Json {
    ignoreUnknownKeys = true
}





val LocalPreferenceManager =
    compositionLocalOf<PreferenceManager> { error("Preference Manager Not Provided") }

val LocalSnackBarProvider =
    compositionLocalOf<ShackBarState> { error("ShackBarState  Not Provided") }

fun Context.showToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}