
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mo.movie.android.core.composables.IfOrNope
import com.mo.movie.android.core.utils.keyboardAsState
import com.mo.movie.core.navigation.Screen

sealed class BottomNavItem(val screen: Screen, val selectedIcon: ImageVector, val notSelectedIcon : ImageVector, val label: String) {
    data object Home : BottomNavItem(Screen.Home, Icons.Filled.Home, Icons.Outlined.Home, "Home")
    data object Search : BottomNavItem(Screen.Search, Icons.Filled.Search, Icons.Outlined.Search, "Search")
    data object Movies : BottomNavItem(Screen.Movies, Icons.Filled.Movie, Icons.Outlined.Movie, "Movies")
    data object TvShows : BottomNavItem(Screen.TvShows, Icons.Filled.LiveTv, Icons.Outlined.LiveTv, "TvShows")
    data object More : BottomNavItem(Screen.More, Icons.Filled.Menu, Icons.Outlined.Menu, "More")

}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navigationItems = listOf(
        BottomNavItem.More,
        BottomNavItem.TvShows,
        BottomNavItem.Movies,
        BottomNavItem.Search,
        BottomNavItem.Home,
    )
    val navigationScreens = navigationItems.map { it.screen.route }
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val notSelectedColor = Color(61, 61, 61, 255)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val clipShape = RoundedCornerShape(topStart = 12.dp , topEnd = 12.dp)
    val isKeyboardVisible by keyboardAsState()
    IfOrNope(condition = navigationScreens.contains(currentRoute) && isKeyboardVisible.not()) {
       BottomAppBar(
            modifier = Modifier
                .height(60.dp)
                .clip(clipShape)
                .shadow(elevation = 16.dp)
                .clip(clipShape)
                .background(backgroundColor)
           , containerColor = backgroundColor
        ) {
            navigationItems.forEach { item ->
                val isSelected = currentRoute == item.screen.route
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.onBackground,
                        selectedIconColor = primaryColor,
                        unselectedIconColor = notSelectedColor,
                    ),
                    alwaysShowLabel = false,
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            if (isSelected) item.selectedIcon else item.notSelectedIcon,
                            contentDescription = item.label
                        )
                    },
                )
            }
        }
    }
}