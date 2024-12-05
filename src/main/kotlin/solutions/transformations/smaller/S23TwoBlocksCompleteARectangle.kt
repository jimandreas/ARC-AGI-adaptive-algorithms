@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.translateBlocksToOrigin

// example: 681b3aeb two blocks translate to complete a rectangle
//  TODO: this runs really slow in the debugger but is OK in real time - could use optimization

class S23TwoBlocksCompleteARectangle : BidirectionalBaseClass() {
	override val name: String = "**** two blocks translate to complete a rectangle"

	var checkedOutput = false
	var isTwoBlocksToRectangle = false
	override fun resetState() {
		checkedOutput = false
		isTwoBlocksToRectangle = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "681b3aeb") {
			// println("here now")
		}

		// conditions for this Task - two blocks on input and output, no points
		if (!checkedOutput) {
			if (!inputPointList.isEmpty()) {
				return emptyList()
			}
			if (!outputPointList.isEmpty()) {
				return emptyList()
			}
			if (inputBlockList.size != 2 || outputBlockList.size != 2) {
				return emptyList()
			}

			// another condition - since this is a merge task - is that the output
			//   matrix size is the same as the combined coordinate data of the two
			// input blocks

			val outputMatrixCellCount = outputMatrix.size * outputMatrix[0].size
			val inputBlockCoordinateCount = inputBlockList.flatMap { it.coordinates }.count()

			if (outputMatrixCellCount != inputBlockCoordinateCount) {
				return emptyList()
			}
			checkedOutput = true
			isTwoBlocksToRectangle = true
		}

		if (!isTwoBlocksToRectangle) {
			return emptyList()
		}

		val bestFit = findSmallestRectanglePlacement(inputBlockList[0], inputBlockList[1])
		val slideToOrigin = translateBlocksToOrigin(bestFit)

		val coords = slideToOrigin.flatMap { it.coordinates }
		val minRow = coords.minOf { it.first }
		val maxRow = coords.maxOf { it.first }
		val minCol = coords.minOf { it.second }
		val maxCol = coords.maxOf { it.second }

		val retMatrix = recreateMatrix(maxRow + 1, maxCol +1, slideToOrigin, emptyList())
		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

	fun findSmallestRectanglePlacement(block1: Block, block2: Block): List<Block> {
		// Helper function to translate a block by given offsets
		fun translateBlock(block: Block, rowOffset: Int, colOffset: Int): Block {
			val translatedCoordinates = block.coordinates.map { (row, col) ->
				Pair(row + rowOffset, col + colOffset)
			}.toSet()
			return Block(block.color, translatedCoordinates)
		}

		var minArea = Int.MAX_VALUE
		var bestPlacement = listOf<Block>()

		// Iterate through possible translations of block1
		val numRows = inputMatrix.size
		val numCols = inputMatrix[0].size
		val rowRange = -numRows..numRows
		val colRange = -numCols..numCols

		for (rowOffset1 in rowRange) { // Adjust the range (-10..10) if needed
			for (colOffset1 in colRange) {
				val translatedBlock1 = translateBlock(block1, rowOffset1, colOffset1)

				// Iterate through possible translations of block2
				for (rowOffset2 in rowRange) {
					for (colOffset2 in colRange) {
						val translatedBlock2 = translateBlock(block2, rowOffset2, colOffset2)

						// Check for overlaps
						if (translatedBlock1.coordinates.intersect(translatedBlock2.coordinates).isNotEmpty()) {
							continue
						}

						// Calculate bounding box
						val allCoordinates = translatedBlock1.coordinates + translatedBlock2.coordinates
						val minRow = allCoordinates.minOf { it.first }
						val maxRow = allCoordinates.maxOf { it.first }
						val minCol = allCoordinates.minOf { it.second }
						val maxCol = allCoordinates.maxOf { it.second }

						// Calculate area and update best placement
						val area = (maxRow - minRow + 1) * (maxCol - minCol + 1)
						if (area < minArea) {
							minArea = area
							bestPlacement = listOf(translatedBlock1, translatedBlock2)
						}
					}
				}
			}
		}

		return bestPlacement
	}
}
