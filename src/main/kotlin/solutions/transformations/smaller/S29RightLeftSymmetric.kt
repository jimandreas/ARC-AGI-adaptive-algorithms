@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.isMatrixSymmetric

// example: 44f52bb0 right left symmetric

class S29RightLeftSymmetric : BidirectionalBaseClass() {
	override val name: String = "right left symmetric"

	var checkedOutput = false
	var trueColor = 0
	var falseColor = 0
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		// results must be a single point
		if (outputMatrix.size != 1 && outputMatrix[0].size != 1) {
			return emptyList()
		}

		if (isMatrixSymmetric(inputMatrix)) {
			trueColor = outputMatrix[0][0]
			return outputMatrix
		}

		falseColor = outputMatrix[0][0]
		return outputMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		if (isMatrixSymmetric(inputMatrix)) {
			return listOf(listOf(trueColor))
		}
		return listOf(listOf(falseColor))
	}
}
