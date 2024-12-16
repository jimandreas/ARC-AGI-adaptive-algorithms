@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findSwappedColors
import solutions.utilities.recreateMatrix

// example: d511f180 color swap

class E03ColorSwap : BidirectionalBaseClass() {
	override val name: String = "color swap analysis"

	var checkedOutput = false

	var color1 = 0
	var color2 = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {



		val colorPairList = findSwappedColors(inputMatrix, outputMatrix)
		if (colorPairList.isEmpty()) {
			return emptyList()
		}
		val colorPair = colorPairList[0]

		if (!checkedOutput) {
			color1 = colorPair.first
			color2 = colorPair.second
		}

		val testColor1 = colorPair.first
		val testColor2 = colorPair.second

		if (testColor1 != color1 && testColor1 != color2) {
			return emptyList()
		}
		if (testColor2 != color1 && testColor2 != color2) {
			return emptyList()
		}

		return outputMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		// swap color1 with color2 in the input matrix

		val rowCount = inputMatrix.size
		val colCount = inputMatrix[0].size

		val retList : MutableList<MutableList<Int>> = mutableListOf()

		for (row in 0 until rowCount) {
			val retRow : MutableList<Int> = mutableListOf()
			for (col in 0 until colCount) {
				val cell = inputMatrix[row][col]

				if (cell == color1) {
					retRow.add(color2)
				} else if (cell == color2) {
					retRow.add(color1)
				} else {
					retRow.add(cell)
				}
			}
			retList.add(retRow)
		}
		return retList
	}



}