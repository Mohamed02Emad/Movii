package com.mo.movie.android.features.auth.presentation.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mo.movie.android.IS_DARK_MODE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.Height
import com.mo.movie.android.core.composables.buttons.ButtonWithIcon
import com.mo.movie.android.core.navigation.pushReplace
import com.mo.movie.android.features.setup.onBoarding.composables.LogoLayout
import com.mo.movie.core.navigation.Screen
import com.mo.movie.features.auth.presentation.AuthViewModel
import com.mo.movie.features.more.settings.presentation.SettingsViewModel

@Composable
fun AuthScreen(
    onSignInClick: () -> Unit,
    viewModel: AuthViewModel,
    settingsViewModel : SettingsViewModel,
    navController: NavHostController
) {
    viewModel.resetState()
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Height(height = 40.dp)
        Box(contentAlignment = Alignment.Center) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                LogoLayout()
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(35.dp)
                        .clip(RoundedCornerShape(35.dp))
                        .clickable {
                            settingsViewModel.setDarkMode(IS_DARK_MODE.not())
                        }
                        .padding(2.dp),
                    imageVector = if (IS_DARK_MODE) Icons.Filled.DarkMode else Icons.Outlined.DarkMode,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Height(height = 8.dp)
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "google",
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(35.dp)
                ),
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonWithIcon(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            title = context.getString(R.string.login),
            onClick = onSignInClick,
            painter = painterResource(R.drawable.google),
        )
        Height(height = 20.dp)
        ButtonWithIcon(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            title = context.getString(R.string.guest_login),
            onClick = {
                      navController.pushReplace(Screen.Home)
            },
            painter = painterResource(R.drawable.guest),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            isBordered = true,
            textColor = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.weight(4f))
    }
}