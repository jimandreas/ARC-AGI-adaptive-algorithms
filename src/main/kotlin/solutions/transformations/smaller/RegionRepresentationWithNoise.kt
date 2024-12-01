@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)


package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.getMajorityValuesInRegions

// example: 5614dbcf  region representation with noise
//   for an nxn output matrix look at the input matrix for
// regional color mappings with noise presence
//   input matrix must be some multiple of output matrixpackage solutions.transformations.smaller

class RegionRepresentationWithNoise : BidirectionalBaseClass() {
	override val name: String = "regional color mapping with noise"

	var matrixMutipleChecked = false
	var rowCount = 0
	var colCount = 0
	var rowRegionCount = 0
	var colRegionCount = 0

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
			rowRegionCount = inputMatrix.size / rowCount
			colRegionCount = inputMatrix[0].size / colCount
		}

		val retList = getMajorityValuesInRegions(
			matrix = inputMatrix,
			rowRegions = rowRegionCount,
			colRegions = colRegionCount
		)

		return retList
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

}