@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Block
import solutions.transformations.BidirectionalBaseClass

// example: a85d4709 point to row colormap

class E01PointToRowColorMapping : BidirectionalBaseClass() {
	override val name: String = "point to row colormap"

	// mapping now is column number to color
	val accumulatedColorMapping = mutableMapOf<Int, Int>()

	var checkedOutput = false
	var numOutputRows = -1
	var numOutputCols = -1


	override fun resetState() {
		checkedOutput = false
		accumulatedColorMapping.clear()
	}

	/*
	 * point to row color map
	 *    The color mapping cannot be know a priori, so
	 * accumulate the color map of points in columns to colored rows in output
	 * in the examples
	 */
	override fun testTransform(): List<List<Int>> {

		if (taskName == "a85d4709") {
			// println("here now")
		}

		if (!checkedOutput) {
			numOutputRows = outputMatrix.size
			numOutputCols = outputMatrix[0].size
			checkedOutput = true
		}

		if (outputPointList.isNotEmpty()) {
			return emptyList() // no points allowed, only blocks across rows
		}

		if (inputMatrix.size < 2 || inputMatrix[0].size < 2
			|| outputMatrix.size < 2 || outputMatrix[0].size < 2
		) {
			return emptyList()
		}

		if (outputMatrix.size != numOutputRows || outputMatrix[0].size != numOutputCols) {
			return emptyList()
		}

		if (!areAllBlocksHorizontalRows(matrix = outputMatrix, outputBlockList)) {
			return emptyList()
		}

		val whichColumnList = scanMatrix(inputMatrix)
		if (whichColumnList.isEmpty()) {
			return emptyList()
		}

		for (theRow in 0 until inputMatrix.size) {

			val b = findBlockByRow(outputBlockList, theRow)
			if (b == null) {
				return emptyList()
			}
			val color = b.color
			accumulatedColorMapping.putIfAbsent(whichColumnList[theRow], color)

		}

		return outputMatrix
	}

	/**
	map the colors of the input blocks according to the
	accumulated color mapping and then create the output
	matrix for the test and return it.
	 */
	override fun returnTestOutput(): List<List<Int>> {
		val retList : MutableList<List<Int>> = mutableListOf()

		val whichColumnList = scanMatrix(inputMatrix)
		if (whichColumnList.isEmpty()) {
			return emptyList()
		}

		for (row in 0 until numOutputRows) {
			val color = accumulatedColorMapping[whichColumnList[row]]
			if (color == null) {
				return emptyList()
			}
			val list = List(numOutputRows) { color }
			retList.add(list)
		}
		return retList
	}

	// modified Gemini code
	fun areAllBlocksHorizontalRows(matrix: List<List<Int>>, blocks: List<Block>): Boolean {
		val numCols = matrix[0].size

		for (block in blocks) {
			val cols = block.coordinates.map { it.second }.toSet() // Get unique col numbers
			if (cols.size != numCols) { // If the block doesn't cover all cols, it's not a full row
				return false
			}
		}
		return true // If all blocks pass the checks, return true
	}

	/**
	The coordinates are row and column indices into a matrix.
	Please find a Block in a List<Block> that has a row coordinate equal
	to rowNum and return it.
	GROK CODE
	 */
	fun findBlockByRow(blocks: List<Block>, rowNum: Int): Block? {
		val b = blocks.find { block ->
			block.coordinates.any { (row, _) -> row == rowNum }
		}
		return b
	}

	/**
	A matrix in Kotlin is given by List<List<Int>>. Please create a function
	that scans the matrix and verifies that there is only one non-zero point
	per row, and return a List<Int> that gives the column number of
	the point location for each row.
	GROK code follows
	 */
	fun scanMatrix(matrix: List<List<Int>>): List<Int> {
		val result = mutableListOf<Int>()

		for ((rowIndex, row) in matrix.withIndex()) {
			var nonZeroCount = 0
			var nonZeroColumn = -1

			for ((colIndex, value) in row.withIndex()) {
				if (value != 0) {
					nonZeroCount++
					nonZeroColumn = colIndex
				}
			}

			// Check if there is exactly one non-zero element per row
			if (nonZeroCount != 1) {
				return emptyList() // Indicates that the condition is not met
			} else {
				result.add(nonZeroColumn)
			}
		}

		return result
	}

}