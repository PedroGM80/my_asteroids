import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.openrndr.math.Vector2
import kotlin.math.atan2
import kotlin.random.Random

const val MAX_SIZE_ASTEROID = 50.0
const val MAX_BULLETS = 3

enum class GameState { STOPPED, RUNNING }

fun Vector2.angle(): Double {
    val rawAngle = atan2(y = this.y, x = this.x)
    return (rawAngle / Math.PI) * 180
}

class Game {
    private var prevTime = 0L
    private var gameState by mutableStateOf(GameState.RUNNING)
    var sourceAnimation=mutableListOf("animtedImg/sprite0_0.png","animtedImg/sprite0_1.png","animtedImg/sprite0_2.png",
        "animtedImg/sprite0_3.png","animtedImg/sprite0_4.png","animtedImg/sprite0_5.png","animtedImg/sprite0_6.png")
    val ship = ShipData(sourceAnimation,1.0F,"img/player.png",sourceAnimation,"img/ex.png")
    var targetLocation by mutableStateOf(DpOffset.Zero)
    var gameObjects = mutableStateListOf<GameObject>()
    var gameStatus by mutableStateOf("Let's play!")


    fun startGame() {
        ship.size = 40.0
        gameObjects.clear()
        ship.position = Vector2(width.value / 2.0, height.value / 2.0)
        ship.movementVector = Vector2.ZERO
        gameObjects.add(ship)
        repeat(3) {
            gameObjects.add(AsteroidData().apply {
                position = Vector2(100.0, 100.0); angle = Random.nextDouble() * 360.0; speed = 2.0
            })
        }
        gameState = GameState.RUNNING
        gameStatus = "Good luck!"
    }

    fun update(time: Long) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time
        val cursorVector = Vector2(targetLocation.x.value.toDouble(), targetLocation.y.value.toDouble())
        val shipToCursor = cursorVector - ship.position
        val bullets = gameObjects.filterIsInstance<BulletData>()
        val asteroids = gameObjects.filterIsInstance<AsteroidData>()
        if (gameState == GameState.STOPPED) {
            //gameObjects.remove(ship)
            return
        }

        ship.visualAngle = shipToCursor.angle()
        ship.movementVector = ship.movementVector + (shipToCursor.normalized * floatDelta.toDouble())

        for (gameObject in gameObjects) {
            gameObject.update(floatDelta, this)
        }


        // Limit number of bullets at the same time
        if (bullets.count() > MAX_BULLETS) {
            gameObjects.remove(bullets.first())
        }

        // Bullet <-> Asteroid interaction
        asteroids.forEach { asteroid ->
            val least = bullets.firstOrNull { it.overlapsWith(asteroid) } ?: return@forEach
            if (asteroid.position.distanceTo(least.position) < asteroid.size) {
                gameObjects.remove(asteroid)
                gameObjects.remove(least)
                if (asteroid.size < MAX_SIZE_ASTEROID) return@forEach
                // it's still pretty big, let's spawn some smaller ones
                repeat(2) {
                    gameObjects.add(AsteroidData(
                        asteroid.speed * 2, Random.nextDouble() * 360.0, asteroid.position
                    ).apply {
                        size = asteroid.size / 2
                    })
                }
            }
        }

        // Asteroid <-> Ship interaction
        if (asteroids.any { asteroid -> ship.overlapsWith(asteroid) }) {
            val least = asteroids.firstOrNull { it.overlapsWith(ship) } ?: return
            ship.energy--
            print(ship.energy)
            if(ship.energy<99) {
                endGame()
            }
        }

        // Win condition
        if (asteroids.isEmpty()) {
            winGame()
        }
    }


    private fun endGame() {
        //Defeult suze 40
        val preSize = ship.size
        val explote = Sound("src/main/resources/sound/explosion.wav", 0)
        explote.play()
        //gameObjects.remove(ship)
        //ship.size=preSize
        print(preSize)
        gameState = GameState.STOPPED
        gameStatus = "Better luck next time!"
    }

    private fun winGame() {
        gameState = GameState.STOPPED
        gameStatus = "Congratulations!"
    }

    var width by mutableStateOf(0.dp)
    var height by mutableStateOf(0.dp)
}