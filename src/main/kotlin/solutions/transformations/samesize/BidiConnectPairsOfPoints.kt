@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import kotlin.collections.groupingBy
import kotlin.collections.iterator

// example: 1f876c06
class BidiConnectPairsOfPoints : BidirectionalBaseClass() {
	override val name: String = "BIDI connect pairs of points"

	val accumulatedColorMapping = mutableMapOf<Int, Int>()

	/*
	 * connect pairs of points that have the same color
	 */
	override fun testTransform(): List<List<Int>> {

		if (inputBlockList.isNotEmpty()) {
			return emptyList()  // no blocks are allowed
		}

		// point list must have two points of same color for each occurrence
		if (!hasExactlyTwoPointsOfSameColor(inputPointList)) {
			return emptyList()
		}

		val pointsList = connectMatchingPoints(
			inputMatrix.size,
			inputMatrix[0].size,
			inputPointList)

		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			emptyList(),
			pointsList
		)
		return outputMatrix
	}

	/**
	 * connect pairs of points that have the same color
	 */
	override fun returnTestOutput(): List<List<Int>> {
		if (inputBlockList.isNotEmpty()) {
			return emptyList()  // no blocks are allowed
		}

		// point list must have two points of same color for each occurrence
		if (!hasExactlyTwoPointsOfSameColor(inputPointList)) {
			return emptyList()
		}

		val pointsList = connectMatchingPoints(
			inputMatrix.size,
			inputMatrix[0].size,
			inputPointList)

		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			emptyList(),
			pointsList
		)
		return outputMatrix
	}

	/**
	In Kotlin points in a matrix of form List<List<Int>> and of
	size rowCount and colCount are expressed as a list in the following form:

	data class Point(val color: Int, val coordinate: Pair<Int, Int>)

	Please confirm that the list contains some occurrence of precisely
	two points of matching color - with the number of matching points
	unknown.  Then please connect the matching points spatially in
	the matrix with additional points of the same color.  Repeat
	until all matching points are connected.  You can ignore any
	cases where there are multiple points that might be generated
	for a single cell in the matrix.  Please return the new list of points.
	 Gemini code follows
	 */

	fun connectMatchingPoints(rowCount: Int, colCount: Int, points: List<Point>): List<Point> {
		val pointsByColor = points.groupBy { it.color }
		val resultPoints = points.toMutableList()

		for ((color, colorPoints) in pointsByColor) {
			if (colorPoints.size == 2) { // Found exactly two points with the same color
				val (p1, p2) = colorPoints
				val (row1, col1) = p1.coordinate
				val (row2, col2) = p2.coordinate

				// Connect horizontally
				if (row1 == row2) {
					for (col in minOf(col1, col2) + 1 until maxOf(col1, col2)) {
						resultPoints.add(Point(color, Pair(row1, col)))
					}
				}
				// Connect vertically
				else if (col1 == col2) {
					for (row in minOf(row1, row2) + 1 until maxOf(row1, row2)) {
						resultPoints.add(Point(color, Pair(row, col1)))
					}
				}
				// Connect diagonally (if needed)
				else {
					val rowStep = if (row2 > row1) 1 else -1
					val colStep = if (col2 > col1) 1 else -1
					var row = row1 + rowStep
					var col = col1 + colStep
					while (row != row2 && col != col2) {
						resultPoints.add(Point(color, Pair(row, col)))
						row += rowStep
						col += colStep
					}
				}
			}
		}

		return resultPoints
	}

	/**
	Could you please create a helper function that takes the
	list of Point data and confirms that there are two points
	present for a particular color.  If so, return true.  Return
	false if the list contains Point data with only one occurrence
	of a color, or  three or more occurrences of a particular color.
	Gemini code follows
	 */

	fun hasExactlyTwoPointsOfSameColor(points: List<Point>): Boolean {
		val colorCounts = points.groupingBy { it.color }.eachCount()
		return colorCounts.containsValue(2)
	}
}