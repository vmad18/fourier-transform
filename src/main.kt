import transformations.FourierTransform
import utils.Part
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    var grid = Grid(50, 50)

    var transform = FourierTransform { 2*sin(3*2*PI*it) + 6*cos(5*2*PI*it) + 9*sin(7*2*PI*it) + 3*cos(9*2*PI*it)} //sum of sinusoids

    transform.transform(transform.sample(0, 1, 1.0/100), 1.0, false)
    transform.graph_func(grid, "*")

    grid.show_reset

    var transform2 = FourierTransform { 1.0 }

    transform2.fourier_area(-.5, .5, freq = 18, wh = 1.0) //pulse function [-.5, .5]
    transform2.transform(transform2.sample(0, 1, 1.0/100), 4.0, true)
    transform2.graph_func(grid, "*")

    grid.show_reset

    transform2.plot_area_curve(grid, Part.REAL, 15)

    grid.show_reset

}
