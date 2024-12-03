@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass

// example: e9afcf9a blocks to points tiling

class S22BlocksToPointsTiling : BidirectionalBaseClass() {
	override val name: String = "**** blocks to points tiling"

	var checkedOutput = false
	var isBlockToPointsConversion = false
	override fun resetState() {
		checkedOutput = false
		isBlockToPointsConversion = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "e9afcf9a") {
			println("here now")
		}

		// conditions for this Task - blocks on input, points on output
		if (!checkedOutput) {
			if (!inputPointList.isEmpty()) {
				return emptyList()
			}
			if (!outputBlockList.isEmpty()) {
				return emptyList()
			}
			checkedOutput = true
			isBlockToPointsConversion = true
		}

		if (!isBlockToPointsConversion) {
			return emptyList()
		}

		if (inputBlockList.size != 2) {
			return emptyList()
		}
		// TODO - expand from two color tiling
		val evenColor = inputBlockList[0].color
		val oddColor = inputBlockList[1].color

		val numRows = inputMatrix.size
		val numCols = inputMatrix[0].size

		val retMatrix = MutableList(numRows) { MutableList(numCols) {0} }

		var flipper = 0
		for (row in 0 until numRows) {
			for (col in 0 until numCols) {
				val index = row * numCols + col + flipper
				if (index % 2 == 0) {
					// even
					retMatrix[row][col] = evenColor
				} else {
					// odd
					retMatrix[row][col] = oddColor
				}
			}
			flipper += 1
		}
		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
