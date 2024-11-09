package com.example.bikerentalapp.screen.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bikerentalapp.R
import com.example.bikerentalapp.components.navigationItems
import com.example.bikerentalapp.screen.main.station.StationsScreen
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MainNavigationScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(65.dp),
                cutoutShape = CircleShape,
                backgroundColor = Color.White,
                elevation = 20.dp
            ){
                BottomNav(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    navController.navigate("qrcode"){
                        //other destinations in the back stack (before the start destination) are popped,
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        //if the user selects the same item (route) again, it doesn’t create a new instance of that destination
                        launchSingleTop = true
                        //restores the state of the destination when the user returns to it
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .offset(y = (-6).dp)
                    .size(60.dp),
                containerColor = PrimaryColor,
                contentColor = Color.White
            ) {
                GlideImage(
                    imageModel = { R.drawable.qr_scan},
                    modifier = Modifier.size(56.dp).clip(CircleShape)
                )
            }
        }
    ) {paddingValues ->
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
                QrScreen()
            }
        }
    }

}

@Composable
fun BottomNav(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .height(50.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ){
        navigationItems.forEachIndexed{index,item->
            if(index ==2){
                BottomNavigationItem(selected = false, enabled = false, onClick = { /*TODO*/ }, icon = { /*TODO*/ })
            }else{
                BottomNavigationItem(
                    selected = currentRoute?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
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
                            tint = if (currentRoute?.hierarchy?.any { it.route == item.route } == true) PrimaryColor else Color.Gray,
                        )
                    },
                    selectedContentColor = PrimaryColor,
                    unselectedContentColor = Color.Gray,
                    label = {
                        Text(
                            text = item.label,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                                color = if (currentRoute?.hierarchy?.any { it.route == item.route } == true) PrimaryColor else Color.Gray
                            )
                        )
                    },
                )
            }
        }
    }
}

