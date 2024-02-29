package common

import model.Complex

/**Fast Fourier Transform**/
object FFT {

    fun fft(x: Array<Complex>, isFft: Boolean): Array<Complex> {
        val X: Array<Complex>
        val N = x.size
        if (N == 2) {
            X = Array<Complex>(2) {
                Complex(0.0, 0.0)
            }
            X[0] = x[0] + x[1]
            X[1] = x[0] - x[1]
        } else {

            val x_even = Array<Complex>(N / 2) { Complex(0.0, 0.0) }
            val x_odd = Array<Complex>(N / 2) { Complex(0.0, 0.0) }
            for (i in 0 until N / 2) {
                x_even[i] = x[2 * i]
                x_odd[i] = x[2 * i + 1]
            }
            val X_even = fft(x_even, isFft)
            val X_odd = fft(x_odd, isFft)
            X = Array<Complex>(N) { Complex(0.0, 0.0) }
            for (i in 0 until N / 2) {
                if (isFft) {
                    X[i] = X_even[i] + W(i, N) * X_odd[i]
                    X[i + N / 2] = X_even[i] - W(i, N) * X_odd[i]
                } else {
                    X[i] = X_even[i] + M(i, N) * X_odd[i]
                    X[i + N / 2] = X_even[i] - M(i, N) * X_odd[i]

                }
            }
        }
        return X
    }

    private fun W(k: Int, N: Int): Complex {
        return if (k % N == 0) Complex(1.0, 0.0) else {
            val arg = -2 * Math.PI * k / N.toDouble()
            Complex(Math.cos(arg), Math.sin(arg))
        }
    }

    private fun M(k: Int, N: Int): Complex {
        return if (k % N == 0) Complex(1.0, 0.0) else {
            val arg = 2 * Math.PI * k / N.toDouble()
            Complex(Math.cos(arg), Math.sin(arg))
        }
    }


}