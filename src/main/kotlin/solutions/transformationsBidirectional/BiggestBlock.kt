@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import Block
import entities.BlockUtilities
import solutions.utilities.aggregateColorQuantities
import solutions.utilities.countCoordinatesWithSingleColor
import solutions.utilities.countOccurrences
import solutions.utilities.findEnclosedPoint
import solutions.utilities.getMaxCountFromMap
import solutions.utilities.uniqueColors

// example: 445eab21
//   return biggest block as colored 2x2 matrix

class BiggestBlock : BidirectionalBaseClass() {
	override val name: String = "**** biggest block ****"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		// only blocks for this Task
		if (inputPointList.isNotEmpty()) {
			return emptyList()
		}

		// just two blocks allowed
		if (inputBlockList.size != 2) {
			return emptyList()
		}

		var biggerBlock = inputBlockList[0]
		if (inputBlockList[1].coordinates.size > inputBlockList[0].coordinates.size) {
			biggerBlock = inputBlockList[1]
		}

		val color = biggerBlock.color
		val retArray = listOf(listOf(color, color), listOf(color, color))
		return retArray

	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}