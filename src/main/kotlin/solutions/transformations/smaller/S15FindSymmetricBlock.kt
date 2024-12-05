@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.hasHorizontalSymmetry
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: 72ca375d find symmetric block

class S15FindSymmetricBlock : BidirectionalBaseClass() {
	override val name: String = "find symmetric block"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		// redo the block abstraction allowing diagonals
		val n = MatrixAbstractions()
		val bu = BlockUtilities()
		n.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			n,
			scanDiagonals = true,
			requireSameColor = true
		)

		for (b in n.blocks) {
			val relocatedBlock = relocateToOrigin(b)
			if (hasHorizontalSymmetry(relocatedBlock.coordinates)) {
				val blockCoordinates = relocatedBlock.coordinates
				val minRow = blockCoordinates.minOf { it.first }
				val maxRow = blockCoordinates.maxOf { it.first }
				val minCol = blockCoordinates.minOf { it.second }
				val maxCol = blockCoordinates.maxOf { it.second }

				val retMatrix = recreateMatrix(
					maxRow+1,
					maxCol+1,
					listOf(relocatedBlock), emptyList())
				return retMatrix
			}
		}

		return emptyList()
//		val b = findSymmetricBlock(n.blocks)
//		if (b == null) {
//			return emptyList()
//		}


	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
