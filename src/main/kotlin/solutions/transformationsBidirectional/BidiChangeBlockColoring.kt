@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import solutions.utilities.changeBlockColor
import solutions.utilities.recreateMatrix

// example:  67385a82

class BidiChangeBlockColoring : BidirectionalBaseClass() {
	override val name: String = "BIDI change block coloring"
	var outputColorSet = false
	var outputColor = -1

	override fun resetState() {
		outputColorSet = false
	}
	override fun testTransform(): List<List<Int>> {

		if (inputBlockList.isEmpty() || outputBlockList.isEmpty()) {
			return emptyList()
		}
		val firstOutputBlock = outputBlockList[0]
		val thisExampleOutputColor = firstOutputBlock.color
		if (!outputColorSet) {
			outputColor = thisExampleOutputColor
		} else if (outputColor != thisExampleOutputColor) {
			// output color is not consistent.  Fail
			return emptyList()
		}

		val changeBlocks = changeBlockColor(inputBlockList, outputColor)
		val outputMatrix = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			changeBlocks,
			inputPointList
		)
		return outputMatrix
	}

	/*
	 change the color of the input blocks to the cached color
	 and recreate the output matrix as the test answer
	 */
	override fun returnTestOutput(): List<List<Int>> {
		// note that the output matrix here is the
		//   cached output matrix from the last example!!
		if (inputBlockList.isEmpty()) {
			return emptyList()
		}

		val changeBlocks = changeBlockColor(inputBlockList, outputColor)
		val outputMatrix = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			changeBlocks,
			inputPointList
		)
		return outputMatrix

	}


}