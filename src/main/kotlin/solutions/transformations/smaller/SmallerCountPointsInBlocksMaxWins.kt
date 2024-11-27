@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.countPointsInRegion

// example: de1cd16c
// count the number of points that are contained in blocks -
//   the block with the maximum number of contained points is the winner

class SmallerCountPointsInBlocksMaxWins : BidirectionalBaseClass() {
	override val name: String = "SMALLER count points in block fields max wins"
	val bu = BlockUtilities()

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {

		if (inputBlockList.size == 0 || inputPointList.size == 0) {
			return emptyList()
		}

		var maxPoints = -1
		var winnerBlock : Block = inputBlockList[0]

		for (i in 0 until inputBlockList.size) {
			val pointsCount = countPointsInRegion(inputBlockList[i], inputPointList)
			if (pointsCount > maxPoints) {
				maxPoints = pointsCount
				winnerBlock = inputBlockList[i]
			}
		}

		if (maxPoints == -1) {
			return emptyList()
		}

		// the answer is the color of the winning block
		val color = winnerBlock.color
		val retArray = listOf(listOf(color))
		return retArray

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}