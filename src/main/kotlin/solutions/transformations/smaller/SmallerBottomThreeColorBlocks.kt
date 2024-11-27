@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.aggregateColorQuantities

// example: f8b3ba0a
//   return colors as List<List<Int>> of the bottom three(?) color block counts

class SmallerBottomThreeColorBlocks : BidirectionalBaseClass() {
	override val name: String = "SMALLER bottom block counts"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {

		if (inputPointList.isNotEmpty()) {
			return emptyList() // no points for this Task
		}

		var theColorsAndCounts = aggregateColorQuantities(inputBlockList, emptyList())
		if (theColorsAndCounts.size < 4) {
			return emptyList()  // have to have at least 4 colors
		}

		val colorList: MutableList<List<Int>> = mutableListOf()
		val listIter = theColorsAndCounts.iterator()
		val tossFirst = listIter.next()  // only the last three (?) are returned as the solution
		while (listIter.hasNext()) {
			val next = listIter.next()
			val color = next.key
			colorList.add(listOf(color))
		}

		return colorList
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}