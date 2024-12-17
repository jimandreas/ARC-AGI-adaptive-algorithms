@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
	"AssignmentInCondition"
)

package solutions.transformations.smaller

import entities.findAllIsolatedThings
import entities.findIsolatedThing
import entities.validateThingWithBlocksAndPoints
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.invertMatrixWithColorsSpecified

// example: b94a9452 find block plus inversion

class S38FindBlockPlusInversion : BidirectionalBaseClass() {
	override val name: String = "find block plus inversion"

	var checkedOutput = false
	var color1 = 0
	var color2 = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {



		// demand two blocks or one block and one point

		if (inputBlockList.size == 2) {
			if (inputPointList.isNotEmpty()) {
				return emptyList()
			}
			color1 = inputBlockList[0].color
			color2 = inputBlockList[1].color
		} else {
			if (inputBlockList.size == 1) {
				if (inputPointList.size != 1) {
					return emptyList()
				}
				color1 = inputBlockList[0].color
				color2 = inputPointList[0].color
			}
		}

		// need ONE isolated thing
		val aThing = findIsolatedThing(inputMatrix)
		val isolatedThings = findAllIsolatedThings(inputMatrix)
		if (isolatedThings.size != 1) {
			return emptyList()
		}

		// make sure this thing has our values
		val validatedMatrix = validateThingWithBlocksAndPoints(
			inputMatrix, isolatedThings[0], inputBlockList, inputPointList
		)

		// swap the colors
		val retMatrix = invertMatrixWithColorsSpecified(
			validatedMatrix, color1, color2)
		return retMatrix
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}

}
