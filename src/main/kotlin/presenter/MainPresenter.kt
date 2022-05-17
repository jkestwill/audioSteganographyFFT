package presenter

import repository.WavFileRepository
import repository.WavFileRepositoryImpl
import steganography.Koch
import view.View

class MainPresenter(private val view: View) {
    private val wavFileRepository: WavFileRepository = WavFileRepositoryImpl()
    private  var koch: Koch?=null

    private var array:Array<DoubleArray>? = null
    fun readFile(path: String) {
        array=wavFileRepository.readWave(path)

    }

    fun encode(text: String) {
        koch= Koch(array!!,text,text.length,100023)
        koch?.encode()
    }

    fun decode(){
        val text="I hate steganography"
        koch=Koch(array!!,text,text.length,100023)
        koch?.decode()
    }

    fun writeBytes(path: String){
        koch?.let {
            wavFileRepository.writeBytes(path,it.data)
        }?:  wavFileRepository.writeBytes(path,array!!)

    }
}