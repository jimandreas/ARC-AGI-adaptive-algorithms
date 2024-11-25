@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import solutions.utilities.changeBlockColor
import solutions.utilities.findOtherColor
import solutions.utilities.recreateMatrix
import solutions.utilities.splitBlocksVertically

// example: ce9e57f2
//   oops the top should have the majority, not the bottom!
class BidiSplitBlocksVertically : BidirectionalBaseClass() {
	override val name: String = "BIDI split blocks vertically"

	var outputColorSet = false
	var outputColor = -1

	override fun resetState() {
		outputColorSet = false
	}

	override fun testTransform(): List<List<Int>> {
		val blocksPair = splitBlocksVertically(inputBlockList)
		if (blocksPair.first.size == 0 || blocksPair.second.size == 0) {
			return emptyList()
		}
		if (outputBlockList.isEmpty()) return emptyList()

		val topBlocks = blocksPair.first
		val bottomBlocks = blocksPair.second

		// find a possible color for the bottom blocks
		if (!outputColorSet) {
			val otherColor = findOtherColor(outputBlockList, inputBlockList[0].color)
			outputColorSet = true
			outputColor = otherColor
		}

		val changeBlocks = changeBlockColor(bottomBlocks, outputColor)

		val combinedBlocks = topBlocks + changeBlocks

		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			combinedBlocks,
			emptyList()
		)
		return outputMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		val blocksPair = splitBlocksVertically(inputBlockList)
		if (blocksPair.first.size == 0 || blocksPair.second.size == 0) {
			return emptyList()
		}

		val topBlocks = blocksPair.first
		val bottomBlocks = blocksPair.second

		val changeBlocks = changeBlockColor(bottomBlocks, outputColor)

		val combinedBlocks = topBlocks + changeBlocks

		val outputMatrix = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			combinedBlocks,
			emptyList()
		)
		return outputMatrix
	}
}