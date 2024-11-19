# Asteroids, built with Compose for Desktop

### Want to learn how I built it?
### Read the [two-part article](https://dev.to/kotlin/how-i-built-an-asteroids-game-using-jetpack-compose-for-desktop-309l) in which I describe the most interesting parts of this project.

`asteroids-compose-for-desktop` is a basic experiment of building a 2D space game on top of [Compose for Desktop](https://www.jetbrains.com/lp/compose/). 🚀 Run with `./gradlew run`. Feel free to explore, and enjoy! 🌕

![Screenshot of the game](dev-graphics2.png)

- First change use the spacebar to shoot
- Adding controls to speed up and slow down the ship
- Added graphics from opengameart.org artists
- Added points
- Added energy
- Added interface Animable
- Added animations rocket

- For the background:
[Westbeam](https://opengameart.org/content/space-background-1#:~:text=Author%3A%C2%A0-,Westbeam,-Sunday%2C%20April%2014)

- For the rest of the graphics
[Kenney](  https://opengameart.org/users/kenney)
[Space Shooter art pac](https://opengameart.org/content/space-shooter-redux)
 
 ## Graphics now
 
![Now](https://github.com/PedroGM80/my_asteroids/blob/The_end_/demo.png?raw=true)


https://claude.site/artifacts/5153259b-e69b-4840-a4e9-4974813c92da


https://claude.site/artifacts/924a0201-378b-42f7-839a-40b4a5b5650c





import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothProfile

class BluetoothAudioHelper(private val context: Context) {
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    fun connectAudioDevice(audioDevice: AudioDeviceInfo?) {
        audioDevice?.let {
            when (it.type) {
                AudioDeviceInfo.TYPE_BLUETOOTH_SCO -> {
                    audioManager.isBluetoothScoOn = true
                    audioManager.startBluetoothSco()
                }
                AudioDeviceInfo.TYPE_BLUETOOTH_A2DP -> {
                    bluetoothAdapter?.takeIf { it.isEnabled }?.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
                        override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                            if (profile == BluetoothProfile.A2DP) {
                                audioManager.isBluetoothA2dpOn = true
                                // Manejar la conexión de A2DP
                            }
                        }

                        override fun onServiceDisconnected(profile: Int) {
                            if (profile == BluetoothProfile.A2DP) {
                                audioManager.isBluetoothA2dpOn = false
                            }
                        }
                    }, BluetoothProfile.A2DP)
                }
                else -> {
                    // Manejar otros tipos de dispositivos de audio si es necesario
                }
            }
        }
    }

    fun disableAudioDevice(audioDevice: AudioDeviceInfo?) {
        audioDevice?.let {
            when (it.type) {
                AudioDeviceInfo.TYPE_BLUETOOTH_SCO -> {
                    audioManager.stopBluetoothSco()
                    audioManager.isBluetoothScoOn = false
                }
                AudioDeviceInfo.TYPE_BLUETOOTH_A2DP -> {
                    audioManager.isBluetoothA2dpOn = false
                    // Desconectar el dispositivo A2DP si es necesario
                }
                else -> {
                    // Manejar otros tipos de dispositivos de audio si es necesario
                }
            }
        }
    }
}


https://claude.site/artifacts/0e0169af-7385-4de5-914b-543c7e6d3ad0

https://claude.site/artifacts/8531a18b-8925-4f11-a46e-cb00fb747a80


https://claude.site/artifacts/4e7997f1-aa1f-4715-a2f3-0d6caa45dd64


https://claude.site/artifacts/b29cec09-40e4-4633-8ad2-d8b8aed3cb2c


https://claude.site/artifacts/0a9dcdc4-d1c9-4a8b-838b-ad03bb531c97
