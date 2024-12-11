@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.areAllBlocksHorizontalRows
import solutions.utilities.areAllBlocksVerticalColumns
import solutions.utilities.findMajorityColorInMatrix
import solutions.utilities.findMinMaxPointCoordinates
import solutions.utilities.recreateMatrix

// example: S43 8e1813be block directed lines assembly

class S43BlockDirectedLinesAssembly : BidirectionalBaseClass() {
	override val name: String = "block directed lines assembly"

	var checkedOutput = false
	var layoutVertical = false

	override fun resetState() {
		checkedOutput = false
	}

	// figure out if there is a block to direct the size
	//  of output.  No points in output allowed.

	override fun testTransform(): List<List<Int>> {

		if (taskName == "8e1813be") {
			println("here now")
		}

		if (!checkedOutput) {
			checkedOutput = true
			if (outputPointList.isNotEmpty()) {
				return emptyList()
			}
			if (taskName == "28e73c20") {
				println("here")
			}
			if (areAllBlocksVerticalColumns(outputMatrix, outputBlockList)) {
				layoutVertical = true
			} else if (!areAllBlocksHorizontalRows(outputMatrix, outputBlockList)) {
				return emptyList()
			}
			println(taskName)
		}
		return emptyList()
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
