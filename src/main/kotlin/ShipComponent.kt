import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun Ship(shipData: ShipData) {
    val shipSize = shipData.size.dp
    var nave: Any
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
        nave = if (shipData.energy > 50) {

           imageFromResource("img/ex.png")
        } else {

            val i= anima.toInt()
            if(i<=shipData.frames.size-1){
                imageFromResource(shipData.frames[i])
            }else{
                imageFromResource(shipData.frames[0])
            }

        }
         if(shipData.speed<5 ){
            shipData.stop()
        }else{
            shipData.play()
         }
        Image(
            bitmap = nave as ImageBitmap,
            "image",
            imageModifier,
            contentScale = ContentScale.Fit
            )
    }

    }


