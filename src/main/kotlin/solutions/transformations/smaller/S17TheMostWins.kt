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
import solutions.utilities.hasHorizontalSymmetry
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: 39a8645d The Most Wins

class S17TheMostWins : BidirectionalBaseClass() {
	override val name: String = "the most wins"

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

		if (n.blocks.size == 0) {
			return emptyList()
		}

		// a count of how many of this Block is present
		val majorityWins: MutableList<Pair<Int, Block>> = mutableListOf()

		// relocate each block and look to see if it already has been seen

		for (thisBlock in n.blocks) {
			val relocatedBlock = relocateToOrigin(thisBlock)

			var found = false
			for (i in 0 until majorityWins.size) {
				val scanThis = majorityWins[i]
				val votingBlock = scanThis.second
				if (relocatedBlock.coordinates == votingBlock.coordinates) {
					var count = scanThis.first
					count = count+1
					majorityWins[i] = Pair(count, relocatedBlock)
					found = true
				}
			}
			if (!found) {
				majorityWins.add(Pair(1, relocatedBlock))
			}
		}
		return emptyList()
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
