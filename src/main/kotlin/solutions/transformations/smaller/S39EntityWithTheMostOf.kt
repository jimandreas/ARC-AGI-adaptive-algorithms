@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
	"AssignmentInCondition"
)

package solutions.transformations.smaller

import Block
import Point
import entities.*
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.invertMatrixWithColorsSpecified

// example: e50d258f entity with the most of

class S39EntityWithTheMostOf : BidirectionalBaseClass() {
	override val name: String = "entity with the most of"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "e50d258f") {
			println("Here now")
		}

		// need at least two things
		val isolatedThings = findAllIsolatedThings(inputMatrix)
		if (isolatedThings.size  < 2) {
			return emptyList()
		}
		return emptyList()
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}
