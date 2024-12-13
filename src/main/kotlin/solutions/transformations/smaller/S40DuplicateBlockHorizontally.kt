@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.convertBlocksToMatrix
import solutions.utilities.translateBlockBy
import solutions.utilities.translateBlocksToOrigin

// example: 28bf18c6 duplicate block horizontally
class S40DuplicateBlockHorizontally : BidirectionalBaseClass() {
	override val name: String = "duplicate block horizontally"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "28bf18c6") {
			 //println("here now")
		}

		// redo the block abstraction allowing diagonals
		val nIn = MatrixAbstractions()
		val bu = BlockUtilities()
		nIn.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			nIn,
			scanDiagonals = true,
			requireSameColor = true
		)

		// allow only one block
		if (nIn.blocks.size != 1) {
			return emptyList()
		}

		// move the one block to the origin
		val b = translateBlocksToOrigin(nIn.blocks)
		val firstBlock = b[0]
		val coordinates = b[0].coordinates
		val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first }
		val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second }

		val duplicateBlock = translateBlockBy(b[0], 0, maxCol+1) // copy to the right

		val compositeList = listOf(firstBlock, duplicateBlock)
		val retList = convertBlocksToMatrix(compositeList)

		return retList
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
