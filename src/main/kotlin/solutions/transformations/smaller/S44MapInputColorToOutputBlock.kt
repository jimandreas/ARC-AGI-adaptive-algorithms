@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.compareMatrices
import solutions.utilities.countColorsInMatrix

// example:S44 d4469b4b map input color to output block

class S44MapInputColorToOutputBlock : BidirectionalBaseClass() {
	override val name: String = "map input color to output block"

	var checkedOutput = false
	var mapColorToOutputMatrix : MutableMap<Int, List<List<Int>>> = mutableMapOf()
	var outputRows = 0
	var outputCols = 0

	override fun resetState() {
		checkedOutput = false
		mapColorToOutputMatrix = mutableMapOf()
	}

	override fun testTransform(): List<List<Int>> {



		val colorList = countColorsInMatrix(inputMatrix)
		if (colorList.size != 1) {
			return emptyList()
		}
		val theColor = colorList[0]

		// for this task - there is one output block

		if (outputBlockList.size != 1) {
			return emptyList()
		}

		// error check the algorithm - it should be consistent
		//   in the example data
		if (mapColorToOutputMatrix.keys.contains(theColor)) {
			val hashedArray = mapColorToOutputMatrix[theColor]
			if (hashedArray == null) {
				return emptyList()
			}
			if (!compareMatrices(hashedArray, outputMatrix)) {
				return emptyList()
			}
		} else {
			mapColorToOutputMatrix[theColor] = outputMatrix
		}

		return outputMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		val colorList = countColorsInMatrix(inputMatrix)
		if (colorList.size != 1) {
			return emptyList()
		}
		val theColor = colorList[0]

		val retMatrix = mapColorToOutputMatrix[theColor]
		if (retMatrix == null) {
			return emptyList()
		}

		return retMatrix
	}


}
