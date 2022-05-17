package steganography

import common.BinaryToString
import common.FFT
import common.toBinary
import model.Complex
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


class Koch(var data: Array<DoubleArray>, private var text: String, private val textSize:Int, private val seed: Long) : Steganography {
    companion object {
        const val MIN_RATE = 10000
        const val MAX_RATE = 14000
        const val SAMPLE_RATE = 44100
        const val BLOCK = 1024
        const val EPS = 0.4
    }

    init {
        text = text.toBinary()
    }

    private val bottom = MIN_RATE / (SAMPLE_RATE / BLOCK)
    private val top = MAX_RATE / (SAMPLE_RATE / BLOCK)


    private fun toFFT(block: MutableList<Complex>): Array<Complex> {
        val complexArray = block.toTypedArray()
        return FFT.fft(complexArray, true)
    }

    override fun encode() {
        val blockArray = toBlockArray(data[0].toMutableList())
        println(blockArray.size)
        println(text.length)
        val random = Random(seed)
        println(blockArray[0])
        for (i in text.indices) {
            if (i >= text.length) {
                break
            }
            val block = toFFT(blockArray[i])
            var randVal1 = random.nextInt(bottom, top)
            val randVal2 = random.nextInt(bottom, top)

            if (randVal1 == randVal2) {
                randVal1 = random.nextInt(bottom, top)
            }

            if (text[i] == '1') {
                val new =  correctFirstValue(block[randVal1], block[randVal2], EPS)
                block[randVal1] = new
                block[block.size - randVal1] =  new

            } else  {
                val new =  correctSecondValue(block[randVal1], block[randVal2], EPS)
                block[randVal2] =  new
                block[block.size - randVal2] =  new
            }
            blockArray[i] = FFT.fft(block, false).map { Complex(it.re / block.size, 0.0) }.toMutableList()
        }

        val size = blockArray.flatten().size
        val newS = blockArray.flatten().map { it.re }.toMutableList()

        for (i in size until data[0].size) {
            newS.add(data[0][i])
        }
        this.data[0] = newS.toDoubleArray()
        this.data[1] = newS.toDoubleArray()

    }

    override fun decode() {
        val decodeArray = toBlockArray(data[0].toMutableList())
        val random = Random(seed)
        println(text)
        println(decodeArray.size)
        val stringBuilder = StringBuilder()
        for (i in text.indices) {
            val block = toFFT(decodeArray[i])

            var positiveVal1 = random.nextInt(bottom, top)
            val positiveVal2 = random.nextInt(bottom, top)

            if (positiveVal1 == positiveVal2) {
                positiveVal1 = random.nextInt(bottom, top)
            }
            if (abs(block[positiveVal1].toPolar().getModule()) - abs(block[positiveVal2].toPolar().getModule()) > EPS-0.4) {
                stringBuilder.append(1)
            } else  {
                stringBuilder.append(0)
            }
            decodeArray[i] = FFT.fft(block, false).map { Complex(it.toPolar().re / block.size, 0.0) }.toMutableList()
        }
        println(stringBuilder.toString().BinaryToString())
    }

    private fun toBlockArray(array: MutableList<Double>): MutableList<MutableList<Complex>> {
        val blockArray = mutableListOf<MutableList<Complex>>()

        var bot = 0
        var top = 1024
        while (top <= array.size) {

            blockArray.add(array.subList(bot, top).map { Complex(it, 0.0) }.toMutableList())
            bot += 1024
            top += 1024
        }

        return blockArray
    }

    private fun correctFirstValue(value1: Complex, value2: Complex, eps: Double): Complex {
        var v1 = value1.toPolar().re
        val v2 = value2.toPolar().re

        var i = 0
        while (abs(v1) - abs(v2) <= eps) {
            if (v1 >= 0)
                v1 += eps
            else v1 -= eps
            i++
        }
        if (value1.re < 0) {
            i *= -1
        }
        return Complex(value1.getModule()*cos(value1.getArgument())+i*eps, value1.getModule()*sin(value1.getArgument()))
    }

    private fun correctSecondValue(value1: Complex, value2: Complex, eps: Double): Complex {
        val v1 = value1.toPolar().re
        var v2 = value2.toPolar().re
        var i = 0
        while (abs(v1) - abs(v2) >= -eps) {
            if (v2 >= 0)
                v2 += eps
            else v2 -= eps
            i++
        }
        if (value2.toPolar().re < 0) {
            i *= -1
        }
        return Complex(value2.getModule()*cos(value2.getArgument())+i*eps, value2.getModule()*sin(value2.getArgument()))
    }

}