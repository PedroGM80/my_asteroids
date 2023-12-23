interface Animable {
    var frames: MutableList<String>
    var beforeframes: MutableList<String>
    var speedFrames:Float
    var imageIndex:String
    var extraImage:String
    fun play()
    fun stop()
    fun pause()
    fun boom()
}