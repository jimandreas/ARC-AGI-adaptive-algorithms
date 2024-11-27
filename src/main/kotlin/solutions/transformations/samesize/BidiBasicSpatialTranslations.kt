@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.getOrientation
import solutions.utilities.recreateMatrix

// make sure this Test still works
// TxBIDI BIDI Translate blocks by (1, 0) for 25ff71a9 - VERIFIED!!

// example 5168d44c - include the points (but don't translate them)
//   somehow have to capture directionality context for this one!!

class BidiBasicSpatialTranslations : BidirectionalBaseClass() {
	override var name: String = "BIDI Translate blocks spatial by "
	var originalName = name

	//var solutionFound = false

	// revise delta state to save the test delta based on point orientation

	var rHorizontalDelta = -1
	var cHorizontalDelta = -1

	var rVerticalDelta = -1
	var cVerticalDelta = -1

	var rNullDelta = -1
	var cNullDelta = -1

	override fun resetState() {
		//solutionFound = false
	}

	override fun testTransform(): List<List<Int>> {
		// try translations of 1 or 2 or 3 in every direction
		for (rd in 0..3) {
			for (cd in 0..3) {
				if ((rd == 0) && (cd == 0)) {
					continue
				}
				val tblocks = translateBlocksBy(inputBlockList, rd, cd)
				if (tblocks == outputBlockList) {
					// solutionFound = true
					// categorize the saved state by
					//   point orientation, if any
					val orientation = getOrientation(inputPointList)
					when (orientation) {
						'V' -> {
							rVerticalDelta = rd
							cVerticalDelta = cd
						}
						'H' -> {
							rHorizontalDelta = rd
							cHorizontalDelta = cd
						}
						else -> {
							rNullDelta = rd
							cNullDelta = cd
						}
					}

					val outputMatrix = recreateMatrix(
						outputMatrix.size,
						outputMatrix[0].size,
						tblocks,
						inputPointList
					)
					return outputMatrix
				}
			}
		}
		// no solution
		return emptyList()
	}


override fun returnTestOutput(): List<List<Int>> {

	// translate the blocks based on the orientation of the blocks, if any

	val myName = taskName // for debugging
	var rDelta = 0
	var cDelta = 0
	val orientation = getOrientation(inputPointList)
	when (orientation) {
		'V' -> {
			rDelta= rVerticalDelta
			cDelta =cVerticalDelta
		}
		'H' -> {
			rDelta =rHorizontalDelta
			cDelta = cHorizontalDelta
		}
		else -> {
			rDelta = rNullDelta
			cDelta = cNullDelta
		}
	}

	val tblocks = translateBlocksBy(inputBlockList, rDelta, cDelta)
	val outputMatrix = recreateMatrix(
		outputMatrix.size,
		outputMatrix[0].size,
		tblocks,
		inputPointList
	)

	name = "$originalName($rDelta, $cDelta)"
	return outputMatrix
}


fun translateBlocksBy(blocks: List<Block>, rowDelta: Int, colDelta: Int): List<Block> {
	val transformedBlocks = blocks.map { block ->
		block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row + rowDelta, col + colDelta) }
			.toSet())
	}
	return transformedBlocks
}


}