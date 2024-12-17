@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.connectionBetweenTwoBlocks
import solutions.utilities.isSquare

// example: 239be575 block connection

class S30BlockConnection : BidirectionalBaseClass() {
	override val name: String = "block connection"

	var checkedOutput = false
	var trueColor = 0
	var falseColor = 0
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		// results must be a single point
		if (outputMatrix.size != 1 && outputMatrix[0].size != 1) {
			return emptyList()
		}

		// find the two marker blocks
		val markerBlocks = inputBlockList.filter {
			it.coordinates.size == 4 && isSquare(it.coordinates)
		}
		// should be only two
		if (markerBlocks.size != 2) {
			return emptyList()
		}

		// now see if there is a connection
		val answer = connectionBetweenTwoBlocks(inputMatrix, markerBlocks[0], markerBlocks[1])
		if (answer) {
			trueColor = outputMatrix[0][0]
		} else {
			falseColor = outputMatrix[0][0]
		}

		return outputMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		// find the two marker blocks
		val markerBlocks = inputBlockList.filter { it.coordinates.size == 4}
		// should be only two
		if (markerBlocks.size != 2) {
			return emptyList()
		}

		// now see if there is a connection
		val answer = connectionBetweenTwoBlocks(inputMatrix, markerBlocks[0], markerBlocks[1])
		if (answer) {
			return listOf(listOf(trueColor))
		}
		return listOf(listOf(falseColor))
	}
}
