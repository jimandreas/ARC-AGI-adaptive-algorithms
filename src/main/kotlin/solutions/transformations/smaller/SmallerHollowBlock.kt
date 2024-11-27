@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass

// example: b9b7f026
//   looking for a single hollow block among filled block
// answer is the color of that block
class SmallerHollowBlock : BidirectionalBaseClass() {
	override val name: String = "SMALLER color of single hollow block"
	val bu = BlockUtilities()

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		// output must be a point
		if (outputMatrix.size != 1
			|| outputMatrix[0].size != 1
			|| inputPointList.isNotEmpty()  // no points allowed
		) {
			return emptyList()
		}

		// scan blocks looking for one that is not filled

		val notFilledBlockList:MutableList<Block> = mutableListOf()
		for (i in 0 until inputBlockList.size) {
			if (!bu.verifyRectangularBlock(inputBlockList[i].coordinates)) {
				notFilledBlockList.add(inputBlockList[i])
			}
		}

		if (notFilledBlockList.size != 1) {
			return emptyList()
		}

		// OK we have one non-filled block.  return a 1x1 matrix with its color

		val color = notFilledBlockList[0].color
		val retArray = listOf(listOf(color))
		return retArray

	}


	override fun returnTestOutput(): List<List<Int>> {
		// output must be a point
		if (outputMatrix.size != 1
			|| outputMatrix[0].size != 1
			|| inputPointList.isNotEmpty()  // no points allowed
		) {
			return emptyList()
		}

		// scan blocks looking for one that is not filled

		val notFilledBlockList:MutableList<Block> = mutableListOf()
		for (i in 0 until inputBlockList.size) {
			if (!bu.verifyRectangularBlock(inputBlockList[i].coordinates)) {
				notFilledBlockList.add(inputBlockList[i])
			}
		}

		if (notFilledBlockList.size != 1) {
			return emptyList()
		}

		// OK we have one non-filled block.  return a 1x1 matrix with its color

		val color = notFilledBlockList[0].color
		val retArray = listOf(listOf(color))
		return retArray

	}
}