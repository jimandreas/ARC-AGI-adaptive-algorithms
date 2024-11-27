@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import Block
import entities.BlockUtilities
import solutions.utilities.aggregateColorQuantities
import solutions.utilities.countCoordinatesWithSingleColor
import solutions.utilities.countOccurrences
import solutions.utilities.findEnclosedPoint
import solutions.utilities.getMaxCountFromMap
import solutions.utilities.uniqueColors

// example: 2013d3e2  sliding 3x3 window fill
//   return the upper left 3x3 that has one filled row or column


class ScanMatrixWithThreeByThreeViewport : BidirectionalBaseClass() {
	override val name: String = "**** scan matrix with 3x3 viewport"

	var outputMatrixChecked = false
	override fun resetState() {
		outputMatrixChecked = false
	}

	override fun testTransform(): List<List<Int>> {
		// do this one time
		if (!outputMatrixChecked) {
			if (outputMatrix.size != 3 || outputMatrix[0].size != 3) {
				return emptyList()
			}
			if (inputMatrix.size != inputMatrix[0].size) {
				return emptyList() // demand a square matrix
			}
			outputMatrixChecked = true
		}

		val numRows = inputMatrix.size
		val numCols = inputMatrix[0].size
		if ((numRows < 4) || (numCols < 4)) {
			return emptyList()
		}

		return scanWithThreeByThreeViewport(inputMatrix)
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

	/**
	In Kotlin a matrix of List<List<Int>> has some non-zero entries.
	It is much larger than
	3 rows and 3 columns.

	Please take a 3x3 "viewport" and scan the matrix with it.
	Work down from the upper left, that is from the 0,0 coordinate.
	Stop when all columns and all rows of the viewport have a non-zero value.
	Return the 3x3 matrix in the form of List<List<Int>>.
	 */
	fun scanWithThreeByThreeViewport(matrix: List<List<Int>>): List<List<Int>> {
		val numRows = matrix.size
		val numCols = matrix[0].size

		if (numRows < 3 || numCols < 3) {
			return emptyList() // Matrix is too small for a 3x3 viewport
		}

		for (d in 0..numRows - 3) {

			val i = d  // scan down in a 45 degree angle
			val j = d
			val viewport = matrix.subList(i, i + 3).map { it.subList(j, j + 3) }
			if (hasNonZeroRowsAndCols(viewport)) {
				return viewport
			}
		}
		return emptyList() // No suitable viewport found
	}

	/*
	Modified slightly so that passing conditions are:
	A row or column is completely filled
	or
	All rows and columns have a non-zero value
	 */
	fun hasNonZeroRowsAndCols(viewport: List<List<Int>>): Boolean {

		if (viewport.any { row -> row.all { it != 0 } } || // Check rows
			(0..2).any { col -> viewport.all { row -> row[col] != 0 } }) { // Check columns
			return true
		}

		// Check rows
		for (row in viewport) {
			if (row.all { it == 0 }) {
				return false
			}
		}

		// Check columns
		for (j in 0..2) {
			if (viewport[0][j] == 0 && viewport[1][j] == 0 && viewport[2][j] == 0) {
				return false
			}
		}

		return true
	}
}