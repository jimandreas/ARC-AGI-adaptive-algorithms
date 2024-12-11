@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.utilities

import Block
import Point

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


fun convertBlocksToMatrix(blocks: List<Block>): List<List<Int>> {
	if (blocks.isEmpty()) return emptyList()

	// Determine the dimensions of the matrix
	val (numRows, maxCols) = calculateGridSize(blocks)

	// Create a matrix initialized with zeros
	val matrix = MutableList(numRows) { MutableList(maxCols) { 0 } }

	// Fill the matrix with block colors
	//fillMatrix(matrix, blocks)

	for (b in blocks) {
		val color = b.color
		for ((row, col) in b.coordinates) {
			matrix[row][col] = color
		}
	}

	return matrix
}

// Calculate the size of the grid based on blocks
private fun calculateGridSize(blocks: List<Block>): Pair<Int, Int> {

	if (blocks.isEmpty()) {
		return Pair(0, 0)
	}

	var maxRowOut = 0
	var maxColOut = 0

	for (b in blocks) {
		val coordinates = b.coordinates
		val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first }
		val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second }
		if (maxRowOut < maxRow) {
			maxRowOut = maxRow
		}
		if (maxColOut < maxCol) {
			maxColOut = maxCol
		}
	}

	return Pair(maxRowOut + 1, maxColOut + 1)

}

// TODO: add unit tests
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

fun findUniqueBlockCoordinatesOnly(blocks: List<Block>): Pair<Int, Block> {

	val relocatedBlocks = blocks.map { relocateToOrigin(it) }
	val uniqueBlock = relocatedBlocks.groupBy { it.coordinates }.values.singleOrNull {
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
I have a List<Block> in Kotlin. Please create a function to translate their
row and column coordinate data so that the group is now based at the 0,0 origin.
Gemini code follows:
 */

fun translateBlocksToOrigin(blocks: List<Block>): List<Block> {
	if (blocks.isEmpty()) return emptyList()

	// Find the minimum row and column across all blocks
	val minRow = blocks.flatMap { it.coordinates }.minOf { it.first }
	val minCol = blocks.flatMap { it.coordinates }.minOf { it.second }

	// Translate each block to the origin
	return blocks.map { block ->
		val translatedCoordinates = block.coordinates.map { (row, col) ->
			Pair(row - minRow, col - minCol)
		}.toSet()
		Block(block.color, translatedCoordinates)
	}
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
	val newBlock = block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row + rd, col + cd) }
		.toSet())

	return newBlock
}

fun translatePointBy(point: Point, rd: Int, cd: Int): Point {
	val newCoor = Pair(point.coordinate.first + rd, point.coordinate.second + cd)
	val newPoint = Point(point.color, newCoor)

	return newPoint
}

/**
 * shove a list of Block
 */
fun translateBlockListBy(blockList: List<Block>, rd: Int, cd: Int): List<Block> {
	val retList: MutableList<Block> = mutableListOf()
	for (b in blockList) {
		val newb = translateBlockBy(b, rd, cd)
		retList.add(newb)
	}
	return retList
}

/**
In Kotlin I have a Set<Pair<Int, Int>> - that are row and col
coordinates into a matrix List<List<Int>> - please provide a
means to determine the majority matrix values referred to by the Set.
GROK code follows:
 */
fun findMajorityColorBasedOnCoordinates(matrix: List<List<Int>>, coordinates: Set<Pair<Int, Int>>): Int {
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

/**
 * count occurences of color based on coordinates
 * return count.  Code based on above function.
 */
fun countColorBasedOnCoordinates(color: Int, matrix: List<List<Int>>, coordinates: Set<Pair<Int, Int>>): Int {
	// Map coordinates to matrix values
	val values = coordinates.map { (row, col) -> matrix[row][col] }

	// Count occurrences of each value
	val valueCounts = values.groupingBy { it }.eachCount()

	// Filter values with the max count
	val majorityCandidates = valueCounts.filter { it.key == color }

	if (majorityCandidates.isEmpty()) {
		return 0
	}
	// should be no tie - since filter just returns the target color
	return majorityCandidates.values.first()
}

/**
 *
 */
fun countColorsInMatrix(matrix: List<List<Int>>): List<Int> {
	// Flatten the matrix and filter out zeros
	val values = matrix.flatten().filter { it != 0 }.sorted()

	// Count occurrences of each value
	val colorList = values.distinct()

	return colorList
}
/**
 * Finds the majority value in the matrix, excluding zeros.
 * If no majority exists (all values are equal or there's a tie),
 * it will return one of the most frequent values arbitrarily.
 * GROK code
 */
fun findMajorityColorInMatrix(matrix: List<List<Int>>): Int {
	// Flatten the matrix and filter out zeros
	val values = matrix.flatten().filter { it != 0 }

	// Count occurrences of each value
	val valueCounts = values.groupingBy { it }.eachCount()

	// If all values are zero or there are no values, return 0 or handle appropriately
	if (valueCounts.isEmpty()) return 0

	// Find the maximum count
	val maxCount = valueCounts.values.maxOrNull() ?: 0

	// Filter values with the max count
	val majorityCandidates = valueCounts.filter { it.value == maxCount }

	// Return one of the majority candidates. In case of a tie, this might not be deterministic.
	return majorityCandidates.keys.first()
}


/**
Quantize

Take the smallest block and use it as the quanta.
Create a matrix using this block as a chunk to form an abstraction of
the blocks.
 */

fun quantize(bl: List<Block>, retColor: Int): List<List<Int>> {

	if (bl.isEmpty()) {
		return emptyList()
	}

	val blocksSortedBySize = bl.sortedBy { it.coordinates.size }
	val blockCoordinates = blocksSortedBySize[0].coordinates

	val minRow = blockCoordinates.minOf { it.first }
	val maxRow = blockCoordinates.maxOf { it.first }
	val minCol = blockCoordinates.minOf { it.second }
	val maxCol = blockCoordinates.maxOf { it.second }

	val rowQ = maxRow - minRow + 1
	val colQ = maxCol - minCol + 1
	if (rowQ <= 0 || colQ <= 0) {
		return emptyList()
	}

	val allBlockCoordinates = bl.flatMap { it.coordinates }.sortedBy { it.first }
	var maxRowGroup = allBlockCoordinates.maxOf { it.first } + 1
	var maxColGroup = allBlockCoordinates.maxOf { it.second } + 1

	val rowQuadrants = maxRowGroup / rowQ
	val colQuadrants = maxColGroup / colQ

	var outputMatrix = MutableList(rowQuadrants) { MutableList(colQuadrants) { 0 } }
	for (row in 0 until rowQuadrants) {
		for (col in 0 until colQuadrants) {
			val baseRow = row * rowQ
			val baseCol = col * colQ
			if (coordsPresent(allBlockCoordinates, baseRow, baseCol, rowQ, colQ)) {
				outputMatrix[row][col] = retColor
			}
		}
	}

	return outputMatrix

}

/**
 * return true if the coords set have all coords in the region provided
 */
private fun coordsPresent(
	coords: List<Pair<Int, Int>>,
	baseRow: Int, baseCol: Int,
	rowQ: Int, colQ: Int
): Boolean {

	for (row in baseRow until baseRow + rowQ) {
		for (col in baseCol until baseCol + colQ) {
			if (!coords.contains(Pair(row, col))) {
				return false
			}
		}
	}
	return true
}

/**
I have a set of row and col coordinates in Kotlin in the form of
coordinates: Set<Pair<Int, Int>> in a matrix of rowSize and
colSize dimensions.   Please create a function that finds a
line of connected points along a row or column that span the
matrix completely horizontally or vertically and return a list
of those coordinates.  Return emptyList if no line is found.

NOTE: I like how it fills in a test set in the
"horizontalLine" and "verticalLine" below.
GROK code follows:
 */

fun findSpanningLine(coordinates: Set<Pair<Int, Int>>, rowSize: Int, colSize: Int): List<Pair<Int, Int>> {
	// Check for horizontal line
	for (row in 0 until rowSize) {
		val horizontalLine = (0 until colSize).map { col -> Pair(row, col) }.toSet()
		if (coordinates.containsAll(horizontalLine)) {
			return horizontalLine.toList()
		}
	}

	// Check for vertical line
	for (col in 0 until colSize) {
		val verticalLine = (0 until rowSize).map { row -> Pair(row, col) }.toSet()
		if (coordinates.containsAll(verticalLine)) {
			return verticalLine.toList()
		}
	}

	// No spanning line found
	return emptyList()
}

/**
I have a set of row and col coordinates in Kotlin in the form of
coordinates: Set<Pair<Int, Int>> in a matrix of rowSize and
colSize dimensions.   Please create a function that finds a
line of connected points along a row or column that span the
matrix completely horizontally or vertically and return a list
of those coordinates.  Return emptyList if no line is found.
 */
// GROK - HACKED to look for L
fun findSpanningLineWithBUMP(coordinates: Set<Pair<Int, Int>>, rowSize: Int, colSize: Int): Boolean {
	if (rowSize < 3 || colSize < 3) {
		return false
	}
	// Check for horizontal line
	for (row in 0 until rowSize) {
		val horizontalLine = (0 until colSize).map { col -> Pair(row, col) }.toSet()
		if (coordinates.containsAll(horizontalLine)) {
			// It is horizontal.

			val theRest = coordinates - horizontalLine

			// look for exactly one point on either side of this line
			//   start with one below - if not at bottom
			if (row != rowSize - 1) {
				val testLine = (0 until colSize).map { col -> Pair(row + 1, col) }.toSet()
				val testPoints = theRest.intersect(testLine).map { it.second }
				if (testPoints.size == 1) {
					val singleValue = testPoints[0]
					if (singleValue == 0 || singleValue == colSize - 1) {
						return true
					}
				}
			}
			// continue with one above, if not at top
			if (row != 0) {
				val testLine = (0 until colSize).map { col -> Pair(row - 1, col) }.toSet()
				val testPoints = theRest.intersect(testLine).map { it.second }
				if (testPoints.size != 1) {
					continue
				}
				val singleValue = testPoints[0]
				if (singleValue == 0 || singleValue == colSize - 1) {
					return true
				}
			}
		}
	}

	// Check for vertical line
	for (col in 0 until colSize) {
		val verticalLine = (0 until rowSize).map { row -> Pair(row, col) }.toSet()
		if (coordinates.containsAll(verticalLine)) {

			// It is vertical

			val theRest = coordinates - verticalLine

			// look for exactly one point on either side of this line
			//   start with one to right - if not at right side
			if (col != colSize - 1) {
				val testLine = (0 until rowSize).map { row -> Pair(row, col + 1) }.toSet()
				val testPoints = theRest.intersect(testLine).map { it.first }
				if (testPoints.size == 1) {
					val singleValue = testPoints[0]
					if (singleValue == 0 || singleValue == rowSize - 1) {
						return true
					}
				}
			}
			// continue with one to left, if not at the left side
			if (col != 0) {
				val testLine = (0 until colSize).map { row -> Pair(row, col - 1) }.toSet()
				val testPoints = theRest.intersect(testLine).map { it.first }
				if (testPoints.size != 1) {
					continue
				}
				val singleValue = testPoints[0]
				if (singleValue == 0 || singleValue == rowSize - 1) {
					return true
				}
			}
		}
	}
	return false
}

/**
Please provide a function that takes a matrix and a list of Block,
as well as two Pairs - Pair(baseRow, baseCol) and Pair(endRow, endCol)
that define a subregion within the matrix.   The goal is to
iterate through the list of Block and find Block entries that
have coordinate points within the subregion.  Modify the list of Block
so that only Block entries that have coordinates in the subregion are
returned, and that the coordinates of the Block entries are also clipped to
the boundaries of the subregion.   Return the emptyList() if there are
no blocks with coordinates in the subregion.
 */
fun clipBlocksToSubregion(
	matrix: List<List<Int>>,
	blocks: List<Block>,
	base: Pair<Int, Int>,
	end: Pair<Int, Int>
): List<Block> {

	val (baseRow, baseCol) = base
	val (endRow, endCol) = end

	val clippedBlocks = blocks.mapNotNull { block ->
		val clippedCoordinates = block.coordinates.filter { (row, col) ->
			row in baseRow..endRow && col in baseCol..endCol
		}.toSet()

		if (clippedCoordinates.isNotEmpty()) {
			Block(block.color, clippedCoordinates)
		} else {
			null // Remove blocks with no coordinates in the subregion
		}
	}

	return if (clippedBlocks.isEmpty()) emptyList() else clippedBlocks
}

/**
thoroughly check two List<List<Int>> style matrices
 */
fun compareMatrices(matrix1: List<List<Int>>, matrix2: List<List<Int>>): Boolean {
	if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
		return false // Matrices have different dimensions
	}
	for (i in matrix1.indices) {
		for (j in matrix1[0].indices) {
			if (matrix1[i][j] != matrix2[i][j]) {
				return false // Cells don't match
			}
		}
	}
	return true // Matrices are identical
}

/**
I have a list of Block in Kotlin with row and col coordinates as shown:
data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
Please sort this by the second coordinate in the Set<Pair<Int, Int>>.
 Grok code follows
 */
fun sortBlocksByColumn(blocks: List<Block>): List<Block> {
	return blocks.sortedWith { a, b ->
		val minColA = a.coordinates.minOfOrNull { it.second } ?: Int.MAX_VALUE
		val minColB = b.coordinates.minOfOrNull { it.second } ?: Int.MAX_VALUE
		minColA.compareTo(minColB)
	}
}

// modified Gemini code
fun areAllBlocksHorizontalRows(matrix: List<List<Int>>, blocks: List<Block>): Boolean {
	val numCols = matrix[0].size

	for (block in blocks) {
		val cols = block.coordinates.map { it.second }.toSet() // Get unique col numbers
		if (cols.size != numCols) { // If the block doesn't cover all cols, it's not a full row
			return false
		}
		if (block.coordinates.size % numCols != 0) {
			return false
		}
		val rows = block.coordinates.map { it.first }.toSet() // Get unique row numbers
		if (rows.size != block.coordinates.size / numCols) {
			return false
		}

	}
	return true // If all blocks pass the checks, return true
}

fun areAllBlocksVerticalColumns(matrix: List<List<Int>>, blocks: List<Block>): Boolean {
	val numRows = matrix.size

	for (block in blocks) {
		val rows = block.coordinates.map { it.first }.toSet() // Get unique row numbers
		if (rows.size != numRows) { // If the block doesn't cover all rows, it's not a full column
			return false
		}
		if (block.coordinates.size % numRows != 0) {
			return false
		}
		val cols = block.coordinates.map { it.second }.toSet() // Get unique col numbers
		if (cols.size != block.coordinates.size / numRows) {
			return false
		}
	}
	return true // If all blocks pass the checks, return true
}