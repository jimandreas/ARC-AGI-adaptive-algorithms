@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.getSubmatrix
import solutions.utilities.topRightSubmatrix

// example: d10ecb37
//   And also:  5bd6f4ac for upper right block returned
//   return top two by two matrix from input matrix
// REVISE to return the size of the output matrix (parametric driven)

class S06ReturnTopCorner : BidirectionalBaseClass() {
	override val name: String = "return top corner submatrix"

	var matrixDimensionsSaved = false
	var upperLeft = false
	var rowCount = 0
	var colCount = 0

	override fun resetState() {
		matrixDimensionsSaved = false
		upperLeft = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "5bd6f4ac") {
			// // println("here now")
		}

		if (!matrixDimensionsSaved) {
			matrixDimensionsSaved = true
			rowCount = outputMatrix.size
			colCount = outputMatrix[0].size
			// check which corner!
			val retMatrix = getSubmatrix(inputMatrix, rowCount, colCount)
			if (retMatrix == outputMatrix) {
				upperLeft = true
			} //otherwise upper right, maybe
		}
		if (upperLeft) {
			val retMatrix = getSubmatrix(inputMatrix, rowCount, colCount)
			return retMatrix
		}

		val inputMatrixColCount = inputMatrix[0].size
		val retMatrix = topRightSubmatrix(inputMatrix, rowCount, colCount)
		return retMatrix


	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}