package ignat.weatherappcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ignat.weatherappcompose.presentation.screen.EditWeather
import ignat.weatherappcompose.presentation.screen.Weathers


@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherRoutes.Weathers.route
    ) {
        composable(WeatherRoutes.Weathers.route) { Weathers(navController) }
        composable(WeatherRoutes.EditWeather.route, arguments = listOf(navArgument("weatherId") { type = NavType.LongType})) { backStackEntry ->
            val weatherId = backStackEntry.arguments?.getLong("weatherId") ?: 0L
            EditWeather(navController = navController,
                weatherId = weatherId,
                onReturnToWeatherList = { navController.popBackStack() })
        }
    }

}

sealed class WeatherRoutes(val route: String) {
    object EditWeather : WeatherRoutes("edit/{weatherId}") {
        fun createRoute(weatherId: Long) = "edit/$weatherId"
    }
    object Weathers : WeatherRoutes("weathers")
}