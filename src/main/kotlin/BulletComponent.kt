import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun Bullet(bulletData: BulletData) {
    val bulletSize = bulletData.size.dp
    Box(
        Modifier
            .offset(bulletData.xOffset, bulletData.yOffset)
            .size(bulletSize*3)
            .rotate(bulletData.angle.toFloat())
            .clip(CircleShape)
            .background(Color.Transparent)
    ){
        val imageModifier = Modifier
            .clip(RoundedCornerShape(12.dp))
        Image(
            bitmap = imageFromResource("img/laserRedShot.png"),
            "image",
            imageModifier,
            contentScale = ContentScale.Fit
        )
    }
}
