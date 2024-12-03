@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass

// example: 2dee498d repeated matrix patterns

class S25RepeatedMatrixPatterns : BidirectionalBaseClass() {
	override val name: String = "**** repeated matrix patterns"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "2dee498d") {
			println("here now")
		}

		return findRepeatingColumnPattern(inputMatrix)

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

	fun findRepeatingColumnPattern(matrix: List<List<Int>>): List<List<Int>> {
		val rowCount = matrix.size
		val colCount = matrix[0].size

		if (colCount <= rowCount) return emptyList() // No pattern possible

		for (patternWidth in rowCount..colCount / 2) { // Pattern width cannot be more than half the matrix width
			val pattern = matrix.map { it.subList(0, patternWidth) } // Extract potential pattern

			var isRepeating = true
			for (col in patternWidth until colCount step patternWidth) {
				if (col + patternWidth > colCount) {
					return emptyList()
				}
				val currentColumn = matrix.map { it.subList(col, col + patternWidth) }
				if (currentColumn != pattern) {
					val mirroredColumn = currentColumn.map { row -> row.reversed() }
					if (mirroredColumn != pattern) {
						isRepeating = false
						break
					}
				}
			}

			if (isRepeating && (colCount % patternWidth == 0)) { // Check if pattern repeats cleanly
				return pattern
			}
		}

		return emptyList() // No repeating pattern found
	}

}
