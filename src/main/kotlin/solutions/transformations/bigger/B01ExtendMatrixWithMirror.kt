@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.bigger

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.mirrorMatrixVertically
import solutions.utilities.recreateMatrix

// example: B01 8be77c9e extend matrix with mirror
//   also: 6d0aefbc, 6fa7a44f, c9e6f938  (four solutions with one algo!)

class B01ExtendMatrixWithMirror : BidirectionalBaseClass() {
	override val name: String = "extend matrix with mirror"

	var checkedOutput = false
	var mirrorSolution = false
	var isVertical = false

	override fun resetState() {
		checkedOutput = false
		mirrorSolution = false
		isVertical = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "8be77c9e") {
			println("here now")
		}

		val inRows = inputMatrix.size
		val inCols = inputMatrix[0].size

		if (!checkedOutput) {
			checkedOutput = true
			val outRows = outputMatrix.size
			val outCols = outputMatrix[0].size

			if ((inCols == outCols) && (inRows * 2 == outRows)) {
				// could be vertical mirroring, check it.
				val vertMirror = inputMatrix.reversed()

				val retMatrix = inputMatrix + vertMirror
				if (retMatrix == outputMatrix) {
					mirrorSolution = true
					isVertical = true
					return retMatrix
				}
			}

			if ((inRows == outRows) && (inCols * 2 == outCols)) {
				// could be horizontal mirroring, check it
				val horizMirror = inputMatrix.map { row -> row.reversed() }
				// thank you Grok:  horizontal mirroring
				val retMatrix = inputMatrix.zip(horizMirror) { row1, row2 ->
					row1 + row2 }
				if (retMatrix == outputMatrix) {
					mirrorSolution = true
					return retMatrix
				}
			}
		}

		if (!mirrorSolution) {
			return emptyList()
		}

		if (isVertical) {
			val vertMirror = inputMatrix.reversed()
			val retMatrix = inputMatrix + vertMirror
			return retMatrix
		}

		val horizMirror = inputMatrix.map { row -> row.reversed() }
		// thank you Grok:  horizontal mirroring
		val retMatrix = inputMatrix.zip(horizMirror) { row1, row2 ->
			row1 + row2 }
		return retMatrix
	}

	/**
	test is same as example
	 */
	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}



}