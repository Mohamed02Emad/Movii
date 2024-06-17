package com.mo.movie.android.features.more.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mo.movie.features.auth.presentation.AuthViewModel
import com.mo.movie.features.more.settings.presentation.SettingsViewModel


@Composable
fun MoreScreen(authViewModel: AuthViewModel, settingsViewModel: SettingsViewModel , logoutClicked : ()->Unit) {
   LazyColumn {
       item {
           Button(onClick = logoutClicked) {
               Text(text = "Logout")
           }
       }
   }
}