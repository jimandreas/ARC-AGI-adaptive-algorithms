@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Point
import solutions.transformations.BidirectionalBaseClass
import kotlin.math.roundToInt
import kotlin.math.sqrt

// example: cdecee7f rearrange points in a serpentine fashion

class S24PointsToSerpentine : BidirectionalBaseClass() {
	override val name: String = "rearrange points in a serpentine fashion"

	var checkedOutput = false
	var minSideInteger = 0
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "cdecee7f") {
			// // println("here now")
		}

		if (!checkedOutput) {

			val rowCount = outputMatrix.size
			val colCount = outputMatrix[0].size
			if (rowCount != colCount) {
				return emptyList()//demand square output matrix
			}
			val cellCount = rowCount * colCount

			val pointCount = inputPointList.size
			val minSide = sqrt(pointCount.toFloat())+1
			minSideInteger = minSide.roundToInt()
			if (minSideInteger != rowCount) {
				return emptyList()
			}
			// demand no more than one point per column (in input)
			if (pointCount > inputMatrix[0].size) {
				return emptyList()
			}
			checkedOutput = true
		}

		if (inputBlockList.isNotEmpty() || inputPointList.size == 0) {
			return emptyList()
		}

		val pointCount = inputPointList.size
		val minSide = sqrt(pointCount.toFloat())+0.49f
		minSideInteger = minSide.roundToInt()

		val retList = arrangePointsSerpentine(inputPointList, minSideInteger)
		return retList

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

	/**
	I have a list of Point data classes each with a coordinate in a matrix of Ints.
	Each point the sole point in a column in the matrix.  Please reorder
	these points to that in a per-column ordering they now fill a
	3x3 matrix of the form List<List<Int> -
	but in a serpentine pattern.   That means the first point will go to 0,0
	and the second to 0,1 but at the end of the row the filling will turn around
	and go from right to left.
	 Gemini code follows.
	 CHANGES: changed to be a parametric square matrix
	 */
	fun arrangePointsSerpentine(points: List<Point>, mSize: Int): List<List<Int>> {
		if (points.isEmpty()) return emptyList()

		val sortedPoints = points.sortedBy { it.coordinate.second } // Sort by column
		val matrix = MutableList(mSize) { MutableList(mSize) { 0 } }

		var row = 0
		var col = 0
		var direction = 1 // 1 for left to right, -1 for right to left

		for (point in sortedPoints) {
			matrix[row][col] = point.color
			col += direction

			if (col == mSize) { // Reached the end of the row
				col = mSize-1
				row++
				direction = -1 // Change direction
			} else if (col == -1) { // Reached the beginning of the row
				col = 0
				row++
				direction = 1 // Change direction
			}
		}

		return matrix
	}
}
