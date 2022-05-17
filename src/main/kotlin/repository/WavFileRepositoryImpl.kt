package repository

import wav.WavFile

import java.io.File

class WavFileRepositoryImpl : WavFileRepository {
    var wave:WavFile?=null

    override fun readWave(path: String):Array<DoubleArray> {
       wave= WavFile.openWavFile(File(path))

        val data=Array<DoubleArray>(2){
            DoubleArray(wave!!.numFrames.toInt())
        }

        wave!!.readFrames(data,wave!!.numFrames.toInt())
        println(wave!!.framesRemaining)
      return data
    }

    override fun writeBytes( path: String, array:Array<DoubleArray>) {
       val new= WavFile.newWavFile(File(path),wave!!.numChannels,wave!!.numFrames,wave!!.validBits,wave!!.sampleRate)
        new.writeFrames(array,0,wave!!.numFrames.toInt())
        new.close()

    }

}