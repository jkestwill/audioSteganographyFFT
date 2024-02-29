package presenter

import repository.WavFileRepository
import repository.WavFileRepositoryImpl
import steganography.Koch
import view.View

class MainPresenter(
    private val view: View,
    private val wavFileRepository: WavFileRepository,

    ) {
    private var koch: Koch? = null
    private var array: Array<DoubleArray> = arrayOf()
    fun readFile(path: String) {
        array = wavFileRepository.readWave(path)

    }

    fun encode(text: String) {
        koch = Koch(array, text, 100023)
        koch?.encode()
    }

    fun decode() {
        // it's just for test
        // You can insert text length value in last significant bit
        // and the bit depth must be constant to read it correctly
        // for example you have 0110 text length value and stegocontainer is 0101 0101 0010 0001 -> 0100 0101 0011 0000
        val text = "I hate steganography"
        koch = Koch(array, text, 100023)
        koch?.decode(text.length)
    }

    fun writeBytes(path: String) {
        koch?.let {
            wavFileRepository.writeBytes(path, it.data)
        } ?: wavFileRepository.writeBytes(path, array)

    }
}