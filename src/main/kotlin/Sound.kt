import java.io.File
import javax.sound.sampled.Clip
import javax.sound.sampled.AudioInputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.UnsupportedAudioFileException
import javax.sound.sampled.AudioSystem

class Sound(thePath: String, val numberLoop: Int) {
    //define storage for start position
    var nowFrame: Long? = null
    var clip: Clip

    // get the clip status
    var thestatus: String? = null
    var audioStream: AudioInputStream

    // operation is now as per the user's choice
    @Throws(IOException::class, LineUnavailableException::class, UnsupportedAudioFileException::class)
    private fun gotoChoice(a: Int) {
        when (a) {
            1 -> pause()
            2 -> resumeAudio()
            3 -> restart()
            4 -> stop()
            5 -> {
                println(
                    "Selected time (" + 0 +
                            ", " + clip.microsecondLength + ")"
                )
                val scan = Scanner(System.`in`)
                val cc = scan.nextLong()
                jump(cc)
            }
        }
    }

    // play
    fun play() {
        //start the clip
        if (this.numberLoop != 13) {
            clip.loop(this.numberLoop)
        }
        clip.start()
        thestatus = "play"
    }

    // Pause audio
    fun pause() {
        if (thestatus == "paused") {
            println("audio is already paused")
            return
        }
        nowFrame = clip.microsecondPosition
        clip.stop()
        thestatus = "paused"
    }

    // resume audio
    @Throws(UnsupportedAudioFileException::class, IOException::class, LineUnavailableException::class)
    fun resumeAudio() {
        if (thestatus == "play") {
            println(
                "The audio is" +
                        "being played"
            )
            return
        }
        clip.close()
        resetAudioStream()
        clip.microsecondPosition = nowFrame!!
        play()
    }

    // restart audio
    @Throws(IOException::class, LineUnavailableException::class, UnsupportedAudioFileException::class)
    fun restart() {
        clip.stop()
        clip.close()
        resetAudioStream()
        nowFrame = 0L
        clip.microsecondPosition = 0
        play()
    }

    // stop audio
    @Throws(UnsupportedAudioFileException::class, IOException::class, LineUnavailableException::class)
    fun stop() {
        nowFrame = 0L
        clip.stop()
        clip.close()
    }

    // jump to a selected point
    @Throws(UnsupportedAudioFileException::class, IOException::class, LineUnavailableException::class)
    fun jump(a: Long) {
        if (a > 0 && a < clip.microsecondLength) {
            clip.stop()
            clip.close()
            resetAudioStream()
            nowFrame = a
            clip.microsecondPosition = a
            play()
        }
    }

    // reset the audio stream
    @Throws(UnsupportedAudioFileException::class, IOException::class, LineUnavailableException::class)
    fun resetAudioStream() {
        audioStream = AudioSystem.getAudioInputStream(
            File(thePath).absoluteFile
        )
        clip.open(audioStream)
        clip.loop(Clip.LOOP_CONTINUOUSLY)
    }

    companion object {
        var thePath: String? = null

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                //add the path to the audio file
                val Path = "src/main/resources/LaserShot.wav"
                val simpleSoundPlayer = Sound(Path, 0)
                simpleSoundPlayer.play()
                val scanned = Scanner(System.`in`)

                //show the options
                while (true) {
                    println("1. pause")
                    println("2. resume")
                    println("3. restart")
                    println("4. stop")
                    println("5. Jump to specific time")
                    val a = scanned.nextInt()
                    simpleSoundPlayer.gotoChoice(a)
                    if (a == 4) break
                }
                scanned.close()
            } catch (e: Exception) {
                println("Experienced an error while playing sound.")
                e.printStackTrace()
            }
        }
    }

    // initialize both the clip and streams
    init {
        // the input stream object
        audioStream = AudioSystem.getAudioInputStream(
            File(thePath)
                .absoluteFile
        )
        // the reference to the clip
        clip = AudioSystem.getClip()
        clip.open(audioStream)
        clip.loop(Clip.LOOP_CONTINUOUSLY)
    }
}