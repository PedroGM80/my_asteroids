import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun Ship(shipData: ShipData) {
    val shipSize = shipData.size.dp
    Box(
        Modifier
            .offset(shipData.xOffset, shipData.yOffset)
            .size(shipSize)
            .rotate(shipData.visualAngle.toFloat())
            .clip(CircleShape)
            .background(Color.Transparent)
    ) {
        val imageModifier = Modifier
            .clip(RoundedCornerShape(12.dp))
        Image(
            bitmap = imageFromResource("player.png"),
            "image",
            imageModifier,
            contentScale = ContentScale.Fit
        )
    }
}