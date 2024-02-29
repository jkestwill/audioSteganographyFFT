package view

import presenter.MainPresenter
import repository.WavFileRepositoryImpl

class Main() : View {
    private val wavFileRepository = WavFileRepositoryImpl()
    private val mainPresenter = MainPresenter(this, wavFileRepository = wavFileRepository)
    override fun show(message: Any) {
        println(message)
    }


    override fun onCreate() {
      new()

    }

    fun new(){
        val orign ="C:\\Users\\admin\\Downloads\\serega.wav"
        mainPresenter.readFile(orign )
        mainPresenter.encode("I hate steganography")
        mainPresenter.decode()
        mainPresenter.writeBytes("C:\\Users\\admin\\Downloads\\piratCoded.wav")
    }

/**
 * Decode file modified by audio filters
 * **/
    fun filter(){
        mainPresenter.readFile("D:/curch/piratCode.wav" )
        mainPresenter.decode()
    }

// фвч 001010011101111001001001001000100010
// фнч 001010101111111001010100010001100110


}