@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: 5117e062 The Alien Point Wins

class S18AlienPointWins : BidirectionalBaseClass() {
	override val name: String = "**** the block with the alien point wins"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		// redo the block abstraction allowing diagonals
		//  AND allowing multi-color
		val n = MatrixAbstractions()
		val bu = BlockUtilities()
		n.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			n,
			scanDiagonals = false,
			requireSameColor = false
		)

		if (n.blocks.size == 0) {
			return emptyList()
		}

		// relocate each block and scan it for an alien color

		var foundOne = false
		var theBlock = n.blocks[0]

		for (thisBlock in n.blocks) {

			val baseColor = thisBlock.color
			for (entry in thisBlock.coordinates) {
				val row = entry.first
				val col = entry.second
				if (inputMatrix[row][col] != baseColor) {
					// found an alien color!!
					foundOne = true
					theBlock = thisBlock
					break
				}
			}
			if (foundOne) {
				break
			}
		}

		if (!foundOne) {
			return emptyList()
		}

		val relocatedBlock = relocateToOrigin(theBlock)

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
