@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.utilities

import Block

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

