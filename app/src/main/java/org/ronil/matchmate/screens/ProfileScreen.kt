package org.ronil.matchmate.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.ronil.matchmate.R
import org.ronil.matchmate.utils.AppColors
import org.ronil.matchmate.utils.AppConstants
import org.ronil.matchmate.utils.GetOneTimeBlock
import org.ronil.matchmate.utils.LocalPreferenceManager
import org.ronil.matchmate.utils.LocalSnackBarProvider
import org.ronil.matchmate.viewmodels.RegistrationVM

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    showSkip: Boolean = true,
    backPress: () -> Unit,
    viewmodel: RegistrationVM = koinViewModel(),
    navigate: () -> Unit
) {
    val preferenceManager = LocalPreferenceManager.current
    val snackBar = LocalSnackBarProvider.current
    BackHandler {
        backPress()
    }
    GetOneTimeBlock {
        viewmodel.showError.collectLatest {
            snackBar.showSnackBar(it)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppColors.accentColor,
                        AppColors.secondaryColor,
                        AppColors.tertiaryColor
                    )
                )
            ),

        snackbarHost = {
            SnackbarHost(
                snackBar.hostState,
                snackbar = {
                    Snackbar(
                        it,
                        containerColor = Color.Red,
                        contentColor = AppColors.whiteColor
                    )
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppColors.accentColor,
                            AppColors.secondaryColor,
                            AppColors.tertiaryColor
                        )
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                top = 24.dp,
                bottom = 100.dp
            )
        ) {
            item {
                // Logo Section
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            Color.White.copy(alpha = 0.2f),
                            CircleShape
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.love_icon),
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Title
                Text(
                    text = stringResource(R.string.find_your_match),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 32.sp
                    )
                )
            }

            item {
                // Subtitle
                Text(
                    text = if (showSkip) stringResource(R.string.create_your_profile_to_get_started)
                    else stringResource(R.string.update_your_profile),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    ),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

            item {
                // Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        // KEY FIX 5: Remove animateContentSize to prevent layout issues
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Full Name Field
                        OutlinedTextField(
                            value = viewmodel.name,
                            onValueChange = { viewmodel.name = it },
                            label = { Text(stringResource(R.string.full_name)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = stringResource(R.string.name),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFFF6B9D),
                                focusedLabelColor = Color(0xFFFF6B9D)
                            ),
                            singleLine = true
                        )

                        // Email Field
                        OutlinedTextField(
                            value = viewmodel.email,
                            onValueChange = { viewmodel.email = it },
                            label = { Text(stringResource(R.string.email_address)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = stringResource(R.string.email),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFFF6B9D),
                                focusedLabelColor = Color(0xFFFF6B9D)
                            ),
                            singleLine = true
                        )

                        // Age and Gender Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Age Field
                            OutlinedTextField(
                                value = viewmodel.age,
                                onValueChange = { viewmodel.age = it.filter { it.isDigit() } },
                                label = { Text(stringResource(R.string.age)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = stringResource(R.string.age),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFFF6B9D),
                                    focusedLabelColor = Color(0xFFFF6B9D)
                                ),
                                singleLine = true
                            )

                            // Gender Dropdown
                            ExposedDropdownMenuBox(
                                expanded = viewmodel.genderExpanded,
                                onExpandedChange = {
                                    viewmodel.genderExpanded = !viewmodel.genderExpanded
                                },
                                modifier = Modifier.weight(1.5f)
                            ) {
                                OutlinedTextField(
                                    value = viewmodel.gender,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Gender") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Face,
                                            contentDescription = "Gender",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = viewmodel.genderExpanded
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFFF6B9D),
                                        focusedLabelColor = Color(0xFFFF6B9D)
                                    ),
                                    singleLine = true
                                )

                                ExposedDropdownMenu(
                                    expanded = viewmodel.genderExpanded,
                                    onDismissRequest = { viewmodel.genderExpanded = false }
                                ) {
                                    viewmodel.genders.forEach { selectionOption ->
                                        DropdownMenuItem(
                                            text = { Text(selectionOption) },
                                            onClick = {
                                                viewmodel.gender = selectionOption
                                                viewmodel.genderExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Continue Button
                        Button(
                            onClick = viewmodel::verifyInput,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF6B9D)
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = stringResource(R.string.submit),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.continue_),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Skip Button (only if showSkip is true)
            if (showSkip) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = {
                            viewmodel.viewModelScope.launch {
                                preferenceManager.setValue(AppConstants.Preferences.LOGIN_SKIPPED, true)
                            }
                            navigate() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.skip_for_now),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }

    // Dialog
    ShowDialogue(
        showDialog = viewmodel.showDialog,
        navigate = {
            viewmodel.showDialog = false
            viewmodel.saveDataIntoPref()
            navigate()
        },
        dismissDialogue = {

            viewmodel.showDialog = false
        }
    )
}

@Composable
private fun ShowDialogue(
    showDialog: Boolean,
    navigate: () -> Unit,
    dismissDialogue: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = dismissDialogue,
            title = { Text(stringResource(R.string.confirm_submission)) },
            text = {
                Text(stringResource(R.string.are_you_sure))
            },
            confirmButton = {
                TextButton(onClick = navigate) {
                    Text(stringResource(R.string.yes_i_m_sure))
                }
            },
            dismissButton = {
                TextButton(onClick = dismissDialogue) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
