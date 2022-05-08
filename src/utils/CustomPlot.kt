package utils

import Grid

interface CustomPlot {

    fun graph_func(grid: Grid, l:String):Unit{}

    fun graph_func(grid: Grid, points:ArrayList<Point2D>):Unit{
        grid.emplace(points)
    }
}