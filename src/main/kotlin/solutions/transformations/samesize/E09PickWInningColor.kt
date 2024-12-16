@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.changeMatrixColor
import solutions.utilities.countColorsInMatrix

// E09 f76d97a5 pick winning color

class E09PickWInningColor : BidirectionalBaseClass() {
	override val name: String = "pick winning color"

	var checkedOutput = false
	var winningColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

//

		// figure out the winning color 
		if (!checkedOutput) {
			checkedOutput = true
			// demand same size matrices - in and out
			if (inputMatrix.size != outputMatrix.size
				|| inputMatrix[0].size != outputMatrix[0].size) {
				return emptyList()
			}
			// pick the winning color
			//   for this task it is actually the color on the input side!
			if (outputBlockList.isEmpty()) {
				if (outputPointList.isEmpty()) {
					return emptyList()
				} else {
					val coord = outputPointList[0].coordinate
					winningColor = inputMatrix[coord.first][coord.second]
				}
			} else {
				val coord = outputBlockList[0].coordinates.first()
				winningColor = inputMatrix[coord.first][coord.second]
			}
		}

		val theMap = countColorsInMatrix(inputMatrix)
		if (theMap.size != 2) {
			return emptyList()
		}
		var losingColor = theMap[0]
		if (losingColor == winningColor) {
			losingColor = theMap[1]
		}
		// in actually the "losing color" is saved by
		//  coloring the former "winning color" in the output matrix with it!
		// The losing color disappears from the input matrix...
		val retMatrix = changeMatrixColor(
			winningColor, losingColor, inputMatrix, supressOtherColors = true)
		return retMatrix
	}

	/**
	same as testTransform
	 */
	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}