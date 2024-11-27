@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Block
import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findMatchingEntryIndex

// example: 27a28665
//    basically accumulate the examples as the patterns.
//    then match the test input mattern and return the associate result.

class BidiPatternMatching : BidirectionalBaseClass() {
	override val name: String = "BIDI pattern matching"

	val blocksAndPoints: MutableList<Pair<List<Block>, List<Point>>> = mutableListOf()
	val colorList: MutableList<Int> = mutableListOf()

	override fun resetState() {
		blocksAndPoints.clear()
		colorList.clear()
	}

	/*
	 * just accumulate pairs for searching at test time
	 */
	override fun testTransform(): List<List<Int>> {

		blocksAndPoints.add(Pair(inputBlockList, inputPointList))
		var currentColor = 0
		if (outputBlockList.isNotEmpty()) {
			currentColor = outputBlockList[0].color
		} else {
			if (outputPointList.size == 0) {
				return emptyList()
			}
			currentColor = outputPointList[0].color
		}
		colorList.add(currentColor)

		return outputMatrix
	}

	/**
	look up the given block/point combination - if a match,
	 then make a 1x1 matrix and color it with the associated color
	 */
	override fun returnTestOutput(): List<List<Int>> {

		val theIndex = findMatchingEntryIndex(
			inputBlockList,
			inputPointList,
			blocksAndPoints)

		if (theIndex == null || theIndex > colorList.size - 1) {
			return emptyList()
		}
		val outputColor = colorList[theIndex]
		val outputMatrix = listOf(listOf(outputColor))
		return outputMatrix
	}
}