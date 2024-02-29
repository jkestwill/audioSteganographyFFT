
package model

import kotlin.math.*

class Complex(var re: Double, var im: Double):Any() {

    operator fun plus(c: Complex): Complex {
        return Complex(re + c.re, im + c.im)
    }

    operator fun plus(a: Double): Complex {
        return Complex(re+a,im)
    }

    operator fun times(a:Byte):Complex{
        return Complex(this.re * a, this.im * a)
    }

    operator fun plus (a:Byte):Complex{
        return Complex(re+a,im)
    }
    operator fun div(a: Double): Complex {
        return if (this.im != 0.0) {
            Complex(this.re / a, this.im / im)
        }else{
            Complex(this.re/a,im)
        }
    }

    operator fun minus(c:Complex): Complex {
        return Complex(this.re-c.re,this.im-c.im)
    }

    operator fun minus(c:Double): Complex {
        return Complex(this.re-c,this.im)
    }

    operator fun times(c: Complex): Complex {

        return Complex(re * c.re - im * c.im, im * c.re + re * c.im)
    }

    operator fun times(a:Double): Complex {
        return Complex(this.re * a, this.im * a)
    }

    operator fun compareTo(c:Complex):Int{
        val r1= sqrt(c.re.pow(2)+c.im.pow(2))
        val r2= sqrt(re.pow(2)+im.pow(2))
        if(r2>r1) return 1
        if(r2==r1) return 0
        if(r2<r1) return -1
        return 20
    }

    fun getArgument():Double{
        var arg=0.0
        if(re>0.0){
            arg=atan(im/re)
        }
        else if(re<0 && im>=0){
            arg=Math.PI + atan(im/re)
        }
        else if(re<0 && im<0){
            arg=-Math.PI+ atan(im/re)
        }
        return arg
    }

    fun getModule(): Double {
       return  sqrt(re.pow(2)+im.pow(2))
    }

    fun toPolar(): Complex {
        return Complex(getModule()* cos(getArgument()),getModule()* sin(getArgument()))
    }

    override fun toString(): String {
        return "$re + ${im}i"
    }

}