@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

import Block
import solutions.utilities.recreateMatrix

class BidiBasicTranslations : BidirectionalBaseClass() {
	override var name: String = "Translate blocks by "
	var originalName = name

	var solutionFound = false
	var rDelta = -1
	var cDelta = -1

	override fun testTransform(): List<List<Int>> {

		if (!solutionFound) {
			// try translations of 1 or 2 in every direction
			for (rd in 0..2) {
				for (cd in 0..2) {
					if ((rd == 0) && (cd == 0)) {
						continue
					}
					val tblocks = translateBlocksBy(inputBlockList, rd, cd)
					if (tblocks == outputBlockList) {
						solutionFound = true
						rDelta = rd
						cDelta = cd

						val outputMatrix = recreateMatrix(
							outputMatrix.size,
							outputMatrix[0].size,
							tblocks,
							emptyList()
						)
						return outputMatrix
					}
				}
			}
			// no solution
			return emptyList()
		}

		// already found a possible solution.   Just use it.
		val tblocks = translateBlocksBy(inputBlockList, rDelta, cDelta)
		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			tblocks,
			emptyList()
		)

		return outputMatrix
	}

	override fun returnTestOutput(): List<List<Int>> {
		// already found a possible solution.   Just use it.
		val tblocks = translateBlocksBy(inputBlockList, rDelta, cDelta)
		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			tblocks,
			emptyList()
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