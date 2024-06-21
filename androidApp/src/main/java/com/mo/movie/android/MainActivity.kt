package com.mo.movie.android

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.mo.movie.SharedViewModel
import com.mo.movie.android.core.navigation.clearBackStack
import com.mo.movie.android.core.navigation.navhosts.NavHost
import com.mo.movie.android.features.auth.utils.GoogleAuthUiClient
import com.mo.movie.android.theme.MyApplicationTheme
import com.mo.movie.core.SharedStates
import com.mo.movie.features.auth.presentation.AuthViewModel
import com.mo.movie.features.more.settings.domain.models.Languages
import com.mo.movie.features.more.settings.presentation.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel
import java.util.Locale

var IS_RTL: Boolean = false
var IS_DARK_MODE: Boolean = false
var CURRENT_LANGUAGE: Languages = Languages.system
class MainActivity : ComponentActivity() {

    // notifications permission
//    private val pushPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) {
//    }

    private val globalStates: SharedStates by inject()
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        showSplash()
        super.onCreate(savedInstanceState)
        setContent {
            val sharedViewModel: SharedViewModel = globalStates.getSharedViewModel()
            val settingsViewModel: SettingsViewModel = getViewModel()
            val authViewModel: AuthViewModel = getViewModel()
            val navController = rememberNavController()
            runBlocking {
                val isLoggedIn = googleAuthUiClient.getSignedInUser() != null
                sharedViewModel.getStartDestination(isLoggedIn)
                settingsViewModel.initData()
            }
            val startDestination = sharedViewModel.startDestination.collectAsState()
            val darkMode = settingsViewModel.darkMode.collectAsState()
            IS_DARK_MODE = darkMode.value
            val language = settingsViewModel.language.collectAsState()
            CURRENT_LANGUAGE = language.value
            val isRtl = getRtlMode(language.value)
            val direction = if (isRtl) LocalLayoutDirection provides LayoutDirection.Rtl else LocalLayoutDirection provides LayoutDirection.Ltr
            setAppLanguage(language.value)
            CompositionLocalProvider(direction) {
                MyApplicationTheme(darkTheme = darkMode.value) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        authViewModel.onSignInResult(signInResult)
                                        if (signInResult.data != null){
                                            val isLoggedIn = googleAuthUiClient.getSignedInUser() != null
                                            sharedViewModel.getStartDestination(isLoggedIn)
                                        }
                                    }
                                }
                            }
                        )
                        NavHost(
                            navController = navController,
                            startDestination = startDestination.value,
                            settingsViewModel = settingsViewModel,
                            sharedViewModel = sharedViewModel,
                            authViewModel = authViewModel,
                            onSignInClicked = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )

                                }
                            },
                            onLogoutClicked = {
                                    try {
                                        googleAuthUiClient.signOut()
                                    }catch (_:Exception){
                                    }
                            },
                        )

                    }
                }
            }
        }
    }

    private fun showSplash() {
        setTheme(R.style.Theme_My_Base_compose_code)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        } else {
            runBlocking {
                delay(400)
            }
        }
    }

    private fun getRtlMode(lang : Languages): Boolean {
        val lngString = when (lang) {
            Languages.system -> {
                val locale: Locale =
                    Resources.getSystem().configuration.locales[0]
                locale.language ?: "en"
            }

            Languages.ar -> {
                "ar"
            }

            Languages.en -> {
                "en"
            }
        }
        IS_RTL = lngString == "ar"
        return  lngString == "ar"
    }

    private fun setAppLanguage(language: Languages) {
        val lngString = when (language) {
            Languages.system -> {
                val locale: Locale =
                    Resources.getSystem().configuration.locales[0]
                locale.language ?: "en"
            }
            Languages.ar -> {
                "ar"
            }

            Languages.en -> {
                "en"
            }
        }
        val config = resources.configuration
        val locale = Locale(lngString)
        Locale.setDefault(locale)
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

//    private fun requestForPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            pushPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//        }
//    }
}

