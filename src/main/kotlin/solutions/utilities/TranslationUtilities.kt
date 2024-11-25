@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.utilities

import Block
import Point
import kotlin.math.ceil
/*
DIRECTION CONSISTENCY CHECK -

Grok prompts:
I have a list of groupedTranslations: Map<Int, Map<Pair<Int, Int>, Int>>
where the first Map<Int is for a value in an array, the Pair<Int,Int>
represents in which row and column direction this value has moved
in the array, and the last ,Int is the number of times it has moved.
Please create a function that determines from the Pair<Int,Int>
if the movement is uniformly in one direction, that is, the
signs of the Int,Int are consistent through the List of these values.

Good now make the function iterate over a list of the "groupedTranslations"
parameter and return true only if the entire list is consistent.

That fails because it doesn't compare the first entry in the list to the
second to see if the directions are the same.  Only that the directions
are consistent inside one map.  Please revise the loop so that it compares
the directions not only with each other in the map but also from one entry
in the list to the next.
 */

/**
 * Explanation:
 *
 *     extractDirection: This function simplifies the pair into a direction by
 *     determining if it's positive, negative, or zero for both coordinates.
 *     isConsistentDirection: This checks if two directions (extracted from movements)
 *     are the same.
 *     The main loop does the following:
 *         It sets a reference direction from the first movement encountered.
 *         For each map in the list, it checks:
 *             If the movements within the map are consistent with each other.
 *             If the first movement of each map is consistent with the reference
 *             direction set from the first map in the list. If any inconsistency
 *             is found, it immediately returns false.
 *     This ensures consistency not only within each map but across all maps in the list.
 */
fun isEntireMapConsistent(groupedTranslationsMap: Map<Int, Map<Pair<Int, Int>, Int>>): Boolean {
	if (groupedTranslationsMap.isEmpty()) return true

	fun extractDirection(pair: Pair<Int, Int>): Pair<Int, Int> {
		val rowSign = when {
			pair.first > 0 -> 1
			pair.first < 0 -> -1
			else -> 0
		}
		val colSign = when {
			pair.second > 0 -> 1
			pair.second < 0 -> -1
			else -> 0
		}
		return Pair(rowSign, colSign)
	}

	fun isConsistentDirection(firstPair: Pair<Int, Int>, secondPair: Pair<Int, Int>): Boolean {
		val firstDirection = extractDirection(firstPair)
		val secondDirection = extractDirection(secondPair)
		return firstDirection == secondDirection
	}

	var referenceDirection: Pair<Int, Int>? = null
	for (movements in groupedTranslationsMap.values) {
		val firstMovement = movements.keys.firstOrNull() ?: continue
		if (referenceDirection == null) {
			referenceDirection = extractDirection(firstMovement)
		} else {
			if (!isConsistentDirection(referenceDirection, firstMovement)) return false
		}
		if (!movements.keys.all { isConsistentDirection(firstMovement, it) }) return false
	}

	return true
}

/**
There are two Kotlin data structures that contain information on a
matrix.  They are lists of the following two kotlin data classes:

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
data class Point(val color: Int, val coordinate: Pair<Int, Int>)

please take these lists and also the numRow and numCol parameters
and recreate the list as a List<List<Int>> and fill non-specified
cells with the "color" of 0.   The colors, that is the Int values
for the Block and Point entities are specified in the data class.

 Code created by Google Gemini follows:
 */
fun recreateMatrix(
	numRow: Int,
	numCol: Int,
	blocks: List<Block>,
	points: List<Point>
): List<List<Int>> {

	// Initialize the matrix with all cells set to 0
	val matrix = MutableList(numRow) { MutableList(numCol) { 0 } }

	// Fill in the blocks
	for (block in blocks) {
		for ((row, col) in block.coordinates) {
			if ((row > numRow-1) || (col > numCol-1)) return emptyList()
			matrix[row][col] = block.color
		}
	}

	// Fill in the points
	for (point in points) {
		val (row, col) = point.coordinate
		if ((row > numRow-1) || (col > numCol-1)) return emptyList()
		matrix[row][col] = point.color
	}

	return matrix
}

/**
 * change all block colors to the new color
 */
fun changeBlockColor(blocks: List<Block>, newColor: Int): List<Block> {
	return blocks.map { it.copy(color = newColor) }
}

/**
 * find a non-matching color in a block list
 */
fun findOtherColor(blocks: List<Block>, findColor: Int): Int {
	for (b in blocks) {
		if (b.color != findColor) {
			return b.color
		}
	}
	return findColor
}

/**
In Kotlin, for a given List<Point>, please return the character
"H" if the points lie on a row in the matrix, and a "V" if the
points lie in a column.
Gemini code follows
 */
fun getOrientation(points: List<Point>): Char? {
	if (points.isEmpty()) return null

	val firstRow = points[0].coordinate.first
	val firstCol = points[0].coordinate.second

	if (points.all { it.coordinate.first == firstRow }) {
		return 'H' // All points have the same row
	} else if (points.all { it.coordinate.second == firstCol }) {
		return 'V' // All points have the same column
	}

	return null // Points are not all in the same row or column
}


/**
For a list of Blocks,
1 - verify that all blocks have the same color.
2 - split each block vertically to create two new lists of blocks.
The top list vertical size should be rounded down, so that if a block has
a height of 7, the top list should have a block of height 3, and the bottom of height 4.
Return the pair of block lists with the top blocks as first, and the bottom blocks as
second in the pair.

Gemini code follows: */
fun splitBlocksVertically(blocks: List<Block>): Pair<List<Block>, List<Block>> {
	if (blocks.isEmpty()) return Pair(emptyList(), emptyList())

	val firstColor = blocks[0].color
	if (blocks.any { it.color != firstColor }) {
		return Pair(emptyList(), emptyList())  // colors don't match
	}

	val topBlocks = mutableListOf<Block>()
	val bottomBlocks = mutableListOf<Block>()

	for (block in blocks) {
		val rows = block.coordinates.map { it.first }
		val minRow = rows.minOrNull()!!
		val maxRow = rows.maxOrNull()!!
		var midRow = ceil((minRow + maxRow) / 2.0).toInt()
		// adjust for even count of blocks
		if ((maxRow - minRow + 1) % 2 == 0) {
			midRow = midRow-1
		}

		val topCoordinates = block.coordinates.filter { it.first <= midRow }.toSet()
		val bottomCoordinates = block.coordinates.filter { it.first > midRow }.toSet()

		topBlocks.add(Block(block.color, topCoordinates))
		bottomBlocks.add(Block(block.color, bottomCoordinates))
	}

	return Pair(topBlocks, bottomBlocks)
}

/**
in Kotlin given two arrays in the form List<List<Int>>,
please create a function to "prettyprint" that is format
and output in rows and columns and, if the matrix is smaller
than 10x10, include indexes on the left for rows and add an
index of single digits at the bottom for columns - and then
show with an "X" where there is a difference between the two
matrices and a "O" where the matrices are the same.

 Gemini code follows:
 */
fun prettyPrintMatrixDiff(matrix1: List<List<Int>>, matrix2: List<List<Int>>) {
	if (matrix1.isEmpty() || matrix2.isEmpty()) {
		println("NO DIFF for empty matrices")
		return
	}

	val numRows = matrix1.size
	val numCols = matrix1[0].size

	// Ensure matrices have the same dimensions
	if (numRows != matrix2.size || numCols != matrix2[0].size) {
		println("Matrices must have the same dimensions")
		return
	}

	// Print column indices if matrix is smaller than 10x10
	if (numRows < 10 && numCols < 10) {
		print("   ")
		for (j in 0 until numCols) {
			print(" $j")
		}
		println()
	}

	// Print rows with differences marked
	for (i in 0 until numRows) {
		if (numRows < 10 && numCols < 10) {
			print("$i  ") // Print row index
		}
		for (j in 0 until numCols) {
			if (matrix1[i][j] == matrix2[i][j]) {
				print(" O")
			} else {
				print(" X")
			}
		}
		println()
	}

	// Print column indices if matrix is smaller than 10x10
	if (numRows < 10 && numCols < 10) {
		print("   ")
		for (j in 0 until numCols) {
			print(" $j")
		}
		println()
	}
}

/**
In Kotlin, for an input pair of List<Block> and List<Point>,
create a fun to find the list entry that matches in the
following data structure:

List<Pair<List<Block>, List<Point>>>

and return the Pair<List<Block>, List<Point>> that matches.

Good but I messed up.   What I need is the index into the list where
the match occurred, rather than the matching entry.  Please revise the function.

Ooops I forgot the clarify again.  The search should ignore the colors
of the blocks and points, and only compare them based on their coordinate(s).

 Gemini code follows:
 */

fun findMatchingEntryIndex(  // based only on coordinates, return index
	inputBlocks: List<Block>,
	inputPoints: List<Point>,
	data: List<Pair<List<Block>, List<Point>>>
): Int? {

	val inputBlockCoordinates = inputBlocks.flatMap { it.coordinates }.toSet()
	val inputPointCoordinates = inputPoints.map { it.coordinate }.toSet()

	for ((index, entry) in data.withIndex()) {
		val (blocks, points) = entry
		val blockCoordinates = blocks.flatMap { it.coordinates }.toSet()
		val pointCoordinates = points.map { it.coordinate }.toSet()

		if (blockCoordinates == inputBlockCoordinates
			&& pointCoordinates == inputPointCoordinates) {
			return index
		}
	}
	return null
}