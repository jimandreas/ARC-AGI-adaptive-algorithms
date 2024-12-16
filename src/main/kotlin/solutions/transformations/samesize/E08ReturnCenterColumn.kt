@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass

// E08 d23f8c26 return center column

class E08ReturnCenterColumn : BidirectionalBaseClass() {
	override val name: String = "return center column"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

//

		// reject if their isn't an odd number of columns
		if (inputMatrix[0].size % 2 != 1) {
			return emptyList()
		}

		val retMatrix: MutableList<MutableList<Int>> = mutableListOf()

		val theCol = inputMatrix[0].size / 2
		for (row in 0 until inputMatrix.size) {
			val rowList : MutableList<Int> = mutableListOf()
			for (col in 0 until inputMatrix[0].size) {
				if (col == theCol) {
					rowList.add(inputMatrix[row][col])
				} else {
					rowList.add(0)
				}
			}
			retMatrix.add(rowList)
		}
		return retMatrix
	}

	/**
	same as testTransform
	 */
	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}