@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.utilities

import Block
import BoundingBox
import Point
import kotlin.math.max
import kotlin.math.min

/**
simply change cells with the "from" value to the "to" value in the
matrix and return the new matrix.
 */
fun changeMatrixColor(from: Int, to: Int, matrix: List<List<Int>>): List<List<Int>> {

	val newMatrix: MutableList<MutableList<Int>> = mutableListOf()
	for (row in 0 until matrix.size) {
		val newRow: MutableList<Int> = mutableListOf()
		for (col in 0 until matrix[0].size) {
			if (matrix[row][col] == from) {
				newRow.add(to)
			} else {
				newRow.add(matrix[row][col])
			}
		}
		newMatrix.add(newRow)
	}
	return newMatrix
}


/**
In Kotlin a set of Blocks and Points are defined as below.   Compute the
minRow, minCol and maxRow and maxCol over all the Pair<Int, Int> row and
column coordinates.
Return the bounding box information in the data class BoundingBox as given below.
Gemini code follows:
 */

fun computeBoundingBox(blocks: List<Block>, points: List<Point>): BoundingBox {
	var minRow = Int.MAX_VALUE
	var minCol = Int.MAX_VALUE
	var maxRow = Int.MIN_VALUE
	var maxCol = Int.MIN_VALUE

	for (block in blocks) {
		for ((row, col) in block.coordinates) {
			minRow = min(minRow, row)
			minCol = min(minCol, col)
			maxRow = max(maxRow, row)
			maxCol = max(maxCol, col)
		}
	}

	for (point in points) {
		val (row, col) = point.coordinate
		minRow = min(minRow, row)
		minCol = min(minCol, col)
		maxRow = max(maxRow, row)
		maxCol = max(maxCol, col)
	}

	return BoundingBox(minRow, minCol, maxRow, maxCol)
}

// return the interior matrix given by the BoundingBox.
//    return emptyList if something is messed up.
fun returnSubmatrix(matrix: List<List<Int>>, bb: BoundingBox): List<List<Int>> {
	val rowSize = matrix.size
	val colSize = matrix[0].size

	if (bb.maxRow > rowSize || bb.maxCol > colSize) {
		return emptyList()
	}

	val newMatrix: MutableList<MutableList<Int>> = mutableListOf()
	for (row in bb.minRow..bb.maxRow) {
		val newRow: MutableList<Int> = mutableListOf()
		for (col in bb.minCol..bb.maxCol) {
			newRow.add(matrix[row][col])
		}
		newMatrix.add(newRow)
	}
	return newMatrix

}

/**
The row and col coordinates of a point to small region of non-zero values in
a matrix in the Kotlin form of List<List<Int>>.
Please create a function that begins with the point provided
and then finds the extent of this non-zero
region and returns this sub-matrix in the form of List<List<Int>>

GROK code follows
 */
fun findNonZeroRegionFromPoint(matrix: List<List<Int>>, startRow: Int, startCol: Int): List<List<Int>> {
	if (matrix.isEmpty() || matrix[0].isEmpty() ||
		startRow < 0 || startRow >= matrix.size ||
		startCol < 0 || startCol >= matrix[0].size ||
		matrix[startRow][startCol] == 0
	) {
		return emptyList()
	}

	val rows = matrix.size
	val cols = matrix[0].size
	val visited = Array(rows) { BooleanArray(cols) }
	var minRow = rows
	var maxRow = 0
	var minCol = cols
	var maxCol = 0

	// Depth-first search to find all connected non-zero elements
	fun dfs(row: Int, col: Int) {
		if (row < 0 || col < 0 || row >= rows || col >= cols || visited[row][col] || matrix[row][col] == 0) {
			return
		}

		visited[row][col] = true
		minRow = minOf(minRow, row)
		maxRow = maxOf(maxRow, row)
		minCol = minOf(minCol, col)
		maxCol = maxOf(maxCol, col)

		dfs(row + 1, col)   // Down
		dfs(row - 1, col)   // Up
		dfs(row, col + 1)   // Right
		dfs(row, col - 1)   // Left
	}

	// Start DFS from the given point
	dfs(startRow, startCol)

	// If no non-zero elements were found (shouldn't happen here but included for robustness)
	if (minRow > maxRow || minCol > maxCol) {
		return emptyList()
	}

	// Extract the sub-matrix
	return matrix.subList(minRow, maxRow + 1).map { row ->
		row.subList(minCol, maxCol + 1)
	}
}

/**
Please examine the matrix provided in Kotlin by List<List<Int>>
and return true of the matrix contents have right to left symmetry -
that is - the left side of the matrix is a mirror image of the right side.
GROK code follows
 */

fun isMatrixSymmetric(matrix: List<List<Int>>): Boolean {
	// Check if matrix is empty or not square
	if (matrix.isEmpty() || matrix[0].isEmpty()) return false
	val rows = matrix.size
	val cols = matrix[0].size

	// Only square matrices can have right-left symmetry
	//if (rows != cols) return false

	// Compare elements from left to right with right to left
	for (i in 0 until rows) {
		for (j in 0 until cols / 2) { // Only need to check half the width
			if (matrix[i][j] != matrix[i][cols - 1 - j]) {
				return false
			}
		}
	}
	return true
}

/**
 * invert matrix color
 *    assume two colors - some color and zero
 *    if another color is found error with empty list
 *    invert the color with 0 and return the inverted matrix
 *
 *    Clumsy code by Jim follows:
 */
fun invertMatrix(m: List<List<Int>>): List<List<Int>> {
	if (m.isEmpty()) {
		return m
	}
	val rowCount = m.size
	val colCount = m[0].size
	val colMap = m.flatMap { it }.toSortedSet()
	if (colMap.size != 2) {
		return emptyList()
	}
	var theColor = 0
	val foo = colMap.first()
	if (foo == 0) {
		theColor = colMap.elementAt(1)
	}

	val retList: MutableList<MutableList<Int>> = mutableListOf()
	for (row in 0 until rowCount) {
		val rowList: MutableList<Int> = mutableListOf()
		for (col in 0 until colCount) {
			if (m[row][col] == 0) {
				rowList.add(theColor)
			} else {
				rowList.add(0)
			}
		}
		retList.add(rowList)
	}
	return retList
}

/**
 * invert matrix with specified colors
 *    given two colors, swap them in the matrix
 *    if another color is found error with empty list
 *    invert the color with 0 and return the inverted matrix
 *
 *    Clumsy code by Jim follows:
 */
fun invertMatrixWithColorsSpecified(m: List<List<Int>>, c1:Int, c2: Int): List<List<Int>> {
	if (m.isEmpty()) {
		return m
	}
	val rowCount = m.size
	val colCount = m[0].size
	val colMap = m.flatMap { it }.toSortedSet()
	if (colMap.size != 2) {
		return emptyList()
	}

	val retList: MutableList<MutableList<Int>> = mutableListOf()
	for (row in 0 until rowCount) {
		val rowList: MutableList<Int> = mutableListOf()
		for (col in 0 until colCount) {
			if (m[row][col] == c1) {
				rowList.add(c2)
			} else {
				if (m[row][col] == c2) {
					rowList.add(c1)
				} else {
					return emptyList()
				}
			}
		}
		retList.add(rowList)
	}
	return retList
}

/**
I have a Kotlin matrix in the form of List<List<Int>>.
In the matrix there are some non-zero values that
form a column that go from the top of the matrix to the bottom.
These indicate partitions in the matrix.
Please create a function that breaks up the matrix into
partitions and returns a List<List<List<Int>>>
of the partitions.
GROK code follows:
 */
fun partitionMatrix(
	matrix: List<List<Int>>,
	colorSpecified: Boolean = false,
	partitionColor: Int = 0
): List<List<List<Int>>> {
	if (matrix.isEmpty() || matrix[0].isEmpty()) {
		return emptyList()
	}

	val rows = matrix.size
	val cols = matrix[0].size
	val partitions = mutableListOf<List<List<Int>>>()
	var startCol = 0

	for (col in 0 until cols) {
		// Check if this column is a partition (i.e., all elements are non-zero)
		if (colorSpecified) {
			if (matrix.all { it[col] != 0 && it[col] == partitionColor }) {
				if (col > startCol) {
					// Extract the partition from startCol to col-1
					val partition = matrix.map { it.subList(startCol, col) }
					partitions.add(partition)
				}
				// Move startCol to the next column after this partition
				startCol = col + 1
			}
		} else {
			if (matrix.all { it[col] != 0 }) {
				if (col > startCol) {
					// Extract the partition from startCol to col-1
					val partition = matrix.map { it.subList(startCol, col) }
					partitions.add(partition)
				}
				// Move startCol to the next column after this partition
				startCol = col + 1
			}
		}
	}

	// Add the last partition if there's any data after the last partition column
	if (startCol < cols) {
		val lastPartition = matrix.map { it.subList(startCol, cols) }
		partitions.add(lastPartition)
	}

	return partitions
}

/**
 * in Kotlin I have a List<List<Int>> - please provide the first non-zero Int in the list
 */
fun findFirstNonZero(matrix: List<List<Int>>): Int? {
	for (row in matrix) {
		for (value in row) {
			if (value != 0) {
				return value
			}
		}
	}
	return null // If no non-zero number is found
}

/**
Two matrices of List<List<Int>> and List<List<Int>> and a returnColor Int
are passed to a function.  Please check
that the matrices are the same size.  Then return a matrix List<List<Int>>
with a returnColor value for each matrix position where the
two matrices have a matching value.  Return the emptyList() for any errors.
GROK code follows:
 */

fun compareMatricesAndReturnMatchingPoints(
	matrix1: List<List<Int>>,
	matrix2: List<List<Int>>,
	returnColor: Int
): List<List<Int>> {
	// Check if matrices are of the same size
	if (matrix1.size != matrix2.size || matrix1.isEmpty()) {
		return emptyList()
	}
	for (i in matrix1.indices) {
		if (matrix1[i].size != matrix2[i].size) {
			return emptyList()
		}
	}

	// Create a new matrix with matching values replaced by returnColor

	return matrix1.mapIndexed { i, row ->
		row.mapIndexed { j, value ->
			if (value != 0 && value == matrix2[i][j]) returnColor else 0
		}
	}

//	val retList : MutableList<List<Int>> = mutableListOf()
//	for (row in 0 until matrix1.size) {
//		val rowList : MutableList<Int> = mutableListOf()
//		for (col in 0 until matrix1[0].size) {
//			if (matrix1[row][col] == matrix2[row][col]) {
//				rowList.add(returnColor)
//			} else {
//				rowList.add(0)
//			}
//		}
//		retList.add(rowList)
//	}
//	return retList
}

/**
Two blocks are defined in a List<List<Int>> Kotlin matrix as given below.
Please create a function that uses recursion to determine if a connection
between the two blocks exists in the matrix - the connection is defined
by non-zero points of the same value that trace a path between
the two blocks.  Diagonal steps are not allowed, only orthogonal
steps are valid.

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)

GROK code follows:
 */
fun connectionBetweenTwoBlocks(matrix: List<List<Int>>, block1: Block, block2: Block): Boolean {
	// Helper function to check if coordinates are within the matrix bounds
	fun isValid(x: Int, y: Int) = x in matrix.indices && y in matrix[0].indices

	// Recursive function to check for connection
	fun dfs(current: Pair<Int, Int>, visited: Set<Pair<Int, Int>>): Boolean {
		if (current in block2.coordinates) return true

		for ((dx, dy) in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) { // Orthogonal moves
			val next = Pair(current.first + dx, current.second + dy)
			if (isValid(next.first, next.second) &&
				//matrix[next.first][next.second] == block1.color &&
				matrix[next.first][next.second] != 0 &&
				next !in visited
			) {

				if (dfs(next, visited + next)) {
					return true
				}
			}
		}
		return false
	}

	// Check if blocks have the same color
	if (block1.color != block2.color) return false

	// Try from each coordinate of block1
	for (coord in block1.coordinates) {
		if (dfs(coord, setOf(coord))) {
			return true
		}
	}
	return false
}

/**
 *
 */
fun isSquare(coords: Set<Pair<Int, Int>>): Boolean {

	if (coords.size != 4 || coords.toSet().size != 4) return false

	val minX = coords.minOf { it.first }
	val maxX = coords.maxOf { it.first }
	val minY = coords.minOf { it.second }
	val maxY = coords.maxOf { it.second }

	if (coords.contains(Pair(minX + 1, minY))) {
		if (coords.contains(Pair(minX, minY + 1))) {
			return true
		}
	}
	return false

}

/**
A function is passed in two Kotlin arrays in the form List<List<Int>>.
Compare the two arrays and return the two colors that are swapped between
the lists as a Pair.

Gemini code follows:
 */
fun findSwappedColors(array1: List<List<Int>>, array2: List<List<Int>>): List<Pair<Int, Int>> {
	if (array1.size != array2.size || array1[0].size != array2[0].size) {
		return emptyList()
	}

	var firstColor = -1
	var secondColor = -1

	for (i in array1.indices) {
		for (j in array1[i].indices) {
			if (array1[i][j] != array2[i][j]) {
				if (firstColor == -1) {
					firstColor = array1[i][j]
					secondColor = array2[i][j]
				} else if ((firstColor == array2[i][j] && secondColor == array1[i][j])
					|| (secondColor == array2[i][j] && firstColor == array1[i][j]))
				{
					return listOf(Pair(firstColor, secondColor))
				} else {
					return emptyList()
				}
			}
		}
	}

	if (firstColor == -1) {
		return emptyList()
	}

	return listOf(Pair(firstColor, secondColor))
}

fun mirrorMatrixVertically(matrix: List<List<Int>>): List<List<Int>> {
	return matrix.reversed()
}

fun mirrorMatrixHorizontally(matrix: List<List<Int>>): List<List<Int>> {
	return matrix.map { row -> row.reversed() }
}

/**
Please create a Kotlin matrix in the form of List<List<Int> of
size rowSize and colSize and fill the matrix vertically (columnwise)
with the colors in List<Int> - assume the size of List<Int> is the
same as colSize.  Return the matrix.
 GROK code follows:
 */
fun createVerticalMatrixWithColors(
	rowSize: Int, colSize: Int,
	colors: List<Int>): List<List<Int>> {

	// Check if the size of colors matches colSize
	if (colors.size != colSize) {
		return emptyList()
	}

	// Initialize the matrix with zeros
	val matrix = MutableList(rowSize) { MutableList(colSize) { 0 } }

	// Fill the matrix column-wise
	for (col in 0 until colSize) {
		for (row in 0 until rowSize) {
			matrix[row][col] = colors[col]
		}
	}

	return matrix
}

