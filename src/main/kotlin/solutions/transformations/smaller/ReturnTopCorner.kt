@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.getSubmatrix

// example: d10ecb37
//   return top two by two matrix from input matrix
// REVISE to return the size of the output matrix (parametric driven)

class ReturnTopCorner : BidirectionalBaseClass() {
	override val name: String = "return top corner submatrix"

	var matrixDimensionsSaved = false
	var rowCount = 0
	var colCount = 0

	override fun resetState() {
		matrixDimensionsSaved = false
	}

	override fun testTransform(): List<List<Int>> {

		if (!matrixDimensionsSaved) {
			matrixDimensionsSaved = true
			rowCount = outputMatrix.size
			colCount = outputMatrix[0].size
		}
		val retMatrix = getSubmatrix(inputMatrix, rowCount, colCount)
		return retMatrix

	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}