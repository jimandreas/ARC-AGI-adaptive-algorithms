@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findEnclosedPoint

// example: d9fac9be
//   looking for a single hollow block among filled block
// answer is the color of that block
class SmallerBlockEnclosingPoint : BidirectionalBaseClass() {
	override val name: String = "SMALLER block enclosing a point"
	val bu = BlockUtilities()

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		val notSmallBlockList: MutableList<Block> = mutableListOf()
		for (i in 0 until inputBlockList.size) {
			if (inputBlockList[i].coordinates.size < 8) {
				continue
			}
			notSmallBlockList.add(inputBlockList[i])
		}

		if (notSmallBlockList.size != 1) {
			return emptyList()
		}

		// OK we have one not small block.  Verify that it is at least 3x3 block
		// with a hollow center suggesting it is enclosing a point (8 cells)

		val theBlock = notSmallBlockList[0]
		if (theBlock.coordinates.size < 8) {
			return emptyList()
		}

		val coord = findEnclosedPoint(theBlock, inputMatrix)
		if (coord == null) {
			return emptyList()
		}
		val color = inputMatrix[coord.first][coord.second]
		val retArray = listOf(listOf(color))
		return retArray

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}