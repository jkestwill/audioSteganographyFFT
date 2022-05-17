package repository

interface WavFileRepository {

    fun readWave(path: String): Array<DoubleArray>

    fun writeBytes(path:String, array:Array<DoubleArray>)
}