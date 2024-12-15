package com.example.bikerentalapp.screen.main.station

import android.graphics.BitmapFactory
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikerentalapp.R
import com.example.bikerentalapp.api.data.Station
import com.example.bikerentalapp.ui.theme.PrimaryColor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering

data class StationClusterItem(
    val station : Station,
) : ClusterItem {
    override fun getPosition() = LatLng(station.coordinates.lat, station.coordinates.lng)
    override fun getTitle() = station.name
    override fun getSnippet() = "Has ${station.bikeList.size} bicycles"
    override fun getZIndex() = 0f
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun StationCluster(
    stationList: List<Station>,
    onClusterClick: (Cluster<out ClusterItem>) -> Boolean = { false },
    onStationClick: (ClusterItem) -> Boolean = { false },
){
    Clustering(
        items = stationList.map { StationClusterItem(it) },
        onClusterClick = onClusterClick,
        onClusterItemClick = onStationClick,
        clusterContent = { cluster ->
            ClusterContent(
                modifier = Modifier.size(50.dp),
                text = "%,d".format(cluster.size),
            )
        },
        clusterItemContent = { item ->
            ClusterItemContent(
                modifier = Modifier.width(70.dp),
                text = item.station.bikeList.size.toString()
            )
        },
    )
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
        color = if(text.toInt() > 2)PrimaryColor else Color(0xffa5682a),
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