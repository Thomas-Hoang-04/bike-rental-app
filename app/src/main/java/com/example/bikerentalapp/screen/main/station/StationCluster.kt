package com.example.bikerentalapp.screen.main.station

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.model.Station
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.clustering.rememberClusterManager
import com.google.maps.android.compose.clustering.rememberClusterRenderer

data class StationClusterItem(
    val station : Station,
) : ClusterItem {
    override fun getPosition() = LatLng(station.lat, station.lng)
    override fun getTitle() = "Station ${station.id}"
    override fun getSnippet() = "Has ${station.numberBicycle} bicycles"
    override fun getZIndex() = 0f
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun StationCluster(
    stationList: List<Station>,
    onClusterClick: (Cluster<out ClusterItem>) -> Boolean = { false },
    onStationClick: (ClusterItem) -> Boolean = { false },
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val clusterManager = rememberClusterManager<StationClusterItem>()

    clusterManager?.setAlgorithm(
        NonHierarchicalViewBasedAlgorithm(
            screenWidth.value.toInt(),
            screenHeight.value.toInt()
        )
    )

    val renderer = rememberClusterRenderer(
        clusterContent = { cluster ->
            ClusterContent(
                modifier = Modifier.size(50.dp),
                text = "%,d".format(cluster.size),
            )
        },
        clusterItemContent = {
            ClusterItemContent(
                modifier = Modifier.width(70.dp),
                text = it.station.numberBicycle.toString(),
            )
        },
        clusterManager = clusterManager,
    )

    SideEffect {
        clusterManager ?: return@SideEffect
        clusterManager.setOnClusterClickListener {
            onClusterClick(it)
        }
        clusterManager.setOnClusterItemClickListener {
            onStationClick(it)
        }
        clusterManager.setOnClusterItemInfoWindowClickListener {
            Log.d("TAG", "Cluster item info window clicked! $it")
        }
    }
    SideEffect {
        if (clusterManager?.renderer != renderer) {
            clusterManager?.renderer = renderer ?: return@SideEffect
        }
    }

    if (clusterManager != null) {
        Clustering(
            items = stationList.map { StationClusterItem(it) },
            clusterManager = clusterManager,
        )
    }
}

@Composable
private fun ClusterContent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier,
        shape = CircleShape,
        color = PrimaryColor,
        contentColor = Color.White,
        border = BorderStroke(1.dp, Color.White)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ClusterItemContent(
    modifier: Modifier,
    text: String,
){
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        color = PrimaryColor,
        contentColor = Color.White,
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.12f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp, 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                val context = LocalContext.current
                val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bicycle_cluster)
                val imageBitmap: ImageBitmap = bitmap.asImageBitmap()
                Image(bitmap = imageBitmap, contentDescription = null, modifier = Modifier.size(15.dp))
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}