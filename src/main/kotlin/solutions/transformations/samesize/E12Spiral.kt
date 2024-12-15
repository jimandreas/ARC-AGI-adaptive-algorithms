@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.SpiralDirectionChatgpt
import solutions.utilities.detectSpiral
import solutions.utilities.generateSpiral
import solutions.utilities.prettyPrintMatrixDiff

// example: S46 28e73c20 spiral

class E12Spiral : BidirectionalBaseClass() {
	override val name: String = "spiral"

	var checkedOutput = false
	var outputColor = 0
	var clockwiseSpiralDetected = false

	override fun resetState() {
		checkedOutput = false
		clockwiseSpiralDetected = false
	}

	override fun testTransform(): List<List<Int>> {

		// there is NOTHING in the input
		if (inputBlockList.isNotEmpty() || inputPointList.isNotEmpty()) {
			return emptyList()
		}

		if (inputMatrix.size != inputMatrix[0].size) {
			return emptyList() // spiral generator currently only tested for square matrices
		}
		println("(Spiral) EMPTY input: $taskName")
		if (!checkedOutput) {
			checkedOutput = true
			outputColor = outputMatrix[0][0]
			if (outputColor == 0) {
				return emptyList()
			}

			val isItClockwise = detectSpiral(outputMatrix)
			if (isItClockwise == SpiralDirectionChatgpt.CLOCKWISE) {
				clockwiseSpiralDetected = true
			}
		}

		if (!clockwiseSpiralDetected) {
			return emptyList()
		}

		val spiralMatrix = generateSpiral(
			inputMatrix.size,
			outputColor,
			clockwise = true)
		//prettyPrintMatrixDiff(spiralMatrix, outputMatrix)
		return spiralMatrix

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}