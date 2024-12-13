@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass

// example: 2dee498d repeated matrix patterns
//   SEE also : 7b7f7511 for a vertical repeating pattern

class S25RepeatedMatrixPatterns : BidirectionalBaseClass() {
	override val name: String = "repeated matrix patterns"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "7b7f7511") {
			// // println("here now")
		}

		val tryColumn = findRepeatingColumnPattern(inputMatrix)
		if (tryColumn.isNotEmpty()) {
			return tryColumn
		}
		val tryRow = findRepeatingRowPattern(inputMatrix)
		return tryRow

		return emptyList()

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

	/**
	I have a matrix of Ints in Kotlin in the form List<List<Int>>.   Please create 
	a function that scans the matrix in column fashion from left to right.  The 
	function should look for a repeating pattern of matrix cells where the pattern
	spans the full column and the width of the pattern should be no less then 
	the number of rows in the matrix.   The pattern should repeat until the right
	edge of the matrix cleanly - that is a full pattern should be present at the
	right side of the matrix.   Please return this pattern matrix as a List<List<Int>.

	Gemini code follows:
	 */
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

	/**
	This function is very similar to the findRepeatingColumnPattern function,
	with the main difference being that it scans the matrix row by row instead
	of column by column. Here's a breakdown of the changes:

	Check for Possible Pattern:

	if (rows <= cols) return emptyList(): Checks if the number of rows is less
	than or equal to the number of columns. If so, a repeating pattern spanning the
	full row is not possible.
	Iterate through Potential Pattern Heights:

	for (patternHeight in cols..rows / 2): Iterates through possible pattern
	heights, from the number of columns (minimum height) up to half the number of rows (maximum height).
	Extract Potential Pattern:

	matrix.subList(0, patternHeight): Extracts the first patternHeight rows as the potential pattern.
	Check for Repetition:

	for (row in patternHeight until rows step patternHeight): Iterates through the
	remaining rows in steps of patternHeight.
	matrix.subList(row, row + patternHeight): Extracts the current row segment.
	if (currentRowSegment != pattern): Compares the current row segment with the extracted pattern.
	Check for Clean Repetition:

	if (isRepeating && (rows % patternHeight == 0)): Checks if the pattern repeats
	cleanly until the end of the matrix (rows % patternHeight == 0).
	 */
	fun findRepeatingRowPattern(matrix: List<List<Int>>): List<List<Int>> {
		val rows = matrix.size
		val cols = matrix[0].size

		if (rows <= cols) return emptyList() // No pattern possible

		for (patternHeight in cols..rows / 2) { // Pattern height cannot be more than half the matrix height
			val pattern = matrix.subList(0, patternHeight) // Extract potential pattern

			var isRepeating = true
			for (row in patternHeight until rows step patternHeight) {
				if (row + patternHeight > rows) {
					return emptyList()
				}
				val currentRowSegment = matrix.subList(row, row + patternHeight)
				if (currentRowSegment != pattern) {
					isRepeating = false
					break
				}
			}

			if (isRepeating && (rows % patternHeight == 0)) { // Check if pattern repeats cleanly
				return pattern
			}
		}

		return emptyList() // No repeating pattern found
	}
}
