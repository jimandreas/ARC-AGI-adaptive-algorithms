@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import kotlin.collections.iterator

// example: 5582e5ca

class BidiColorByMajority : BidirectionalBaseClass() {
	override val name: String = "BIDI color by majority"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		// have to determine the majority color
		val colorCounts = mutableMapOf<Int, Int>()

		// Count colors in blocks
		for (block in inputBlockList) {
			val area = block.coordinates.size
			colorCounts[block.color] = colorCounts.getOrDefault(block.color, 0) + area
		}

		// Count colors in points
		for (point in inputPointList) {
			colorCounts[point.color] = colorCounts.getOrDefault(point.color, 0) + 1
		}

		// Find the color with the maximum count
		var majorityColor = -1
		var maxCount = 0
		for ((color, count) in colorCounts) {
			if (count > maxCount) {
				majorityColor = color
				maxCount = count
			}
		}

		val rows = inputMatrix.size
		val cols = inputMatrix[0].size
		val retMatrix = List(rows) {
			List(cols) { majorityColor }
		}
		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		// have to determine the majority color
		val colorCounts = mutableMapOf<Int, Int>()

		// Count colors in blocks
		for (block in inputBlockList) {
			val area = block.coordinates.size
			colorCounts[block.color] = colorCounts.getOrDefault(block.color, 0) + area
		}

		// Count colors in points
		for (point in inputPointList) {
			colorCounts[point.color] = colorCounts.getOrDefault(point.color, 0) + 1
		}

		// Find the color with the maximum count
		var majorityColor = -1
		var maxCount = 0
		for ((color, count) in colorCounts) {
			if (count > maxCount) {
				majorityColor = color
				maxCount = count
			}
		}

		val rows = inputMatrix.size
		val cols = inputMatrix[0].size
		val retMatrix = List(rows) {
			List(cols) { majorityColor }
		}
		return retMatrix
	}
}