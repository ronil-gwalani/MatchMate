package org.ronil.matchmate.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.ronil.matchmate.R
import org.ronil.matchmate.models.Location
import org.ronil.matchmate.models.Name
import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.utils.AppColors
import org.ronil.matchmate.viewmodels.MatchesVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(viewModel: MatchesVM = koinViewModel(), backPress: () -> Unit) {
    val cards by viewModel.allUsers.collectAsState(emptyList())

    val states = cards.filter { it.status != Status.NoAction }

    // Bottom Sheet State
    val bottomSheetState = rememberModalBottomSheetState()
    BackHandler {
        backPress()
    }
    Box(Modifier.fillMaxSize()) {

        if (states.isEmpty()) {
            // No more cards message
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "No cards",
                    tint = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No profiles to show",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Button(onClick = backPress) {
                    Text(
                        text = "Explore More",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }

            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    )
            ) {
                items(states) { user ->
                    GridProfileCard(
                        matchProfile = user,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.selectedProfile = user
                            viewModel.showBottomSheet = true
                        }
                    )
                }
            }
        }
    }

    // Bottom Sheet
    if (viewModel.showBottomSheet && viewModel.selectedProfile != null) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.showBottomSheet = false
                viewModel.selectedProfile = null
            },
            sheetState = bottomSheetState,
            modifier = Modifier
        ) {
            ProfileBottomSheetContent(
                profile = viewModel.selectedProfile!!,
                onAccept = { profile ->
                    // Handle accept action
                    viewModel.acceptUser(profile)
                    viewModel.showBottomSheet = false
                    viewModel.selectedProfile = null
                },
                onReject = { profile ->
                    // Handle reject action
                    viewModel.declineUser(profile)
                    viewModel.showBottomSheet = false
                    viewModel.selectedProfile = null
                },
                onRemove = { profile ->
                    // Handle remove action
                    viewModel.removeFromHere(profile)
                    viewModel.showBottomSheet = false
                    viewModel.selectedProfile = null
                },
                onDismiss = {
                    viewModel.showBottomSheet = false
                    viewModel.selectedProfile = null
                }
            )
        }
    }
}


@Composable
private fun GridProfileCard(
    modifier: Modifier = Modifier,
    matchProfile: UserEntity,
    onClick: () -> Unit

) {
    Card(
        modifier = modifier
            .height(300.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Profile Image
            AsyncImage(
                model = matchProfile.picture?.large?.ifEmpty { R.drawable.logo },
                contentDescription = "Profile picture of ${matchProfile.name?.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.logo),
                error = painterResource(id = R.drawable.logo)
            )

            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Status Badge (Top Right)
            StatusBadge(
                status = matchProfile.status ?: Status.Accepted,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            )

            // Profile Information (Bottom)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                // Name and Age
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = buildFullName(matchProfile.name),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Age Badge
                    if (matchProfile.dob?.age != null) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.9f)
                            ),
                            modifier = Modifier.padding(start = 6.dp)
                        ) {
                            Text(
                                text = "${matchProfile.dob.age}",
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Location
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = buildLocation(matchProfile.location),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // No overlay - status shown only in badge
        }
    }
}

@Composable
private fun StatusBadge(
    status: Status,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, contentColor, text) = when (status) {
        Status.Accepted -> Triple(
            Color(0xFFE91E63), // Pink
            Color.White,
            "MATCHED"
        )

        Status.Rejected -> Triple(
            Color.Red,
            Color.White,
            "REJECTED"
        )

        Status.NoAction -> Triple(
            Color.Red,
            Color.White,
            "NO ACTIOn"
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.copy(alpha = 0.9f)
        )
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

// StatusOverlay removed - using only badge indicator

// Helper functions to build name and location strings
private fun buildFullName(name: Name?): String {
    return when {
        name == null -> "Unknown"
        name.first != null && name.last != null -> "${name.first} ${name.last}"
        name.first != null -> name.first
        name.title != null -> name.title
        else -> "Unknown"
    }
}

private fun buildLocation(location: Location?): String {
    return when {
        location == null -> "Unknown location"
        location.city != null && location.state != null -> "${location.city}, ${location.state}"
        location.city != null -> location.city
        location.state != null -> location.state
        else -> "Unknown location"
    }
}


// Bottom Sheet Content
@Composable
private fun ProfileBottomSheetContent(
    profile: UserEntity,
    onAccept: (UserEntity) -> Unit,
    onReject: (UserEntity) -> Unit,
    onRemove: (UserEntity) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {


        Spacer(modifier = Modifier.height(20.dp))

        // Profile Image and Basic Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = profile.picture?.large?.ifEmpty { R.drawable.logo },
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.logo),
                error = painterResource(R.drawable.logo)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = buildFullName(profile.name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Age ${profile.dob?.age ?: "Unknown"}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                // Current Status
                StatusBadge(
                    status = profile.status ?: Status.Rejected,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Detailed Information
        ProfileDetailSection(
            title = "Location",
            content = buildLocation(profile.location),
            icon = Icons.Default.LocationOn
        )

        Spacer(modifier = Modifier.height(16.dp))

//        ProfileDetailSection(
//            title = "Email",
//            content = profile.email ?: "Not available",
//            icon = Icons.Default.Email
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ProfileDetailSection(
//            title = "Phone",
//            content = profile.phone ?: "Not available",
//            icon = Icons.Default.Phone
//        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons based on status
        ActionButtons(
            status = profile.status ?: Status.Rejected,
            onAccept = { onAccept(profile) },
            onReject = { onReject(profile) },
            onRemove = { onRemove(profile) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Close Button
        OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Close")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileDetailSection(
    title: String,
    content: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = content,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ActionButtons(
    status: Status,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onRemove: () -> Unit
) {
    when (status) {

        Status.Rejected -> {
            // Show Accept and Remove buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onRemove,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    ),
                    border = BorderStroke(1.dp, Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.remove),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.remove))
                }

                Button(
                    onClick = onAccept,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63) // Pink
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.accept),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.accept))
                }
            }
        }

        Status.Accepted -> {
            // Show Reject and Remove buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onReject,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.reject),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.reject))
                }

                OutlinedButton(
                    onClick = onRemove,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    ),
                    border = BorderStroke(1.dp, Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.remove),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.remove))
                }
            }
        }

        Status.NoAction -> {

        }
    }
}
