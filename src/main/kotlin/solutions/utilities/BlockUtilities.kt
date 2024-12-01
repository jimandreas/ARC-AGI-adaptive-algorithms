@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.utilities

import Block
import kotlin.compareTo

/**
In Kotlin a Block data structure is given below, and there is a List<List<Block>> that forms
a pre-sorted list of Blocks.   The blocks are ordered into lines  - each List<Block> forms a
group of adajcent blocks.

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)

for a List<List<Bloc>> please create a function that works through the blocks and
makes and returns a new representation in a List<List<Int>> - where the Int is the
color of the block.
The final representation should have the same length for all List<Int> - therefore
the color for wider blocks in the list must be repeated so that all lengths in the List<Int>
are the same.   In other words, the repeating color integers in the list are proportional
to the width of the block.


 */

/*
fun convertBlocksToMatrix(blocks: List<List<Block>>): List<List<Int>> {
	if (blocks.isEmpty()) return emptyList()

	val numRows = blocks.size
	val maxCols = blocks.maxOf { row ->
		row.maxOf { block ->
			val (minCol, maxCol) = block.coordinates.fold(
				Int.MAX_VALUE to Int.MIN_VALUE
			) { (min, max), (r, c) ->
				minOf(min, c) to maxOf(max, c)
			}
			maxCol - minCol + 1  // Calculate width based on column difference
		}
	}
	val matrix = MutableList(numRows) { MutableList(maxCols) { 0 } }

	for (i in 0 until numRows) {
		var colIndex = 0
		for (block in blocks[i]) {
			val (minCol, maxCol) =
				block.coordinates.fold(Int.MAX_VALUE to Int.MIN_VALUE)
				{ (min, max), (r, c) ->
					minOf(min, c) to maxOf(max, c)
				}
			val width = maxCol - minCol + 1

			repeat(width) {
				if (colIndex == maxCols) {
					// error out something went wrong
					return emptyList()
				}
				matrix[i][colIndex++] = block.color
			}
		}
	}
	return matrix
}
*/

fun convertBlocksToMatrix(blocks: List<List<Block>>): List<List<Int>> {
	if (blocks.isEmpty()) return emptyList()

	// Determine the dimensions of the matrix
	val (numRows, maxCols) = calculateGridSize(blocks)

	// Create a matrix initialized with zeros
	val matrix = MutableList(numRows) { MutableList(maxCols) { 0 } }

	// Fill the matrix with block colors
	fillMatrix(matrix, blocks)

	return matrix
}

// Calculate the size of the grid based on blocks
private fun calculateGridSize(blocks: List<List<Block>>): Pair<Int, Int> {
	val numRows = blocks.size
	var maxCols = 0

	for (row in blocks) {
		// Calculate the total width of all blocks in this row
		val rowWidth = row.sumOf { block ->
			val (minCol, maxCol) = block.coordinates.fold(Int.MAX_VALUE to Int.MIN_VALUE) { (min, max), (_, c) ->
				minOf(min, c) to maxOf(max, c)
			}
			maxCol - minCol + 1 // Width of a single block
		}
		if (rowWidth > maxCols) maxCols = rowWidth
	}

	return numRows to maxCols
}

// Fill the matrix with colors from blocks
private fun fillMatrix(matrix: MutableList<MutableList<Int>>, blocks: List<List<Block>>) {
	for ((rowIndex, row) in blocks.withIndex()) {
		var colIndex = 0
		for (block in row) {
			val (minCol, maxCol) = block.coordinates.fold(Int.MAX_VALUE to Int.MIN_VALUE) { (min, max), (_, c) ->
				minOf(min, c) to maxOf(max, c)
			}
			val width = maxCol - minCol + 1

			// Fill the matrix with the block's color across its width
			for (i in 0 until width) {
				if (colIndex >= matrix[rowIndex].size) {
					// Error case: If we've gone beyond the matrix size
					throw IndexOutOfBoundsException("Matrix column index out of bounds")
				}
				matrix[rowIndex][colIndex++] = block.color
			}
		}
	}
}


fun reduceMatrix(matrix: List<List<Int>>): List<List<Int>> {
	var maxColors = -1
	for (row in 0 until matrix.size) {
		val numColors = matrix[row].distinct().size
		if (numColors > maxColors) {
			maxColors = numColors
		}
	}
	// TODO: handle more than 3 colors
	if (maxColors > 3) {
		return emptyList()
	}

	// now force the lists to have maxColors numbers of columns

	val retList : MutableList<List<Int>> = mutableListOf()
	for (row in 0 until matrix.size) {
		var rowList: MutableList<Int> = mutableListOf()
		val listContents = countGroups(matrix[row])
		val numColors = listContents.first
		val theColors = listContents.second

		// if only one color, then duplicate to the max size
		if (numColors == 1) {
			rowList = MutableList(maxColors) { matrix[row].first()}
		} else {
			if (numColors == 2) {
				val color1 = theColors[0]
				val color2 = theColors[1]
				if (maxColors == 3) {
					val numColor1 = matrix[row].count { it == theColors[0] }
					val numColor2 = matrix[row].count { it == theColors[1] }
					if (numColor1 > numColor2) {
						rowList = mutableListOf(color1, color1, color2)
					} else {
						rowList = mutableListOf(color1, color2, color2)
					}
				} else {
					rowList = mutableListOf(color1, color2)
				}
			}

			if (numColors == 3) {
				val color1 = theColors[0]
				val color2 = theColors[1]
				val color3 = theColors[2]
				rowList = mutableListOf(color1, color2, color3)
			}
		}

		retList.add(rowList)
	}
	return retList
}

private fun countGroups(list: List<Int>): Pair<Int, List<Int>> {
	if (list.isEmpty()) return Pair(0,emptyList())
	val listMembers : MutableList<Int> = mutableListOf()
	var count = 1
	var prev = list[0]
	listMembers.add(prev)
	for (i in 1 until list.size) {
		if (list[i] != prev) {
			count++
			prev = list[i]
			listMembers.add(prev)
		}
	}
	return Pair(count, listMembers)
}

/**
In kotlin a Block are defined in a list of the following data structure.
Please create a function that compares the Block data in the list, and
finds the unique Block.  I recommend relocating each Block to the matrix (0,0) origin
and then comparing each block to all the others.   There should be one unique block in
the list.  Please return the unique block as relocated to the (0,0) origin.

Gemini code follows
 */
fun findUniqueBlock(blocks: List<Block>): Pair<Int, Block> {
	fun relocateToOrigin(block: Block): Block {
		val minRow = block.coordinates.minOf { it.first }
		val minCol = block.coordinates.minOf { it.second }
		val relocatedCoordinates = block.coordinates.map { (row, col) -> Pair(row - minRow, col - minCol) }.toSet()
		return Block(block.color, relocatedCoordinates)
	}

	val relocatedBlocks = blocks.map { relocateToOrigin(it) }
	val uniqueBlock = relocatedBlocks.groupBy{ it }.values.singleOrNull {
		it.size == 1 }?.first()

	if (uniqueBlock == null) {
		return Pair(0, Block(0, setOf(Pair(0,0))))
	}

	return Pair(1, uniqueBlock)
}