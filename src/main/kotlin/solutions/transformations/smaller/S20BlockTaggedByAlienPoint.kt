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
import solutions.utilities.findMajorityColorBasedOnCoordinates
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: 48d8fb45 tagged block by alien point

class S20BlockTaggedByAlienPoint : BidirectionalBaseClass() {
	override val name: String = "the alien point is excised and block returned"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

//		if (taskName == "48d8fb45") {
//			println("here now")
//		}

		// redo the block abstraction allowing diagonals
		//  AND allowing multi-color
		val n = MatrixAbstractions()
		val bu = BlockUtilities()
		n.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			n,
			scanDiagonals = true,
			requireSameColor = false
		)

		if (n.blocks.size == 0) {
			return emptyList()
		}



		// relocate each block and scan it for an alien color

		var foundOne = false
		var alienCoordinates : Pair<Int, Int> = Pair(0,0)
		var theBlock = n.blocks[0]
		var theColor = 0

		for (thisBlock in n.blocks) {

			// exclude the alien color!!
			val baseColor = findMajorityColorBasedOnCoordinates(inputMatrix, thisBlock.coordinates)
			//val baseColor = thisBlock.color
			for (entry in thisBlock.coordinates) {
				val row = entry.first
				val col = entry.second
				if (inputMatrix[row][col] != baseColor) {
					// found an alien color!!
					foundOne = true
					theBlock = thisBlock
					alienCoordinates = Pair(row, col)
					theColor = baseColor
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

		val row = alienCoordinates.first
		val col = alienCoordinates.second
		val cleanedCoordinates = theBlock.coordinates.filterNot { it == Pair(row, col) }.toSet()
		val cleanedBlock = Block(theColor, cleanedCoordinates)

		val relocatedBlock = relocateToOrigin(cleanedBlock)

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
