import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

const val SHIP_SPEED_MAX = 15

fun main() = Window(size = IntSize(800, 900), title = "Asteroids for Desktop") {

    val game = remember { Game() }
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos {
                game.update(it)
            }
        }
    }
    val requester = remember { FocusRequester() }
    Box(
        Modifier
            .onKeyEvent {
                when (it.key) {
                    Key.Spacebar -> {
                        game.ship.fire(game)
                    }
                    Key.Z -> {
                        if (game.ship.speed > 0){ game.ship.speed--}
                    }
                    Key.A -> {
                        if (game.ship.speed < SHIP_SPEED_MAX) {game.ship.speed++}
                    }
                }
                true
            }
            .focusRequester(requester)
            .focusable()
            .size(10.dp)
    )
    LaunchedEffect(Unit) { requester.requestFocus() }

    Column(modifier = Modifier.background(Color(51, 153, 255)).fillMaxHeight()) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button({
                game.startGame()
            }) {
                Text("Play")
            }
            Text(
                game.gameStatus+"   Score: "+game.score+"   Speed: "+game.ship.speed.toInt()+"%   Live: "+game.ship.energy+"%",
                modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 16.dp),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
                .background(Color(0, 0, 30))
                .fillMaxWidth()
                .fillMaxHeight()
        ) {     val imageModifier = Modifier
            Image(
                bitmap = imageFromResource("back.png"),
                "image",
                imageModifier,
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clipToBounds()
                    .pointerMoveFilter(onMove = {
                        with(density) {
                            game.targetLocation = DpOffset(it.x.toDp(), it.y.toDp())
                        }
                        false
                    })
                    //.clickable() {game.ship.fire(game)}
                    .onSizeChanged {
                        with(density) {
                            game.width = it.width.toDp()
                            game.height = it.height.toDp()
                        }
                    }) {
                game.gameObjects.forEach {
                    when (it) {
                        is ShipData -> Ship(it)
                        is BulletData -> Bullet(it)
                        is AsteroidData -> Asteroid(it)
                    }
                }
            }
        }
    }
}