package com.example.myapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardToolbar
import com.example.myapp.presentation.profile.components.BannerSection
import com.example.myapp.presentation.profile.components.ProfileHeaderSection
import com.example.myapp.presentation.profile.components.User
import com.example.myapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    userId: String? = null,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {

    val state = viewModel.state
    val context = LocalContext.current
    
    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ProfileViewModel.UiEvent.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                BannerSection(
                    modifier = Modifier
                        .aspectRatio(2.15f)
                )
            }
            item {
                state.profile?.let { profile ->
                    ProfileHeaderSection(
                        user = User(
                        userId = profile.userId,
                        profilePictureUrl = profile.profilePictureUrl,
                        username = profile.username,
                        description = profile.bio
                    ),
                        onEditClick = {
                            navController.navigate(Screen.EditProfileScreen.route +"/${profile.userId}")
                        }
                    )
                }

            }
        }
    }

}