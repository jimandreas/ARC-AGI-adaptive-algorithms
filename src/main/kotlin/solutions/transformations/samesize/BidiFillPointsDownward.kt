@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass

// example: d037b0a7

class BidiFillPointsDownward : BidirectionalBaseClass() {
	override val name: String = "BIDI fill points downward"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		val rowCount = inputMatrix.size
		val colCount = inputMatrix[0].size
		val points = inputPointList

		// coded by Google Gemini
		// Initialize the matrix with all cells set to 0
		val matrix = MutableList(rowCount) { MutableList(colCount) { 0 } }

		// Place points and fill downwards
		for (point in points) {
			val (row, col) = point.coordinate
			if ((row > rowCount - 1) || (col > colCount - 1)) continue
			// Place the point in the matrix
			matrix[row][col] = point.color

			// Fill downwards
			for (r in row + 1 until rowCount) {
				matrix[r][col] = point.color
			}
		}
		return matrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		val rowCount = inputMatrix.size
		val colCount = inputMatrix[0].size
		val points = inputPointList

		// coded by Google Gemini
		// Initialize the matrix with all cells set to 0
		val matrix = MutableList(rowCount) { MutableList(colCount) { 0 } }

		// Place points and fill downwards
		for (point in points) {
			val (row, col) = point.coordinate
			if ((row > rowCount - 1) || (col > colCount - 1)) continue
			// Place the point in the matrix
			matrix[row][col] = point.color

			// Fill downwards
			for (r in row + 1 until rowCount) {
				matrix[r][col] = point.color
			}
		}
		return matrix
	}
}