@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findUniqueBlockCoordinatesOnly
import solutions.utilities.recreateMatrix

// example: a87f7484 find unique block color don't care
//   derived from S14 - but color is now a don't care

class S31FindUniqueBlockColorDontCare : BidirectionalBaseClass() {
	override val name: String = "find unique block color don't care"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "a87f7484") {
			println("here now")
		}

		// redo the block abstraction allowing diagonals
		val n = MatrixAbstractions()
		val bu = BlockUtilities()
		n.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			n,
			scanDiagonals = true,
			requireSameColor = true
		)

		val retVal = findUniqueBlockCoordinatesOnly(n.blocks)
		if (retVal.first == 0) {
			return emptyList()
		}

		val b = retVal.second // the "ub" unique block
		val blockCoordinates = b.coordinates
		val minRow = blockCoordinates.minOf { it.first }
		val maxRow = blockCoordinates.maxOf { it.first }
		val minCol = blockCoordinates.minOf { it.second }
		val maxCol = blockCoordinates.maxOf { it.second }

		val retMatrix = recreateMatrix(maxRow+1, maxCol+1, listOf(b), emptyList())
		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
