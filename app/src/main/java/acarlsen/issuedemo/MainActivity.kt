package acarlsen.issuedemo

import acarlsen.issuedemo.ui.theme.IssueDemoTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IssueDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {
            Home(navController = navController)
        }

        composable(NavRoutes.HorizontalPagerRecomposes.route) {
            HorizontalPagerIssue()
        }
    }
}

@Composable
fun Home(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Issues",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Button(
                    modifier = Modifier.padding(vertical = 16.dp),
                    onClick = { navController.navigate(NavRoutes.HorizontalPagerRecomposes.route) }) {
                    Text(text = "HorizontalPager recomposes")
                }
            }
        }
    }
}

val items = listOf("https://picsum.photos/id/237/200/300", "https://picsum.photos/id/137/200/300", "https://picsum.photos/id/37/200/300")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerIssue() {
    val pageCount = items.size
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = startIndex)

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val height by remember { mutableStateOf(maxWidth * 0.66f) }
        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) { index ->
            // We calculate the page from the given index
            val page = (index - startIndex).floorMod(pageCount)
            val imageUrl = items[page]

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object HorizontalPagerRecomposes : NavRoutes("horizontalPagerRecomposes")
}
