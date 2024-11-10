package com.example.bikerentalapp.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class NavBarShape(
    private val offset: Float,
    private val circleRadius: Dp,
    private val circleGap: Dp = 5.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density): Outline = Outline.Generic(getPath(size, density))

    private fun getPath(size: Size, density: Density): Path {
        val cutoutCenterX = offset
        val cutoutRadius = density.run { (circleRadius + circleGap).toPx() }
        return Path().apply {
            val cutoutLeftX = cutoutCenterX - cutoutRadius
            val cutoutRightX = cutoutCenterX + cutoutRadius

            moveTo(x = 0f, y = 0f)
            lineTo(cutoutLeftX, 0f)

            // Draw the arc
            arcTo(
                rect = Rect(
                    left = cutoutCenterX - cutoutRadius,
                    top = -cutoutRadius,
                    right = cutoutCenterX + cutoutRadius,
                    bottom = cutoutRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            lineTo(cutoutRightX, 0f)
            lineTo(x = size.width, y = 0f)
            lineTo(x = size.width, y = size.height)
            lineTo(x = 0f, y = size.height)
            close()
        }
    }
}