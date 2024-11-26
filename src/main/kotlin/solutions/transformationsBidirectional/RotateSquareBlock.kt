@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import solutions.utilities.rotateMatrix

// example:  ed36ccf7

class RotateSquareBlock : BidirectionalBaseClass() {
	override val name: String = "******** rotation of the matrix *******"
	var rotationSet = false
	var rotation = 0

	override fun resetState() {
		rotationSet = false
	}

	/*
	try rotating the matrix 90 degrees clock- or counter-clockwise
	 */
	override fun testTransform(): List<List<Int>> {

		if (!rotationSet) {
			val rotateClockwise = rotateMatrix(inputMatrix, 0)
			if (rotateClockwise == outputMatrix) {
				rotationSet = true
				rotation = 0
				return rotateClockwise
			} else {
				val rotateCounterClockwise = rotateMatrix(inputMatrix, 1)
				if (rotateCounterClockwise == outputMatrix) {
					rotationSet = true
					rotation = 1
					return rotateCounterClockwise
				}
			}
		}
		return rotateMatrix(inputMatrix, rotation)
	}

	/*
	 rotate the matrix as determined earlier
	 */
	override fun returnTestOutput(): List<List<Int>> {

		return rotateMatrix(inputMatrix, rotation)
	}


}