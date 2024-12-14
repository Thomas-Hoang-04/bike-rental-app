package com.example.bikerentalapp.screen.main

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bikerentalapp.R
import com.example.bikerentalapp.components.LocalNavigation
import com.example.bikerentalapp.components.NavBarShape
import com.example.bikerentalapp.components.navigationItems
import com.example.bikerentalapp.screen.main.qrcode.QrCodeResultScreen
import com.example.bikerentalapp.screen.main.qrcode.QrScreen
import com.example.bikerentalapp.screen.main.qrcode.TrackingMapScreen
import com.example.bikerentalapp.screen.main.station.StationsScreen
import com.example.bikerentalapp.navigation.navigationItems
import com.example.bikerentalapp.navigation.Screens
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.example.bikerentalapp.ui.theme.PrimaryNavBarColor
import com.example.bikerentalapp.ui.theme.PrimarySelectedColor
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MainScreen(
    content: @Composable (PaddingValues) -> Unit
) {
    val navController = LocalNavigation.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        bottomBar = { BottomNav() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    navController.navigate(Screens.Main.QRCode){
                        //other destinations in the back stack (before the start destination) are popped,
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        //if the user selects the same item (route) again, it doesn’t create a new instance of that destination
                        launchSingleTop = true
                        //restores the state of the destination when the user returns to it
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .offset(y = 48.dp)
                    .size(60.dp),
                containerColor = PrimaryColor,
                contentColor = Color.White
            ) {
                GlideImage(
                    imageModel = { R.drawable.qr_scan },
                    modifier = Modifier.size(56.dp).clip(CircleShape)
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ){
            composable("home"){
                HomeScreen(onFeatureClick = {}, paddingValues = paddingValues)
            }
            composable("station"){
                StationsScreen()
            }
            composable("notification"){
                NotificationScreen()
            }
            composable("profile"){
                ProfileScreen()
            }
            composable("qrcode") {
                QrScreen(navController)
            }
            composable(
                "qr_result/{qrCodeContent}",
                arguments = listOf(navArgument("qrCodeContent") {
                    try{
                        type = NavType.StringType
                    }catch (e : Exception){
                        Log.e("Qrcode",e.toString())
                    }
                }),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(400)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(400)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(400)
                    )
                }
            ) { backStackEntry ->
                val qrCodeContent = backStackEntry.arguments?.getString("qrCodeContent")
                QrCodeResultScreen(qrCodeContent = qrCodeContent.orEmpty(),navController = navController)
            }
            composable(
                "tracking_map/{bikeId}",
                arguments = listOf(navArgument("bikeId") {
                    try{
                        type = NavType.StringType
                    }catch (e : Exception){
                        Log.e("BikeId",e.toString())
                    }
                }),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(400)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(400)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(400)
                    )
                }
            ) { backStackEntry ->
                val bikeId = backStackEntry.arguments?.getString("bikeId")
                TrackingMapScreen(bikeId = bikeId.orEmpty())
            }
        }
        content(paddingValues)
    }
}

@Composable
fun BottomNav(){
    val navController = LocalNavigation.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val withPx = LocalContext.current.resources.displayMetrics.widthPixels
    val shape = NavBarShape(
        offset = withPx / 2f,
        circleRadius = 30.dp,
        circleGap = 8.dp
    )
    NavigationBar (
        modifier = Modifier
            .height(80.dp)
            .clip(shape),
        containerColor = PrimaryNavBarColor,
    ){
        navigationItems.forEachIndexed{ index, item->
            if(index == 2){
                NavigationBarItem(selected = false, enabled = false, onClick = {}, icon = {})
            }else{
                NavigationBarItem(
                    selected = currentRoute?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                    onClick = {
                        if (currentRoute?.hierarchy?.any { it.hasRoute(item.route::class) } == true) {
                            return@NavigationBarItem
                        }
                        navController.navigate(item.route){
                            popUpTo(navController.graph.startDestinationId){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = PrimaryColor,
                        selectedIconColor = PrimaryColor,
                        indicatorColor = PrimarySelectedColor,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    }
}



