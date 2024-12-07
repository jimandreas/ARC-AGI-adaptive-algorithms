@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import Block
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin
import solutions.utilities.translateBlockBy

// example: bc1d5164 block compositing

class S34BlockCompositing : BidirectionalBaseClass() {
	override val name: String = "block compositing"

	var checkedOutput = false
	var numRowOutput = 0
	var numColOutput = 0

	override fun resetState() {
		checkedOutput = false
	}

	// translate all blocks based on their initial position to
	//  composite them
	override fun testTransform(): List<List<Int>> {

		if (taskName == "bc1d5164") {
			println("here now")
		}
		if (!checkedOutput) {
			if (outputMatrix.size != 3 && outputMatrix[0].size != 3) {
				return emptyList()
			}
			numRowOutput = 3
			numColOutput = 3
			checkedOutput = true
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

		val numRow = inputMatrix.size
		val numCol = inputMatrix[0].size
		val bList: MutableList<Block> = mutableListOf()
		// figure out which corner each block is located in
		for (b in nIn.blocks) {
			var rowOffset = 0
			var colOffset = 0
			val coordinates = b.coordinates
			val minRow = coordinates.minOf { it.first }
			val maxRow = coordinates.maxOf { it.first }
			val minCol = coordinates.minOf { it.second }
			val maxCol = coordinates.maxOf { it.second }

			// for block at origin offset is 0, 0 already. Handle other cases

			if ((minRow < 2) && (maxCol > numCol - 2)) {
				colOffset = 1
			} else if ((maxRow > numRow - 2) && (maxCol > numCol - 2)) {
				rowOffset = 1 // bottom right corner
				colOffset = 1
			} else if ((maxRow > numRow - 2) && (minCol < 2)) {
				rowOffset = 1
			}
			val atOrigin = relocateToOrigin(b)
			val translated = translateBlockBy(atOrigin, rowOffset, colOffset)
			bList.add(translated)
		}

		val retArray = recreateMatrix(
			numRowOutput, numColOutput,
			bList, emptyList())

		return retArray

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}
