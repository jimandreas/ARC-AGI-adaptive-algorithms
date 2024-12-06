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
fun changeMatrixColor(from:Int, to:Int, matrix: List<List<Int>>):List<List<Int>> {

	val newMatrix : MutableList<MutableList<Int>> = mutableListOf()
	for (row in 0 until matrix.size) {
		val newRow : MutableList<Int> = mutableListOf()
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

	val newMatrix : MutableList<MutableList<Int>> = mutableListOf()
	for (row in bb.minRow..bb.maxRow) {
		val newRow : MutableList<Int> = mutableListOf()
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
		matrix[startRow][startCol] == 0) {
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