package org.ronil.matchmate.screens

// Required additional imports:
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.theapache64.twyper.CardController
import com.github.theapache64.twyper.SwipedOutDirection
import com.github.theapache64.twyper.Twyper
import com.github.theapache64.twyper.rememberTwyperController
import org.koin.androidx.compose.koinViewModel
import org.ronil.matchmate.R
import org.ronil.matchmate.models.Location
import org.ronil.matchmate.models.Name
import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.utils.AppColors
import org.ronil.matchmate.viewmodels.LikesVM


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikesScreen(
    viewModel: LikesVM = koinViewModel(),
    fetchDataFromServer: () -> Unit,
    openMatches: () -> Unit
) {
    val cards by viewModel.allUsers.collectAsState(emptyList())
    val bottomSheetState = rememberModalBottomSheetState()
    val swipeController = rememberTwyperController()
    val states = cards.filter { it.status == Status.NoAction }


    Column(
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
            .padding(vertical = 10.dp)
    ) {


        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        {
            LaunchedEffect(states) {
                if (states.size == 3) {
                    fetchDataFromServer()
                }
            }

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
                        text = "No more profiles to show",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Button(onClick = openMatches) {
                        Text(
                            text = "Open Matches",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    }

                }
            } else {
                // Render cards
                Twyper(
                    items = states,
                    twyperController = swipeController, // optional
                    onItemRemoved = { item, direction ->
                        println("Item removed: $item -> $direction")
                        if (direction === SwipedOutDirection.RIGHT) {
                            viewModel.onRightSwipe(item)
                        } else if (direction === SwipedOutDirection.LEFT) {
                            viewModel.onLeftSwipe(item)
                        }
//                        states2.remove(item)
                    },
                    onEmpty = { // invoked when the stack is empty
                        println("End reached")
                    }
                ) { item ->
                    ProfileCard(
                        modifier = Modifier
                            .fillMaxSize(),
                        matchProfile = item,
                        item==states.first(),
                        state = swipeController.currentCardController
                    )
                }


            }
        }

        // Bottom Action Buttons
        if (states.isNotEmpty()) {
            BottomActionButtons(
                onDislike = {
                    swipeController.swipeLeft()
                },
                onLike = {
                    swipeController.swipeRight()
                }, onInfo = {
                    states.firstOrNull()?.let { card ->
                        viewModel.selectedProfile = card
                        viewModel.showBottomSheet = true
                    }
                }
            )
        }

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
                        viewModel.onRightSwipe(profile)
                        viewModel.showBottomSheet = false
                        viewModel.selectedProfile = null
                    },
                    onReject = { profile ->
                        // Handle reject action
                        viewModel.onLeftSwipe(profile)
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
}

@Composable
private fun ProfileCard(
    modifier: Modifier,
    matchProfile: UserEntity,
    showOverLays:Boolean,
    state: CardController?
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Profile Image - Full card background
            AsyncImage(
                model = matchProfile.picture?.large?.ifEmpty { R.drawable.logo },
                contentDescription = "Profile picture of ${matchProfile.name?.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.logo),
                error = painterResource(R.drawable.logo)
            )
            if (showOverLays) {
                SwipeOverlays(state)

            }
            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )


            // Profile Information - Bottom section
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // Name and Age Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = buildFullName(matchProfile.name),
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.9f)
                        ),
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(
                            text = "${matchProfile.dob?.age}",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location with Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = buildLocation(matchProfile.location),
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomActionButtons(
    onDislike: () -> Unit,
    onLike: () -> Unit,
    onInfo: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 24.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dislike Button
        Card(
            onClick = onDislike,
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.size(64.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dislike",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Info Button (Optional)
        Card(
            onClick = onInfo,
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = Color.Blue,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Like Button
        Card(
            onClick = onLike,
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.size(64.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = Color(0xFFE91E63), // Pink color
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun SwipeOverlays(swipeState: CardController?) {
    val screenWidth= LocalView.current.width
    val offsetX = swipeState?.cardX?:0f
//    val screenWidth = swipeState

    // Calculate opacity based on swipe distance
    val rightSwipeProgress = (offsetX / (screenWidth * 0.3f)).coerceIn(0f, 1f)
    val leftSwipeProgress = ((-offsetX) / (screenWidth * 0.3f)).coerceIn(0f, 1f)

    // LIKE overlay (right swipe)
    if (rightSwipeProgress > 0) {
        LikeOverlay(
            modifier = Modifier.fillMaxSize(),
            alpha = rightSwipeProgress
        )
    }

    // DISLIKE overlay (left swipe)
    if (leftSwipeProgress > 0) {
        DislikeOverlay(
            modifier = Modifier.fillMaxSize(),
            alpha = leftSwipeProgress
        )
    }
}

@Composable
private fun LikeOverlay(
    modifier: Modifier = Modifier,
    alpha: Float
) {
    Box(
        modifier = modifier
            .background(Color(0xFFE91E63).copy(alpha = alpha * 0.4f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(-25f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like",
                tint = Color(0xFFE91E63).copy(alpha = alpha),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "LIKE",
                color = Color(0xFFE91E63).copy(alpha = alpha),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White.copy(alpha = alpha * 0.7f),
                        offset = Offset(3f, 3f),
                        blurRadius = 6f
                    )
                )
            )
        }

        // Border effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .border(
                    width = 4.dp,
                    color = Color(0xFFE91E63).copy(alpha = alpha * 0.8f),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
private fun DislikeOverlay(
    modifier: Modifier = Modifier,
    alpha: Float
) {
    Box(
        modifier = modifier
            .background(Color.Red.copy(alpha = alpha * 0.4f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(25f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Dislike",
                tint = Color.Red.copy(alpha = alpha),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "NOPE",
                color = Color.Red.copy(alpha = alpha),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White.copy(alpha = alpha * 0.7f),
                        offset = Offset(3f, 3f),
                        blurRadius = 6f
                    )
                )
            )
        }

        // Border effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .border(
                    width = 4.dp,
                    color = Color.Red.copy(alpha = alpha * 0.8f),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

// Helper functions
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



        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons based on status
        ActionButtons(
            status = profile.status ?: Status.Rejected,
            onAccept = { onAccept(profile) },
            onReject = { onReject(profile) },
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
) {
    when (status) {

        Status.NoAction -> {
            // Show Accept and Remove buttons
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
                        contentDescription = "Reject",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reject")
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
                        contentDescription = "Accept",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Accept")
                }
            }
        }

        else -> {}
    }
}

