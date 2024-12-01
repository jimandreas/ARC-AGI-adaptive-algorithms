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