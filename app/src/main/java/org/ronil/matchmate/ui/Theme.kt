package org.ronil.matchmate.ui


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import org.ronil.matchmate.utils.AppColors


private val LightColorScheme = lightColorScheme(
    primary = AppColors.accentColor,
    background = AppColors.whiteColor,
    secondary = AppColors.accentColor,
    tertiary = AppColors.textColor,
    onSecondary = AppColors.textColor,
    onPrimary = AppColors.whiteColor,
    onSurface = AppColors.accentColor,
    onPrimaryContainer = AppColors.accentColor,
    onSecondaryContainer = AppColors.accentColor,
    onTertiaryContainer = AppColors.accentColor,

    onTertiary = AppColors.accentColor,
    /* Other default colors to override
    background = RonColor(0xFFFFFBFE),
    surface = RonColor(0xFFFFFBFE),
    onPrimary = RonColor.White,
    onSecondary = RonColor.White,
    onTertiary = RonColor.White,
    onBackground = RonColor(0xFF1C1B1F),
    onSurface = RonColor(0xFF1C1B1F),
    */
)

@Composable
fun MatchMateTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(

        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}