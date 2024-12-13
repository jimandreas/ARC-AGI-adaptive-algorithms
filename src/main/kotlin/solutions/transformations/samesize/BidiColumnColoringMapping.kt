@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix

// example: 0d3d703e
class BidiColumnColoringMapping : BidirectionalBaseClass() {
	override val name: String = "BIDI column coloring mapping"

	val accumulatedColorMapping = mutableMapOf<Int, Int>()

	/*
	 * look for vertical columns of blocks in the input and output block lists
	 */
	override fun testTransform(): List<List<Int>> {

		if (!areAllBlocksVerticalColumns(matrix = inputMatrix, inputBlockList)) {
			return emptyList()
		}

		if (!areAllBlocksVerticalColumns(matrix = outputMatrix, outputBlockList)) {
			return emptyList()
		}

		if (inputBlockList.size != outputBlockList.size) {
			return emptyList()
		}
		val thisMapping = mapBlockColors(inputBlockList, outputBlockList)
		accumulatedColorMapping.apply { putAll(thisMapping) }

		val retList = applyColorMapping(inputBlockList, accumulatedColorMapping)
		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			retList,
			emptyList()
		)
		return outputMatrix
	}

	/**
	 map the colors of the input blocks according to the
	 accumulated color mapping and then create the output
	 matrix for the test and return it.
	 */
	override fun returnTestOutput(): List<List<Int>> {
		if (!areAllBlocksVerticalColumns(matrix = inputMatrix, inputBlockList)) {
			return emptyList()
		}

		val retList = applyColorMapping(inputBlockList, accumulatedColorMapping)
		val outputMatrix = recreateMatrix(
			outputMatrix.size,
			outputMatrix[0].size,
			retList,
			emptyList()
		)
		return outputMatrix
	}

	/**
	A Block in a matrix is specified by the following Kotlin data class.
	A matrix of integers is specified by List<List<Int>>.

	data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)

	Please create a Kotlin function takes a list of Blocks and returns true
	if each Block makes a vertical column in the matrix.
	 Google Gemini code follows:
	 */

	fun areAllBlocksVerticalColumns(matrix: List<List<Int>>, blocks: List<Block>): Boolean {
		val numRows = matrix.size

		for (block in blocks) {
			val cols = block.coordinates.map { it.second }.toSet() // Get unique column numbers
			if (cols.size != 1) { // If the block spans more than one column, it's not vertical
				return false
			}
			val rows = block.coordinates.map { it.first }.toSet() // Get unique row numbers
			if (rows.size != numRows) { // If the block doesn't cover all rows, it's not a full column
				return false
			}
		}

		return true // If all blocks pass the checks, return true
	}

	/**
	Assume that two lists of Blocks have passed the
	"areAllBlocksVerticalColumns" test.   Now create a
	mapping of the colors in the blocks in the inputBlockList
	to the colors of the outputBlockList in a function.
	Two lists of Blocks - the inputBlockList and the
	outputBlockList are passed to the function.
	The association should be based on colors, not on columns,
	that is color 1 maps to color 5 based on column 0 in the
	inputBlockList compared to column 0 of the outputBlockList.
	Return this mapping.

	 Gemini code follows:
	 */
	fun mapBlockColors(inputBlockList: List<Block>, outputBlockList: List<Block>): Map<Int, Int> {
		val colorMapping = mutableMapOf<Int, Int>()

		// Assuming both lists have passed the "areAllBlocksVerticalColumns" test
		// and have the same number of blocks

		for (i in inputBlockList.indices) {
			val inputColor = inputBlockList[i].color
			val outputColor = outputBlockList[i].color
			colorMapping[inputColor] = outputColor
		}

		return colorMapping
	}

	// data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
	/**
	Now that the colorMapping has been created, please create a
	function in Kotlin that takes another List<Block> and applies
	the colorMapping to the blocks, so that the color values
	for each block in the list are changed according to the mapping.
	Leave the column and row information untouched in this mapping.
	Return the new List<Block>.
	 Gemini code follows:
	 */

	fun applyColorMapping(blocks: List<Block>, colorMapping: Map<Int, Int>): List<Block> {
		return blocks.map { block ->
			val newColor = colorMapping[block.color] ?: block.color // Use new color if found in mapping, otherwise keep original color
			block.copy(color = newColor)
		}
	}
}