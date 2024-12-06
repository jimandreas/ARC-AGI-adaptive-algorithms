@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix

// example: ae4f1146 max dark blue wins

class E04MaxDarkBlueWins : BidirectionalBaseClass() {
	override val name: String = "max dark blue wins"


	var checkedOutput = false


	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "ae4f1146") {
			println("here now")
		}
		return emptyList()

	}

	/**
	map the colors of the input blocks according to the
	accumulated color mapping and then create the output
	matrix for the test and return it.
	 */
	override fun returnTestOutput(): List<List<Int>> {
		return emptyList()
	}


}