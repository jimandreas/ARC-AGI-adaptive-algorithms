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
		data class Candidates(var votes: Int, val theBlock: Block)
		val majorityWins: MutableList<Candidates> = mutableListOf()

		// relocate each block and look to see if it already has been seen

		var foundOne = false
		for (thisBlock in n.blocks) {
			val relocatedBlock = relocateToOrigin(thisBlock)

			var found = false
			for (i in 0 until majorityWins.size) {
				val scanThis = majorityWins[i]
				val votingBlock = scanThis.votes
				if (relocatedBlock.coordinates == scanThis.theBlock.coordinates) {
					var count = scanThis.votes
					count = count+1
					majorityWins[i].votes = count
					found = true
				}
			}
			if (!found) {
				majorityWins.add(Candidates(1, relocatedBlock))
				foundOne = true
			}
		}

		val maxVotesCandidate = majorityWins.maxByOrNull { it.votes }
		if (!foundOne  || maxVotesCandidate == null) {
			return emptyList()
		}

		val blockCoordinates = maxVotesCandidate.theBlock.coordinates
		val minRow = blockCoordinates.minOf { it.first }
		val maxRow = blockCoordinates.maxOf { it.first }
		val minCol = blockCoordinates.minOf { it.second }
		val maxCol = blockCoordinates.maxOf { it.second }

		val retMatrix = recreateMatrix(
			maxRow+1,
			maxCol+1,
			listOf(maxVotesCandidate.theBlock), emptyList())
		return retMatrix

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
