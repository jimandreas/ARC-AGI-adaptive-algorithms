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
	points: List<Point>,
	fillColor:Int  = 0,
	overrideColors: Boolean = false,
	colorToUse: Int = 0
): List<List<Int>> {

	// Initialize the matrix with all cells set to 0
	val matrix = MutableList(numRow) { MutableList(numCol) { fillColor } }

	// Fill in the blocks
	for (block in blocks) {
		for ((row, col) in block.coordinates) {
			if ((row > numRow - 1) || (col > numCol - 1)) return emptyList()

			if (row < 0 || col < 0) {
				// whooopsie
				return emptyList()
			}
			if (!overrideColors) {
				matrix[row][col] = block.color
			} else {
				matrix[row][col] = colorToUse
			}
		}
	}

	// Fill in the points
	for (point in points) {
		val (row, col) = point.coordinate
		if ((row > numRow - 1) || (col > numCol - 1)) return emptyList()
		if (!overrideColors) {
			matrix[row][col] = point.color
		} else {
			matrix[row][col] = colorToUse
		}
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
			midRow = midRow - 1
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
			&& pointCoordinates == inputPointCoordinates
		) {
			return index
		}
	}
	return null
}

/**
In Kotlin, the Block data structure has a color Int and a
set of row/col coordinates for points in an matrix.

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)

Please create a function that scans the coordinates to see if
they enclose a non-zero point in the matrix.   Please return the
coordinates of the enclosed point as a Pair<Int,Int> and
null if no enclosed point is found.  Note that the block might be
irregular, but the block matrix elements should completely surround the point.
 */

// note that this checks to see if the enclosed point is non-zero
fun findEnclosedPoint(block: Block, matrix: List<List<Int>>): Pair<Int, Int>? {
	val rows = block.coordinates.map { it.first }
	val cols = block.coordinates.map { it.second }
	val minRow = rows.minOrNull()!!
	val maxRow = rows.maxOrNull()!!
	val minCol = cols.minOrNull()!!
	val maxCol = cols.maxOrNull()!!

	for (r in minRow + 1..maxRow - 1) {  // Start from the second row and go to the second-to-last
		for (c in minCol + 1..maxCol - 1) { // Start from the second col and go to the second-to-last
			if (Pair(r, c) !in block.coordinates && matrix[r][c] != 0) {
				// Found a non-zero point not in the block's coordinates
				// Now check if it's fully surrounded
				if (isPointEnclosed(r, c, block.coordinates)) {
					return Pair(r, c)
				}
			}
		}
	}

	return null // No enclosed point found
}

fun isPointEnclosed(row: Int, col: Int, blockCoordinates: Set<Pair<Int, Int>>): Boolean {
	// Check if all 4-neighbors are in the block's coordinates
	return (row - 1 to col) in blockCoordinates &&
			(row + 1 to col) in blockCoordinates &&
			(row to col - 1) in blockCoordinates &&
			(row to col + 1) in blockCoordinates
}

/**
In Kotlin, the Block data structure has a color Int and a set
of row/col coordinates for points in an matrix, and the Point
data structure has a color Int and a single row/col
coordinate.

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
data class Point(val color: Int, val coordinate: Pair<Int, Int>)

The desired function takes a block and it represents an extent of a
region in a matrix.  It might not have all the points in it that are
in the region in the matrix.   Please examine the points to see if
they are also located in the same region in the matrix.  Count the
points that also exist in the same region and return the number of
points that are in the same region.
Gemini code follows:
 */

fun countPointsInRegion(block: Block, points: List<Point>): Int {
	// Find the bounding box of the block
	val rows = block.coordinates.map { it.first }
	val cols = block.coordinates.map { it.second }
	val minRow = rows.minOrNull()!!
	val maxRow = rows.maxOrNull()!!
	val minCol = cols.minOrNull()!!
	val maxCol = cols.maxOrNull()!!

	var count = 0
	for (point in points) {
		val (row, col) = point.coordinate
		// Check if the point is within the bounding box of the block
		if (row in minRow..maxRow && col in minCol..maxCol) {
			count++
		}
	}
	return count
}

/**

For a given matrix in kotlin of the form List<List<Int>>,
create a function to rotate the outer rows and columns either
clockwise (with an input parameter 0) or counterclockwise
(with an input parameter of 1).  Return the rotated matrix.

Oops sorry that I was not clear on my specifications.
The matrix should be rotated 90 degrees, for a clockwise
rotation the top row should be rotated to form the right column, etc.
For counterclockwise, the top row should be rotated to form the left column.
 */
fun rotateMatrix(matrix: List<List<Int>>, direction: Int): List<List<Int>> {
	if (matrix.isEmpty()) return matrix

	val numRows = matrix.size
	val numCols = matrix[0].size

	// Create a new matrix with swapped dimensions
	val rotatedMatrix = MutableList(numCols) { MutableList(numRows) { 0 } }

	if (direction == 0) { // Clockwise
		for (i in 0 until numRows) {
			for (j in 0 until numCols) {
				rotatedMatrix[j][numRows - 1 - i] = matrix[i][j]
			}
		}
	} else { // Counterclockwise
		for (i in 0 until numRows) {
			for (j in 0 until numCols) {
				rotatedMatrix[numCols - 1 - j][i] = matrix[i][j]
			}
		}
	}

	return rotatedMatrix
}

/**

For a give list of Block and list of Point data classes,
create a function to aggregate the quantity of cells of
each color.  Count the number of each color in the Set<Pair, Pair> coordinates
for the Block data class, and add the number of Point that have the same color.
Return a descending sorted map of color to quantity.

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
data class Point(val color: Int, val coordinate: Pair<Int, Int>)

 */

fun aggregateColorQuantities(blocks: List<Block>, points: List<Point>): Map<Int, Int> {
	val colorCounts = mutableMapOf<Int, Int>()

	// Count colors from blocks
	for (block in blocks) {
		colorCounts[block.color] = (colorCounts[block.color] ?: 0) + block.coordinates.size
	}

	// Count colors from points
	for (point in points) {
		colorCounts[point.color] = (colorCounts[point.color] ?: 0) + 1
	}

	// Sort the map in descending order by quantity
	return colorCounts.entries.sortedByDescending { it.value }.associate { it.toPair() }
}


/**
For a given matrix in Kotlin as a List<List<Int>>
and a given list of Block as given below,
return a 1 if each block has coordinates that span an
entire row of the matrix, and a 2 if each block has
coordinates that span an entire column of the matrix.
Return 0 if neither case is true.

REVISED with this functionality: returns the
indicator -
0 - no spanning values
1 - for column spanning,
2 - for row spanning, and
list of colors that span a column or row
 */


fun checkBlockSpan(matrix: List<List<Int>>, blocks: List<Block>): Pair<Int, List<Int>> {
	val numRows = matrix.size
	val numCols = matrix[0].size

	val colorList: MutableList<Int> = mutableListOf()

	val isColSpan = blocks.all { block ->
		val byColumns = block.coordinates.map { it.second }
		val occurrences = countOccurrences(byColumns)
		val maxNumber = getMaxCountFromMap(occurrences)
		if (maxNumber == numRows) {
			colorList.add(block.color)
		}
		maxNumber == numRows
	}

	val isRowSpan = blocks.all { block ->
		val byRows = block.coordinates.map { it.first }
		val occurrences = countOccurrences(byRows)
		val maxNumber = getMaxCountFromMap(occurrences)
		if (maxNumber == numCols) {
			colorList.add(block.color)
		}
		maxNumber == numCols
	}

	return when {
		isColSpan -> Pair(1, colorList.toList())
		isRowSpan -> Pair(2, colorList.toList())
		else -> Pair(0, emptyList())
	}
}

/**
 In kotlin I have an ArrayList of Ints and I want to count how many
 0's, 1's, 2's etc are in the list.    How do I count these occurrences?

 Gemini: Count occurrences: For each item, it uses counts.getOrDefault(item, 0)
 to get the current count of that item in the map (defaulting to 0 if not found)
 and increments it by 1.
 */
fun countOccurrences(list: List<Int>): Map<Int, Int> {
	val counts = mutableMapOf<Int, Int>()
	for (item in list) {
		counts[item] = counts.getOrDefault(item, 0) + 1
	}
	return counts
}



/**
    This function, getMaxCountFromMap, takes a Map<Int, Int>
    (where the keys are numbers and the values are their counts)
    as input and returns the maximum count.
 */
fun getMaxCountFromMap(counts: Map<Int, Int>): Int {
	return counts.values.maxOrNull() ?: 0
}

/**
Please convert a List<Int> to a List<List<Int>>
 Gemini:
 map { listOf(it) }: This uses the map function to
transform each element (it) in the inputList into a
new list containing only that element (listOf(it)).
This creates a list of single-element lists.
 */

fun convertList(inputList: List<Int>): List<List<Int>> {
	return inputList.map { listOf(it) }
}

/**

I have two data classes in Kotlin - a list of Block and a list of Point.
Please create a function that returns the number of unique colors
when both lists are aggregated.
 */

fun uniqueColors(blocks: List<Block>, points: List<Point>): Int {
	val blockColors = blocks.map { it.color }
	val pointColors = points.map { it.color }
	return (blockColors + pointColors).distinct().size
}

/**
Now please create a function that, assuming there is only
one color, counts the coordinate Pairs in the list of Block
and Point data class lists and returns a Pair with the color
value and the count of coordinate Pairs.
 Gemini code follows:
 */
fun countCoordinatesWithSingleColor(blocks: List<Block>, points: List<Point>): Pair<Int, Int> {
//	require(blocks.all { it.color == blocks.first().color }
//			&& points.all { it.color == points.first().color }) {
//		"All blocks and points must have the same color"
//	}

	val color = blocks.firstOrNull()?.color ?: points.firstOrNull()?.color
	if (color == null) {
		return Pair(0, 0)
	}
	val blockCoordinatesCount = blocks.sumOf { it.coordinates.size }
	val pointCoordinatesCount = points.size

	return Pair(color, blockCoordinatesCount + pointCoordinatesCount)
}

/**
for a given rowCount and colCount and the kotlin matrix
List<List<Int>>,  return a submatrix from the top left
origin of size rowCount and colCount in the form List<List<Int>>.
Return the emptyList() if the given matrix is smaller than rowCount or colCount.
 Gemini code follows:
 */
fun getSubmatrix(matrix: List<List<Int>>, rowCount: Int, colCount: Int): List<List<Int>> {
	if (matrix.size < rowCount || matrix[0].size < colCount) {
		return emptyList()
	}

	return matrix.subList(0, rowCount).map { it.subList(0, colCount) }
}

/**
for a given rowCount and colCount and the kotlin matrix
List<List<Int>>,  return a submatrix from the top right region of
the matrix of  size rowCount and colCount in the form List<List<Int>>.
Return the emptyList() if the given matrix is smaller than rowCount or colCount.
Gemini code follows:
 */

fun topRightSubmatrix(matrix: List<List<Int>>, rowCount: Int, colCount: Int): List<List<Int>> {
	val rows = matrix.size
	val cols = matrix[0].size

	if (rows < rowCount || cols < colCount) {
		return emptyList() // Not enough rows or columns in the original matrix
	}

	val startRow = 0
	val endRow = rowCount - 1
	val startCol = cols - colCount
	val endCol = cols - 1

	return matrix.subList(startRow, endRow + 1).map {
		row -> row.subList(startCol, endCol + 1)
	}
}

/**
For a given Kotlin matrix of form List<List<Int>> that is evenly partitioned
into rowRegions and colRegions, please map the cells and return the majority
cell value in a new List<List<Int>> - where each entry in the new list
is the majority cell value in the respective region.

 Gemini code follows:
 */
fun getMajorityValuesInRegions(
	matrix: List<List<Int>>,
	rowRegions: Int,
	colRegions: Int
): List<List<Int>> {

	val numRows = matrix.size
	val numCols = matrix[0].size
	val rowRegionSize = numRows / rowRegions
	val colRegionSize = numCols / colRegions

	val majorityValues = MutableList(rowRegions) { MutableList(colRegions) { 0 } }

	for (i in 0 until rowRegions) {
		for (j in 0 until colRegions) {
			val regionValues = mutableListOf<Int>()
			for (row in i * rowRegionSize until (i + 1) * rowRegionSize) {
				for (col in j * colRegionSize until (j + 1) * colRegionSize) {
					regionValues.add(matrix[row][col])
				}
			}
			majorityValues[i][j] = findMajorityValue(regionValues)
		}
	}

	return majorityValues
}

fun findMajorityValue(values: List<Int>): Int {
	val counts = mutableMapOf<Int, Int>()
	for (value in values) {
		counts[value] = (counts[value] ?: 0) + 1
	}
	return counts.maxByOrNull { it.value }?.key ?: 0
}

