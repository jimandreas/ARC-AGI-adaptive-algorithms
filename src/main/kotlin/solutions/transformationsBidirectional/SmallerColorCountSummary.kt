@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import Block
import entities.BlockUtilities
import solutions.utilities.aggregateColorQuantities
import solutions.utilities.findEnclosedPoint

// example: f8ff0b80
//   return a stacked color set based on quantity

class SmallerColorCountSummary : BidirectionalBaseClass() {
	override val name: String = "**** SMALLER color count summary ******"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		val colorSummary = aggregateColorQuantities(inputBlockList, inputPointList)

		val outputMatrix: MutableList<List<Int>> = mutableListOf()
		for (key in colorSummary.keys) {
			outputMatrix.add(listOf(key))
		}
		return outputMatrix

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}