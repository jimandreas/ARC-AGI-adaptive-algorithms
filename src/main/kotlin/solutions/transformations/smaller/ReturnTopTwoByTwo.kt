@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass

// example: d10ecb37
//   return top two by two matrix from input matrix
// REVISE to return the size of the output matrix (parametric driven)

class ReturnTopTwoByTwo : BidirectionalBaseClass() {
	override val name: String = "return top 2x2"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		if (inputMatrix.size < 2 || inputMatrix[0].size < 2) {
			return emptyList()
		}
		val retArray = listOf(listOf(inputMatrix[0][0], inputMatrix[0][1]),
			listOf(inputMatrix[1][0], inputMatrix[1][1]))
		return retArray

	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}