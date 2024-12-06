@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.partition

import compareMatrices
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.invertMatrix
import solutions.utilities.partitionMatrix

// example 995c5fa3 pattern matching
//    match a pattern to a color key, then look up the pattern for the test

class P01PatternMatching : BidirectionalBaseClass() {
	override val name: String = "pattern matching"

	var checkedOutput = false
	var mapMatrixToColor: MutableList<Pair<List<List<Int>>, Int>> = mutableListOf()

	// cache output matrix size
	var rowCount = 0
	var colCount = 0

	override fun resetState() {
		checkedOutput = false
		mapMatrixToColor.clear()
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "995c5fa3") {
			//// println("here now")
		}

		val invertedMatrix = invertMatrix(inputMatrix)
		if (invertedMatrix.isEmpty()) {
			return emptyList()
		}
		val partitionedMatrices = partitionMatrix(invertedMatrix)
		val pCount = partitionedMatrices.size
		if (pCount == 0) {
			return emptyList()
		}
		// verify the output matrix has a row for each partition
		if (outputMatrix.size != pCount) {
			return emptyList()
		}
		rowCount = outputMatrix.size
		colCount = outputMatrix[0].size

		// add each map of matrix to color
		for (i in 0 until pCount) {
			val pMatrix = partitionedMatrices[i]
			// see if matrix is already there
			var found = false
			for (j in 0 until mapMatrixToColor.size) {
				val m = mapMatrixToColor[j].first
				if (compareMatrices(m, pMatrix)) {
					val recordedColor = mapMatrixToColor[j].second
					if (outputMatrix[i][0] != recordedColor) {
						return emptyList() // mis match in recorded context
					}
					found = true
					break
				}
			}
			if (!found) {
				mapMatrixToColor.add(Pair(pMatrix, outputMatrix[i][0]))
			}
		}
		return outputMatrix
	}

	// now match the partition matrices to get the color.

	override fun returnTestOutput(): List<List<Int>> {
//		val retMatrix = MutableList(rowCount) { MutableList(colCount) { 0 } }

		val retMatrix: MutableList<MutableList<Int>> = mutableListOf()

		val invertedMatrix = invertMatrix(inputMatrix)
		if (invertedMatrix.isEmpty()) {
			return emptyList()
		}
		val partitionedMatrices = partitionMatrix(invertedMatrix)
		val pCount = partitionedMatrices.size
		if (pCount == 0) {
			return emptyList()
		}

		for (i in 0 until pCount) {
			var outputRow: List<Int> = mutableListOf()
			val pMatrix = partitionedMatrices[i]
			// find the matrix
			var found = false
			for (j in 0 until mapMatrixToColor.size) {
				val m = mapMatrixToColor[j].first
				if (compareMatrices(m, pMatrix)) {
					val recordedColor = mapMatrixToColor[j].second
					outputRow = MutableList(colCount) { recordedColor }
					retMatrix.add(outputRow)
					found = true
					break
				}
			}
			if (!found) {
				return emptyList()
			}
		}
		return retMatrix
	}
}