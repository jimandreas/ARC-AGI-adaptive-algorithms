@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.compareMatrices

// example: 6e02f1e3 num colors mapping

class S33NumColorsMapping : BidirectionalBaseClass() {
	override val name: String = "num colors mapping"

	var checkedOutput = false
	var mapColorToMatrix : MutableMap<Int, List<List<Int>>> = mutableMapOf()

	override fun resetState() {
		checkedOutput = false
		mapColorToMatrix = mutableMapOf()
	}

	override fun testTransform(): List<List<Int>> {



		val colors = inputMatrix.flatten().toHashSet()
		val numColors = colors.size

		// error check the algorithm - it should be consistent
		//   in the example data
		if (mapColorToMatrix.keys.contains(numColors)) {
			val hashedMatrix = mapColorToMatrix[numColors]
			if (hashedMatrix == null) {
				return emptyList()
			}
			val result = compareMatrices(outputMatrix, hashedMatrix)
			if (result == false) {
				return emptyList()
			}
		}
		mapColorToMatrix[numColors] = outputMatrix

		return outputMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		val colors = inputMatrix.toHashSet()
		val numColors = colors.size

		val retMatrix = mapColorToMatrix[numColors]
		if (retMatrix == null) {
			return emptyList()
		}

		return retMatrix
	}


}
