@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: S46 28e73c20 spiral

class S46Spiral : BidirectionalBaseClass() {
	override val name: String = "spiral"

	var checkedOutput = false
	var outputColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "28e73c20") {
			println("here now")
		}

		// there is NOTHING in the input
		if (inputBlockList.isNotEmpty() || inputPointList.isNotEmpty()) {
			return emptyList()
		}
		if (!checkedOutput) {
			checkedOutput = true
			outputColor = outputMatrix[0][0]
			if (outputColor == 0) {
				return emptyList()
			}
		}

		// TBD - spiral

		return emptyList()

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
