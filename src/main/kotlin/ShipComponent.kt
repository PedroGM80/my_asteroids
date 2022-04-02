import androidx.compose.animation.core.*
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified

@Composable
fun Ship(shipData: ShipData) {
    val shipSize = shipData.size.dp
    var nave:Any


    val infiniteTransition = rememberInfiniteTransition()
    val frames= shipData.frames

    val imageModifier = Modifier
    val anima by infiniteTransition.animateFloat(
        initialValue = 0.0F,
        targetValue = frames.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation =   tween((shipData.speedFrames*1000).toInt(), easing = LinearEasing)

        )
    )
    Box(
        Modifier
            .offset(shipData.xOffset, shipData.yOffset)
            .size(shipSize*2)
            .rotate(shipData.visualAngle.toFloat())
            .clip(CircleShape)
            .background(Color.Transparent)
    ) {
        if(shipSize.value>50){
            nave=imageFromResource("ex.png")
        }else {
            nave= imageFromResource(frames[anima.toInt()])}
        if(shipData.speed<5){
            nave=imageFromResource("player.png")
        }
            Image(
                bitmap = nave as ImageBitmap,
                "image",
                imageModifier,
                contentScale = ContentScale.Fit
            )
        }

    }
