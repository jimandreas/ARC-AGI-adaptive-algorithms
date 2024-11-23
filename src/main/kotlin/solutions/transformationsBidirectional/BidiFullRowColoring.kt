package solutions.transformationsBidirectional

import Block
import solutions.utilities.recreateMatrix

class BidiFullRowColoring : BidirectionalBaseClass() {
	override val name: String = "Full row blocks transform"

	var outputColorSet = false
	var outputColor = -1

	override fun testTransform(): List<List<Int>> {
		val blocks = findBlocksSpanningEntireRow(outputMatrix, inputBlockList)
		if (!blocks.isEmpty() && !outputBlockList.isEmpty()) {
			val firstOutputBlock = outputBlockList[0]
			val thisExampleOutputColor = firstOutputBlock.color
			if (!outputColorSet) {
				outputColor = thisExampleOutputColor
			} else if (outputColor != thisExampleOutputColor) {
				// output color is not consistent.  Fail
				return emptyList()
			}
			val changeBlocks = changeBlockColor(blocks, outputColor)
			val outputMatrix = recreateMatrix(
				outputMatrix.size,
				outputMatrix[0].size,
				changeBlocks,
				emptyList()
			)
			return outputMatrix
		}
		return emptyList()
	}

	override fun returnTestOutput(): List<List<Int>> {
		// note that the output matrix here is the
		//   cached output matrix from the last example!!
		val blocks = findBlocksSpanningEntireRow(outputMatrix, inputBlockList)
		if (!blocks.isEmpty()) {
			val changeBlocks = changeBlockColor(blocks, outputColor)
			val outputMatrix = recreateMatrix(
				outputMatrix.size,
				outputMatrix[0].size,
				changeBlocks,
				emptyList()
			)
			return outputMatrix
		}
		return emptyList()
	}


	/**
	A Block in a matrix is specified by the following Kotlin data class.
	A matrix of integers is specified by List<List<Int>>.

	data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)

	Please create a Kotlin function takes a list of Blocks and
	returns only those Blocks that span an entire row of the matrix.

	Google Gemini function follows:
	 */

	fun findBlocksSpanningEntireRow(matrix: List<List<Int>>, blocks: List<Block>): List<Block> {
		val numCols = matrix[0].size
		val result = mutableListOf<Block>()

		for (block in blocks) {
			val rows = block.coordinates.map { it.first }.toSet() // Get unique row numbers
			if (rows.size == 1) { // Block occupies only one row
				val rowNum = rows.first()
				val cols = block.coordinates.map { it.second }.toSet() // Get unique column numbers
				if (cols.size == numCols) { // Check if the block covers all columns in the row
					result.add(block)
				}
			}
		}
		return result
	}

	fun changeBlockColor(blocks: List<Block>, newColor: Int): List<Block> {
		return blocks.map { it.copy(color = newColor) }
	}
}