@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.utilities

import Point


/**
I have a list of Point in Kotlin as given below with a color Int and row and
column coordinates as a Pair<Int,Int>
Please find the min and max row and column
values in the coordinate list for each color value in the list.
data class Point(val color: Int, val coordinate: Pair<Int, Int>)
 */


//data class Point(val color: Int, val coordinate: Pair<Int, Int>)

fun findMinMaxPointCoordinates(points: List<Point>):
		Map<Int, Pair<Pair<Int, Int>, Pair<Int, Int>>> {
	return points.groupBy { it.color }
		.mapValues { (_, colorPoints) ->
			val rows = colorPoints.map { it.coordinate.first }
			val columns = colorPoints.map { it.coordinate.second }
			Pair(
				Pair(
					rows.minOrNull() ?: Int.MAX_VALUE,  // min row
					rows.maxOrNull() ?: Int.MIN_VALUE   // max row
				),
				Pair(
					columns.minOrNull() ?: Int.MAX_VALUE, // min column
					columns.maxOrNull() ?: Int.MIN_VALUE  // max column
				)
			)
		}
}

/*
// Example usage:
fun main() {
	val points = listOf(
		Point(1, Pair(1, 2)),
		Point(1, Pair(3, 4)),
		Point(2, Pair(5, 6)),
		Point(2, Pair(7, 8))
	)

	val result = findMinMaxCoordinates(points)

	result.forEach { (color, coords) ->
		println("Color $color: Rows=${coords.first}, Columns=${coords.second}")
	}
}*/
