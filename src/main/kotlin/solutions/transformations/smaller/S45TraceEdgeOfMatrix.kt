@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin

// example: S45 6f8cd79b trace edge of matrix with output color

class S45TraceEdgeOfMatrix : BidirectionalBaseClass() {
	override val name: String = "trace edge of matrix with output color"

	var checkedOutput = false
	var outputColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "6f8cd79b") {
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

		val rowCount = inputMatrix.size
		val colCount = inputMatrix[0].size
		val retArray: MutableList<List<Int>> = mutableListOf()

		// create a matching array with the outline of the outputColor

		for (row in 0 until rowCount) {
			val rowList: MutableList<Int> = mutableListOf()
			for (col in 0 until colCount) {
				if (row == 0 || row == rowCount - 1) {
					rowList.add(outputColor)
				} else if (col == 0 || col == colCount - 1) {
					rowList.add(outputColor)
				} else {
					rowList.add(0)
				}
			}
			retArray.add(rowList)
		}
		return retArray

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
