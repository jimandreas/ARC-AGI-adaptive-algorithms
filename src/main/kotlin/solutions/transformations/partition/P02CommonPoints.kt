@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.partition

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.compareMatricesAndReturnMatchingPoints
import solutions.utilities.findFirstNonZero
import solutions.utilities.partitionMatrix

// example 0520fde7 common points in partition


class P02CommonPoints : BidirectionalBaseClass() {
	override val name: String = "common points in partition"

	var checkedOutput = false
	var outputColor = 0

	// cache output matrix size
	var rowCount = 0
	var colCount = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "0520fde7") {
			println("here now")
		}

		// input matrix must be an odd number with the partition line in the middle
		if (inputMatrix[0].size % 2 != 1) {
			return emptyList()
		}
		val partitionColor = inputMatrix[0][inputMatrix[0].size / 2]
		val partitionedMatrices = partitionMatrix(
			inputMatrix,
			colorSpecified = true,
			partitionColor = partitionColor)
		val pCount = partitionedMatrices.size
		if (pCount == 0) {
			return emptyList()
		}
		rowCount = partitionedMatrices[0].size // remember it is a List of matrices!
		colCount = partitionedMatrices[0][0].size

		// cache the output color
		if (!checkedOutput) {
			if (outputMatrix.size != rowCount || outputMatrix[0].size != colCount) {
				return emptyList()
			}
			val firstNonZero = findFirstNonZero(outputMatrix)
			if (firstNonZero == null) {
				return emptyList()
			}
			outputColor = firstNonZero
			checkedOutput = true
		}

		if (pCount != 2) {
			return emptyList()
		}
		val retList = compareMatricesAndReturnMatchingPoints(
			partitionedMatrices[0],
			partitionedMatrices[1],
			outputColor
		)

		return retList
	}

	// now match the partition matrices to get the color.

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}
}