package utils

import kotlin.math.*

class Complex {

    var r:Double
    var j:Double

    constructor(r:Double=0.0, j:Double=0.0){
        this.r = r
        this.j = j
    }

    constructor(phase:Angle, mag:Number){
        this.r = mag.toDouble()*cos(phase.ang)
        this.j = mag.toDouble()*sin(phase.ang)
    }

    constructor(phase:Angle, c:Complex){
        val re = Complex(phase, 1)
        this.r = (c*re).r
        this.j = (c*re).j
    }

    /*
     *
     *(a+bj(c+dj)
     * ac-bd + adj + cbj
     *
     */

    operator fun times(c:Complex): Complex = Complex(this.r * c.r - this.j * c.j, this.r * c.j + c.r * this.j)

    operator fun times(s:Number): Complex = Complex(this.r * s.toDouble(), this.j * s.toDouble())

    operator fun plus(c:Complex): Complex = Complex(this.r + c.r, this.j + c.j)

    operator fun div(s:Number): Complex = Complex(this.r/s.toDouble(), this.j/s.toDouble())

    val mag:Double
        get() = sqrt(r.square + j.square)

    val angle:Double
        get() = atan2(this.j, this.r)

    val to_string:String
        get() = "${round(r*1e4)/1e4}+${round(j*1e4)/1e4}j"


}