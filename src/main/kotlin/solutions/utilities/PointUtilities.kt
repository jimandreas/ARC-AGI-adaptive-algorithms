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

/**
Given one basis Point and a Kotlin List<Point> where the associated List<Point>
have row and column values are absolute values.
Please convert the row and column coordinates to relative
row and column coordinates to the provided basis Point and return the
new list.
Grok code follows:
 */

fun convertToRelativeCoordinates(basis: Point, points: List<Point>): List<Point> {
	val (basisRow, basisCol) = basis.coordinate

	return points.map { point ->
		val (row, col) = point.coordinate
		// Calculate relative coordinates
		val relativeRow = row - basisRow
		val relativeCol = col - basisCol
		// Create a new Point with the same color but new coordinates
		Point(point.color, Pair(relativeRow, relativeCol))
	}
}

