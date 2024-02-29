package repository

import model.Wave
import wav.WavFile

import java.io.File

class WavFileRepositoryImpl(

) : WavFileRepository {
    private var wave:WavFile? = null

    override fun readWave(path: String):Array<DoubleArray> {
       wave= WavFile.openWavFile(File(path))

        val data=Array<DoubleArray>(2){
            DoubleArray(wave!!.numFrames.toInt())
        }
        wave?.readFrames(data, wave!!.numFrames.toInt())
      return data
    }

    override fun writeBytes( path: String, array:Array<DoubleArray>) {
        val file = File(path)
        wave?.let {
            val new = WavFile.newWavFile(file, it.numChannels, it.numFrames, it.validBits, it.sampleRate)
            new.writeFrames(array, 0, it.numFrames.toInt())
            new.close()
        }
    }

}