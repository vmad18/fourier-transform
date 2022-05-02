package Transformations

import utils.CustomFunction
import Grid
import utils.Complex
import utils.complex
import utils.rad
import java.lang.Math.pow
import kotlin.math.*


/**
 * @author v18
 *
 */

class FourierTransform(var func:(Double)->(Double)): CustomFunction {

    var omegaf:ArrayList<Complex> = ArrayList()

    var negative:Boolean = false
    var ep = 0.0
    var st = 0.0
    var en = 0.0


    private fun area(r1:Number, r2:Number, di:Double, f:(Double) -> (Double)):Double{

        var a = r1.toDouble()
        var b = r2.toDouble()

        var w = (b-a)/di
        var k = 0.0
        var cx = a + w

        for(i:Int in 1 until (di/2).toInt() + 1){
            k += 4*f.invoke(cx)
            cx += 2*w
        }

        cx = a + 2 * w

        for(i:Int in 1 until (di/2).toInt()){
            k += 2*f.invoke(cx)
            cx += 2*w
        }

        return (w)/3 * (f.invoke(a) + f.invoke(b) + k)
    }

    fun fourier_area(r1:Number, r2:Number, freq:Number=16, wh:Double = 2 * PI, add:Number = .5): HashMap<Double, Complex>{
        var w:Double = -freq.toDouble()
        val complex_area = HashMap<Double, Complex>()
        while(w<=freq.toDouble()) {
            println("${area(r1, r2, 1e6) { cos(-wh * w * it) * func.invoke(it) }} ${area(r1, r2, 1e6) { sin(-wh * w * it) * func.invoke(it) }} $w")
            complex_area[w] = Complex(
                area(r1, r2, 1e6) { cos(-wh * w * it) * func.invoke(it) },
                area(r1, r2, 1e6) { sin(-wh * w * it) * func.invoke(it) }
            )
            w+=add.toDouble()
        }

        return complex_area
    }

    fun sample(s:Number, e:Number, epsilon:Double, skip:Number=Double.MAX_VALUE): ArrayList<Double>{
        var arr = ArrayList<Double>()

        val max:Double = e.toDouble()
        var start = s.toDouble()

        while(start <= max){
            if(start == skip.toDouble()) continue
            arr.add(start)
            start += epsilon
        }

        this.en = e.toDouble()
        this.st = s.toDouble()
        this.ep = epsilon

        return arr
    }


    fun transform(points:ArrayList<Double>, mult:Double = 1.0, negative:Boolean = false): ArrayList<Complex> {

        var samples:ArrayList<Complex> = ArrayList()

        val transformed:ArrayList<Complex> = ArrayList()

        for(p:Double in points) samples.add(func.invoke(p).complex)

        val N:Int = if(negative) 2*samples.size else samples.size

        val nr = if(negative) (-N/2) else 0
        val pr = if(negative) (N/2) else N

        for(n:Int in nr until pr){
            var c = Complex()
            for(k:Int in 0 until pr) {
                var phase = (-2 * PI * k * n / N).rad
                val t = Complex(phase,  samples[k])
                c += t
            }
            transformed.add(c*mult)
        }

        this.omegaf = transformed
        this.negative = negative

        for((cnt, c:Complex) in transformed.withIndex()){
            println("${c.to_string} ${round(c.mag*1e4)/1e4} ${if(negative) cnt-N/2 else cnt}")
        }


        return transformed
    }


    override fun graph_func(grid: Grid, l: String) {
        val len = omegaf.size

        for((cnt, c:Complex) in omegaf.withIndex()){
            println("${((cnt-(if(negative) len/2 else 0))/(abs(en-st))).toInt()} ${2*(abs(en-st))}")
            grid.emplace(((cnt-(if(negative) len/2 else 0))/(abs(en-st))).toInt(), ((round(c.mag*1e4)/1e4) * ep/(abs(en-st)/2)).toInt())
        }
    }


}