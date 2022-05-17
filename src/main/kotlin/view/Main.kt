package view

import presenter.MainPresenter

class Main : View {

    private val mainPresenter = MainPresenter(this)
    override fun show(message: Any) {
        println(message)
    }


    override fun onCreate() {
      filter()


    }

    fun new(){
        val orign ="D:/Downloads/pirat.wav"
        mainPresenter.readFile(orign )
        mainPresenter.encode("I hate steganography")
        mainPresenter.decode()
        mainPresenter.writeBytes("D:/curch/piratCoded.wav")
    }

    fun filter(){
        mainPresenter.readFile("D:/curch/piratCode.wav" )
        mainPresenter.decode()

    }

// фвч 001010011101111001001001001000100010
// фнч 001010101111111001010100010001100110


}