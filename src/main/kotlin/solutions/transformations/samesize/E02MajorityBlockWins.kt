@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix

// example: 9565186b majority block wins

class E02MajorityBlockWins : BidirectionalBaseClass() {
	override val name: String = "majority block wins"

	// mapping now is column number to color
	val accumulatedColorMapping = mutableMapOf<Int, Int>()

	var checkedOutput = false
	var outputMatrixFillColor = 0
	var numRows = 0
	var numCols = 0

	override fun resetState() {
		checkedOutput = false
		outputMatrixFillColor = 0
	}

	// biggest block wins.
	//   save other block color for the fill color
	override fun testTransform(): List<List<Int>> {



		if (inputBlockList.isEmpty()) {
			return emptyList()
		}
		// find biggest block
		val sortedBlocks = inputBlockList.sortedByDescending() { it.coordinates.size }
		val winnerBlock = sortedBlocks[0]

		if (!checkedOutput) {
			if (outputBlockList.size != 2) {
				return emptyList()
			}
			// find the "other" block and save its color
			var otherBlock = outputBlockList[0]
			if (otherBlock.coordinates == winnerBlock.coordinates) {
				otherBlock = outputBlockList[1]
			}
			outputMatrixFillColor = otherBlock.color
			numRows = outputMatrix.size
			numCols = outputMatrix[0].size
			checkedOutput = true
		}

		val retMatrix = recreateMatrix(
			numRows, numCols,
			listOf(winnerBlock),
			emptyList(), // no points
			fillColor = outputMatrixFillColor)

		return retMatrix

	}

	/**
	map the colors of the input blocks according to the
	accumulated color mapping and then create the output
	matrix for the test and return it.
	 */
	override fun returnTestOutput(): List<List<Int>> {
		if (inputBlockList.isEmpty()) {
			return emptyList()
		}
		// find biggest block
		val sortedBlocks = inputBlockList.sortedByDescending() { it.coordinates.size }
		val winnerBlock = sortedBlocks[0]

		val retMatrix = recreateMatrix(
			numRows, numCols,
			listOf(winnerBlock),
			emptyList(), // no points
			fillColor = outputMatrixFillColor)

		return retMatrix
	}



}