@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: 23b5c85d smallest block wins


class S32SmallestBlockWins : BidirectionalBaseClass() {
	override val name: String = "smallest block wins"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "23b5c85d") {
			//println("here now")
		}

		if (inputBlockList.isEmpty()) {
			return emptyList()
		}
		val sortedBlocks = inputBlockList.sortedBy { it.coordinates.size }
		val winningBlock = sortedBlocks[0]

		val basedBlock = relocateToOrigin(winningBlock)
		val coordinates = basedBlock.coordinates
		val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first }
		val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second }

		val retArray = recreateMatrix(maxRow+1, maxCol+1, listOf(basedBlock), emptyList())
		return retArray

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
