@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass

// example: a740d043 smallest enclosing rectangle

class S13SmallestEnclosingRectangle : BidirectionalBaseClass() {
	override val name: String = "**** smallest enclosing rectangle"

	var checkedOutput = false
	var backgroundColor = -1
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (!checkedOutput) {
			 // set background color here
			checkedOutput = true
		}

		return emptyList()
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
