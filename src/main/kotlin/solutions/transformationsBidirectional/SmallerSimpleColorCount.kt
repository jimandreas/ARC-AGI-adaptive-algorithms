@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import solutions.utilities.countCoordinatesWithSingleColor
import solutions.utilities.uniqueColors

// example: d631b094
//   return count of colors as List<List<Int>> of the single color

class SmallerSimpleColorCount : BidirectionalBaseClass() {
	override val name: String = "SMALLER simple color count"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {

		// just one color for this Task

		val uniqueColorCount = uniqueColors(inputBlockList, inputPointList)
		if (uniqueColorCount != 1) {
			return emptyList()
		}

		val colorAndCount = countCoordinatesWithSingleColor(inputBlockList, inputPointList)
		val color = colorAndCount.first
		val count = colorAndCount.second
		val retList = List(count) { color }
		return listOf(retList)
	}

//	val colorCount = inputBlockList.return outputMatrix


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}