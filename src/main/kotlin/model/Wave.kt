package model

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Wave(private val path: String) {

    private lateinit var fileInputStream: FileInputStream
    private lateinit var fileOutputStream: FileOutputStream

     var header: Header = Header()

    private var file = File(path)

     var data:ByteArray? = null
    fun read(): ByteArray {
        try {
            fileInputStream = FileInputStream(file)
            fileInputStream.read(header.chunkId)
            fileInputStream.read(header.chunkSize)
            fileInputStream.read(header.format)
            fileInputStream.read(header.subchunk1Id)
            fileInputStream.read(header.subchunk1size)
            fileInputStream.read(header.audioFormat)
            fileInputStream.read(header.numChannels)
            fileInputStream.read(header.sampleRate)
            fileInputStream.read(header.byteRate)
            fileInputStream.read(header.blockAlign)
            fileInputStream.read(header.bitsPerSample)
            header.subchunk2Id=skipTrashData()
            fileInputStream.read(header.subchunk2Size)
            data= ByteArray(ByteBuffer.wrap(header.subchunk2Size).order(ByteOrder.LITTLE_ENDIAN).int)
            fileInputStream.read(data)
        } catch (e: Exception) {
            println("Error")
        } finally {
            fileInputStream.close()
        }
        println(header)
        return data!!
    }

   private fun skipTrashData(): ByteArray {
        val byteArray=ByteArray(4)
       val trashArray= mutableListOf<Byte>()
       var i=0
        fileInputStream.read(byteArray)
        while(byteArray.decodeToString()!="data"){
            fileInputStream.read(byteArray)
            trashArray.addAll(byteArray.toMutableList())
            i++
            println("##########$i")
        }
       if(trashArray.size!=0){
           header.extradata=trashArray.toByteArray()
       }


        return byteArray
    }


    fun getAmplitude(pos:Int): Int {
        val intBytes=ByteArray(4)
        val sampleSize= ByteBuffer.wrap(header.bitsPerSample).order(ByteOrder.LITTLE_ENDIAN).short/8
        val channelsNum= ByteBuffer.wrap(header.numChannels).order(ByteOrder.LITTLE_ENDIAN).short

        for (i in intBytes.indices){
            intBytes[i]= data!![pos * sampleSize*channelsNum+i]
        }

        return ByteBuffer.wrap(intBytes).order(ByteOrder.LITTLE_ENDIAN).int
    }

    fun getAmplitudeArray():MutableList<Int>{
        val amplitudeArray= mutableListOf<Int>()
        val sampleSize= ByteBuffer.wrap(header.bitsPerSample).order(ByteOrder.LITTLE_ENDIAN).short/8
        for (i in 0 until  data!!.size/sampleSize){
            amplitudeArray.add(getAmplitude(i))
        }
        return amplitudeArray
    }


inner class Header() {
    //RIFF
    var chunkId = ByteArray(4)
    //это размер файла минус 8, то есть, исключены поля chunkId и chunkSize
    var chunkSize = ByteArray(4)
    //WAVE
    var format = ByteArray(4)
    //fmt
    var subchunk1Id = ByteArray(4)

    var subchunk1size=ByteArray(4)
    var audioFormat = ByteArray(2)
    var numChannels = ByteArray(2)

    // частотата дисретизации
    var sampleRate = ByteArray(4)
    var byteRate = ByteArray(4)
    var blockAlign = ByteArray(2)
    var bitsPerSample = ByteArray(2)

    //"data"
    var subchunk2Id = ByteArray(4)
    var extradata:ByteArray? = null
    // data size
    var subchunk2Size = ByteArray(4)




    override fun toString(): String {
        return "chunkId ${chunkId.decodeToString()}\n" +
                "chunkSize ${ByteBuffer.wrap(chunkSize).order(ByteOrder.LITTLE_ENDIAN).int}\n" +
                "subchunk1Id ${subchunk1Id.decodeToString()}\n" +
                "subChunkSize ${ByteBuffer.wrap(subchunk1size).order(ByteOrder.LITTLE_ENDIAN).short}\n" +
                "audioFormat ${ByteBuffer.wrap(audioFormat).order(ByteOrder.LITTLE_ENDIAN).short.toUShort()}\n" +
                "numChannels ${ByteBuffer.wrap(numChannels).order(ByteOrder.LITTLE_ENDIAN).short}\n" +
                "sampleRate ${ByteBuffer.wrap(sampleRate).order(ByteOrder.LITTLE_ENDIAN).int}\n" +
                "byteRate ${ByteBuffer.wrap(byteRate).order(ByteOrder.LITTLE_ENDIAN).int}\n" +
                "blockAlign ${ByteBuffer.wrap(blockAlign).order(ByteOrder.LITTLE_ENDIAN).short.toUShort()}\n" +
                "bitsPerSample ${ByteBuffer.wrap(bitsPerSample).order(ByteOrder.LITTLE_ENDIAN).short.toUShort()}\n" +
                "trash ${if (extradata!=null) extradata!!.decodeToString() else "" }\n" +
                "subchunk2Id ${subchunk2Id.decodeToString()}\n" +
                "dataSize ${ByteBuffer.wrap(subchunk2Size).order(ByteOrder.LITTLE_ENDIAN).int}\n"
    }
}
}