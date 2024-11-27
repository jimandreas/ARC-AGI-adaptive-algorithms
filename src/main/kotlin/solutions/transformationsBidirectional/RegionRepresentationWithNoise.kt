@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

// example: 5614dbcf  region representation with noise
//   for an nxn output matrix look at the input matrix for
// regional color mappings with noise presence
//   input matrix must be some multiple of output matrix


class RegionRepresentationWithNoise : BidirectionalBaseClass() {
	override val name: String = "**** regional color mapping with noise"

	var matrixMutipleChecked = false
	var rowCount = 0
	var colCount = 0
	var rowMultiple = 0
	var colMultiple = 0

	override fun resetState() {
		matrixMutipleChecked = false
	}

	override fun testTransform(): List<List<Int>> {
		// do this one time
		if (!matrixMutipleChecked) {
			rowCount = outputMatrix.size
			colCount = outputMatrix[0].size

			if (inputMatrix.size % rowCount != 0) {
				return emptyList()
			}
			if (inputMatrix[0].size % colCount != 0) {
				return emptyList()
			}
			matrixMutipleChecked = true
			rowMultiple = inputMatrix.size / rowCount
			colMultiple = inputMatrix[0].size / colCount
		}

		return emptyList() // TODO finish this
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

}