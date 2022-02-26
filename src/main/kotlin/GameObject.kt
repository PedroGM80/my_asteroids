import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.openrndr.math.Vector2
import org.openrndr.math.mod

class ShipData(override var frames: MutableList<String>, override var speedFrames: Float) : GameObject() ,Animable{
    var energy=100
    override var size: Double = 40.0
    var visualAngle: Double = 0.0
    fun fire(game: Game) {
        val shot=Sound("src/main/resources/LaserShot.wav",0)
        shot.play()
        val ship = this
        game.gameObjects.add(BulletData(ship.speed * 4.0, ship.visualAngle, ship.position))
    }
}

class AsteroidData(speed: Double = 0.0, angle: Double = 0.0, position: Vector2 = Vector2.ZERO) :
    GameObject(speed, angle, position) {
    override var size: Double = 120.0
}

class BulletData(speed: Double = 0.0, angle: Double = 0.0, position: Vector2 = Vector2.ZERO) :
    GameObject(speed, angle, position) {
    override val size: Double = 4.0
}

sealed class GameObject(speed: Double = 0.0, angle: Double = 0.0, position: Vector2 = Vector2.ZERO) {
    var speed by mutableStateOf(speed)
    var angle by mutableStateOf(angle)
    var position by mutableStateOf(position)
    abstract val size: Double // Diameter
    var movementVector
        get() = (Vector2.UNIT_X * speed).rotate(angle)
        set(value) {
            speed = value.length
            angle = value.angle()
        }

    fun update(realDelta: Float, game: Game) {
        val obj = this
        val velocity = movementVector * realDelta.toDouble()
        obj.position += velocity
        obj.position = obj.position.mod(Vector2(game.width.value.toDouble(), game.height.value.toDouble()))

    }

    fun overlapsWith(other: GameObject): Boolean {
        // Overlap means the center of the game objects are closer together than the sum of their radius's
        return this.position.distanceTo(other.position) < (this.size / 2 + other.size / 2)
    }
}

