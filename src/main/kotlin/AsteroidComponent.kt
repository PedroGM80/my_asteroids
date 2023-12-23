import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun Asteroid(asteroidData: AsteroidData) {
    val asteroidSize = asteroidData.size.dp
    val infiniteTransition = rememberInfiniteTransition()
    var n = Math.random() * 1000 + 800
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(n.toInt(), easing = LinearEasing)
        )
    )
    Box(
        Modifier
            .offset(asteroidData.xOffset, asteroidData.yOffset)
            .size(asteroidSize)
            .rotate(angle.toFloat())
            //.rotate(asteroidData.angle.toFloat())
            .clip(CircleShape)
            .background(Color.Transparent)

    ) {
        val imageModifier = Modifier
        Image(
            bitmap = imageFromResource("img/meteorBig.png"),
            "image",
            imageModifier,
            contentScale = ContentScale.FillHeight,
        )
    }
}


