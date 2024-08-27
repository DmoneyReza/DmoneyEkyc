import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DrawCenteredFrame(
    width: Dp,
    height: Dp,
    cornerRadius: Dp = 16.dp,
    cornerLength: Dp = 30.dp,
    cornerThickness: Dp = 4.dp,
    cornerColor: Color = Color(0xFFFFA500),
    topGayYxis:Float = 2.0f
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Convert Dp values to Px
        val widthPx = width.toPx()
        val heightPx = height.toPx()
        val cornerLengthPx = cornerLength.toPx()
        val cornerThicknessPx = cornerThickness.toPx()
        val cornerRadiusPx = cornerRadius.toPx()

        // Calculate the top-left corner to center the frame
        val canvasWidth = size.width
        val canvasHeight = size.height
        val topLeft = Offset(
            x = (canvasWidth - widthPx) / 2,
            y = ((canvasHeight - heightPx) / topGayYxis).toFloat()
        )

        // Draw the dark background
        drawRect(
            color = Color.DarkGray.copy(alpha = .9f),
            size = size
        )

        // Clear the area in the middle for the transparent rectangle
        drawRoundRect(
            color = Color.Transparent,
            topLeft = topLeft,
            size = androidx.compose.ui.geometry.Size(widthPx, heightPx),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            blendMode = androidx.compose.ui.graphics.BlendMode.Clear
        )

        // Draw outer rectangle with rounded corners centered
        drawRoundRect(
            color = Color(0xFFDDD2D2),
            topLeft = topLeft,
            size = Size(widthPx, heightPx),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            style = Stroke(width = 4f)
        )

        // Draw rounded corners

        // Top-left corner
        drawLine(
            color = cornerColor,
            start = topLeft.copy(x = topLeft.x + cornerRadiusPx),
            end = topLeft.copy(x = topLeft.x + cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawLine(
            color = cornerColor,
            start = topLeft.copy(y = topLeft.y + cornerRadiusPx),
            end = topLeft.copy(y = topLeft.y + cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawArc(
            color = cornerColor,
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = topLeft,
            size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
            style = Stroke(cornerThicknessPx)
        )

        // Top-right corner
        val topRight = topLeft.copy(x = topLeft.x + widthPx)
        drawLine(
            color = cornerColor,
            start = topRight.copy(x = topRight.x - cornerRadiusPx),
            end = topRight.copy(x = topRight.x - cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawLine(
            color = cornerColor,
            start = topRight.copy(y = topRight.y + cornerRadiusPx),
            end = topRight.copy(y = topRight.y + cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawArc(
            color = cornerColor,
            startAngle = 270f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(topRight.x - cornerRadiusPx * 2, topRight.y),
            size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
            style = Stroke(cornerThicknessPx)
        )

        // Bottom-left corner
        val bottomLeft = topLeft.copy(y = topLeft.y + heightPx)
        drawLine(
            color = cornerColor,
            start = bottomLeft.copy(x = bottomLeft.x + cornerRadiusPx),
            end = bottomLeft.copy(x = bottomLeft.x + cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawLine(
            color = cornerColor,
            start = bottomLeft.copy(y = bottomLeft.y - cornerRadiusPx),
            end = bottomLeft.copy(y = bottomLeft.y - cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawArc(
            color = cornerColor,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(bottomLeft.x, bottomLeft.y - cornerRadiusPx * 2),
            size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
            style = Stroke(cornerThicknessPx)
        )

        // Bottom-right corner
        val bottomRight = bottomLeft.copy(x = topLeft.x + widthPx)
        drawLine(
            color = cornerColor,
            start = bottomRight.copy(x = bottomRight.x - cornerRadiusPx),
            end = bottomRight.copy(x = bottomRight.x - cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawLine(
            color = cornerColor,
            start = bottomRight.copy(y = bottomRight.y - cornerRadiusPx),
            end = bottomRight.copy(y = bottomRight.y - cornerLengthPx),
            strokeWidth = cornerThicknessPx
        )
        drawArc(
            color = cornerColor,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(bottomRight.x - cornerRadiusPx * 2, bottomRight.y - cornerRadiusPx * 2),
            size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
            style = Stroke(cornerThicknessPx)
        )
    }
}
