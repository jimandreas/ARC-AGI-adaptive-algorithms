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

	val retList: MutableList<List<Int>> = mutableListOf()
	for (row in 0 until matrix.size) {
		var rowList: MutableList<Int> = mutableListOf()
		val listContents = countGroups(matrix[row])
		val numColors = listContents.first
		val theColors = listContents.second

		// if only one color, then duplicate to the max size
		if (numColors == 1) {
			rowList = MutableList(maxColors) { matrix[row].first() }
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
	if (list.isEmpty()) return Pair(0, emptyList())
	val listMembers: MutableList<Int> = mutableListOf()
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


	val relocatedBlocks = blocks.map { relocateToOrigin(it) }
	val uniqueBlock = relocatedBlocks.groupBy { it }.values.singleOrNull {
		it.size == 1
	}?.first()

	if (uniqueBlock == null) {
		return Pair(0, Block(0, setOf(Pair(0, 0))))
	}

	return Pair(1, uniqueBlock)
}


/**
In kotlin a Block are defined in a list of the following data structure.
Please create a function that examines each Block in the list.  One of
the Block will have "symmetric" points in it - that is, if the points
in the right half of the coordinate set are mirrored the will be identical
to the points in the left half.  Please return the block that is symmetric.
Gemini code follows:
 */
fun findSymmetricBlock(blocks: List<Block>): Block? {
	for (block in blocks) {
		val coords = block.coordinates
		if (coords.isEmpty() || coords.size % 2 != 0) continue // Skip empty or odd-sized blocks

		val minCol = coords.minOf { it.second }
		val maxCol = coords.maxOf { it.second }
		val midCol = (minCol + maxCol) / 2

		val leftHalf = coords.filter { it.second <= midCol }
		val rightHalf = coords.filter { it.second > midCol }

		if (leftHalf.size != rightHalf.size) continue

		val mirroredRightHalf = rightHalf.map { Pair(it.first, 2 * midCol - it.second) }.toSet()
		if (leftHalf == mirroredRightHalf) return block
	}
	return null
}

/**
 * relocate a Block to begin at 0,0
 */
fun relocateToOrigin(block: Block): Block {
	val minRow = block.coordinates.minOf { it.first }
	val minCol = block.coordinates.minOf { it.second }
	val relocatedCoordinates = block.coordinates.map { (row, col) -> Pair(row - minRow, col - minCol) }.toSet()
	return Block(block.color, relocatedCoordinates)
}

/**
In kotlin an entity is defined by a set of row and column coordinates
as given in Set<Pair<Int, Int>>.
Please create a function that determine if the figure has horizontal
symmetry - that is: the right side is a mirror image of the left side.
Return true if the figure is symmetric, and false otherwise.

GROK code follows:
 */
fun hasHorizontalSymmetry(coordinates: Set<Pair<Int, Int>>): Boolean {
	if (coordinates.isEmpty()) return true // An empty set is symmetric by definition

	// Find the minimum and maximum x-coordinates to determine the central y-line
	val colCoords = coordinates.map { it.second }.toSet()
	val minCol = colCoords.minOrNull() ?: return true
	val maxCol = colCoords.maxOrNull() ?: return true
	val centerColumn = (minCol + maxCol) / 2.0f

	// Check if there is a clear center line
	if (centerColumn != (minCol + maxCol) / 2.0f) {
		return false // No clear center for symmetry
	}

	// For each point, check if its mirrored counterpart exists
	for ((row, col) in coordinates) {
		val mirroredCol = (2 * centerColumn - col).toInt()
		if (!coordinates.contains(Pair(row, mirroredCol))) {
			return false
		}
	}

	return true
}

data class OpeningDirection(
	val vertical: VerticalDirection = VerticalDirection.NA,
	val horizontal: HorizontalDirection = HorizontalDirection.NA
)

enum class VerticalDirection {
	UP, DOWN, NA
}

enum class HorizontalDirection {
	LEFT, RIGHT, NA
}


fun findOpeningDirection(coordinates: Set<Pair<Int, Int>>): OpeningDirection {
	if (coordinates.isEmpty()) {
		return OpeningDirection() // Handle empty input
	}

	val minRow = coordinates.minOf { it.first }
	val maxRow = coordinates.maxOf { it.first }
	val minCol = coordinates.minOf { it.second }
	val maxCol = coordinates.maxOf { it.second }

	// Generate all possible coordinates within the rectangle
	val allCoordinates = mutableSetOf<Pair<Int, Int>>()
	for (row in minRow..maxRow) {
		for (col in minCol..maxCol) {
			allCoordinates.add(Pair(row, col))
		}
	}

	// Find the missing coordinates
	val missingCoordinates = allCoordinates - coordinates

	if (missingCoordinates.isEmpty()) {
		return OpeningDirection() // No missing coordinates, it's already a rectangle
	}

	// Determine the opening direction based on missing coordinates
	val vertical = when {
		missingCoordinates.all { it.first == minRow } -> VerticalDirection.UP
		missingCoordinates.all { it.first == maxRow } -> VerticalDirection.DOWN
		else -> VerticalDirection.NA
	}

	val horizontal = when {
		missingCoordinates.all { it.second == minCol } -> HorizontalDirection.LEFT
		missingCoordinates.all { it.second == maxCol } -> HorizontalDirection.RIGHT
		else -> HorizontalDirection.NA
	}

	return OpeningDirection(vertical, horizontal)
}

/**
 * just shove a block by rd and cd
 */
fun translateBlockBy(block: Block, rd: Int, cd: Int): Block {
	val newBlock = block.copy(coordinates = block.coordinates.map {
		(row, col) -> Pair(row + rd, col + cd) }
			.toSet())

	return newBlock
}

/**
In Kotlin I have a Set<Pair<Int, Int>> - that are row and col
coordinates into a matrix List<List<Int>> - please provide a
means to determine the majority matrix values referred to by the Set.
 GROK code follows:
 */
fun findMajorityColor(matrix: List<List<Int>>, coordinates: Set<Pair<Int, Int>>): Int {
	// Map coordinates to matrix values
	val values = coordinates.map { (row, col) -> matrix[row][col] }

	// Count occurrences of each value
	val valueCounts = values.groupingBy { it }.eachCount()

	// If there's only one unique value, return it
	if (valueCounts.size == 1) return values.first()

	// Find the maximum count
	val maxCount = valueCounts.values.maxOrNull() ?: 0

	// Filter values with the max count
	val majorityCandidates = valueCounts.filter { it.value == maxCount }

	// If there's a tie for the majority, return one of them (you might want to implement a tie-breaker)
	return majorityCandidates.keys.first()
}