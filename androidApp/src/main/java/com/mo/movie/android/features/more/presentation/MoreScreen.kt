package com.mo.movie.android.features.more.presentation

import AppDropdown
import DDLItem
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mo.movie.android.IS_DARK_MODE
import com.mo.movie.android.R
import com.mo.movie.android.core.composables.Width
import com.mo.movie.android.core.composables.buttons.ButtonWithIcon
import com.mo.movie.android.core.composables.text.AppText
import com.mo.movie.android.theme.backgroundLight
import com.mo.movie.features.more.settings.domain.models.Languages
import com.mo.movie.features.more.settings.presentation.SettingsViewModel


@Composable
fun MoreScreen(
    settingsViewModel: SettingsViewModel,
    logoutClicked: () -> Unit,
    languageChanged : () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    val selectedLanguage by settingsViewModel.language.collectAsState()
    val languages = Languages.entries.toTypedArray()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {

        AppText(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            text = context.getString(R.string.more),
            fontSize = 20.sp
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .clickable {
                    settingsViewModel.setDarkMode(IS_DARK_MODE.not())
                }
        ) {
            AppText(text = context.getString(R.string.dark_mode), fontSize = 20.sp)
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(35.dp))
                    .padding(2.dp),
                imageVector = if (IS_DARK_MODE) Icons.Filled.DarkMode else Icons.Outlined.DarkMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Row {
            Width(width = 16.dp)
            AppText(text = context.getString(R.string.language), fontSize = 20.sp)
            AppDropdown(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .fillMaxWidth()
                    .animateContentSize(spring())
                    .border(
                        width = 0.15.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(4.dp)
                    ),
                items = languages.map {
                    DDLItem(
                        value = it,
                        title = it.displayName,
                    )
                },
                selectedItem = selectedLanguage,
                itemTextColor = backgroundLight,
                hint = "pick one"
            ) { language ->
                settingsViewModel.setLanguage(language)
                languageChanged()
            }
        }
        ButtonWithIcon(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 55.dp)
                .fillMaxWidth(),
            title = context.getString(R.string.logout),
        ) {
            logoutClicked()
           }
   }
}