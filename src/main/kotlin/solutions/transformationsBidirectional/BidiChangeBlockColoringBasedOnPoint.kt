@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import solutions.utilities.changeBlockColor
import solutions.utilities.recreateMatrix

// example:  aabf363d

class BidiChangeBlockColoringBasedOnPoint : BidirectionalBaseClass() {
	override val name: String = "BIDI change block coloring based on point"
	var outputColorSet = false
	var outputColor = -1

	override fun resetState() {
		outputColorSet = false
	}
	override fun testTransform(): List<List<Int>> {

		if (inputBlockList.isEmpty() || outputBlockList.isEmpty()) {
			return emptyList()
		}

		// note - there are no output points in this Task
		if (inputPointList.isEmpty() || !outputPointList.isEmpty()) {
			return emptyList()
		}

		val thePointColor = inputPointList[0].color
		outputColor = thePointColor

		val changeBlocks = changeBlockColor(inputBlockList, outputColor)
		val outputMatrix = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			changeBlocks,
			emptyList() // point drops out
		)
		return outputMatrix
	}

	/*
	 change the color of the input blocks to the point color
	 and recreate the output matrix as the test answer
	 */
	override fun returnTestOutput(): List<List<Int>> {
		// note that the output matrix here is the
		//   cached output matrix from the last example!!
		if (inputBlockList.isEmpty()) {
			return emptyList()
		}

		if (inputPointList.isEmpty()) {
			return emptyList()
		}

		val changeBlocks = changeBlockColor(inputBlockList, outputColor)
		val outputMatrix = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			changeBlocks,
			emptyList() // point drops out
		)
		return outputMatrix

	}


}