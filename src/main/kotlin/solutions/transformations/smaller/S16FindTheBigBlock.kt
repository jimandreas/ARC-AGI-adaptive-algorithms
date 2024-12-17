@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: 1f85a75f find the big block

class S16FindTheBigBlock : BidirectionalBaseClass() {
	override val name: String = "find the big block"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (inputBlockList.size == 0) {
			return emptyList()
		}
		var maxCoord = -1
		var winningBlock = inputBlockList[0]
		for (b in inputBlockList) {
			if (b.coordinates.size > maxCoord) {
				winningBlock = b
				maxCoord = winningBlock.coordinates.size
			}
		}
		val relocatedBlock = relocateToOrigin(winningBlock)

		val blockCoordinates = relocatedBlock.coordinates
		val minRow = blockCoordinates.minOf { it.first }
		val maxRow = blockCoordinates.maxOf { it.first }
		val minCol = blockCoordinates.minOf { it.second }
		val maxCol = blockCoordinates.maxOf { it.second }

		val retMatrix = recreateMatrix(
			maxRow + 1,
			maxCol + 1,
			listOf(relocatedBlock), emptyList()
		)
		return retMatrix

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
