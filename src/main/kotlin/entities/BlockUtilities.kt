@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "KotlinConstantConditions"
)

package entities

import Block
import MatrixAbstractions
import Point
import kotlin.collections.List

class BlockUtilities {

	// see GeminiBlockFinder.md

	fun findConnectedBlocksInMatrix(
		oneTrainInstance: MatrixAbstractions, scanDiagonals: Boolean = false) {

		val matrix = oneTrainInstance.matrix
		val blocks = mutableListOf<Block>()
		val numRows = matrix.size
		val numCols = matrix[0].size
		val visited = mutableSetOf<Pair<Int, Int>>()

		for (row in 0 until numRows) {
			for (col in 0 until numCols) {
				if (matrix[row][col] != 0 && Pair(row, col) !in visited) {
					val blockCoordinates = mutableSetOf<Pair<Int, Int>>()
					val targetValue = matrix[row][col]
					exploreBlock(matrix,
						row, col,
						targetValue,
						blockCoordinates,
						visited,
						scanDiagonals)
					if (blockCoordinates.size > 1) { // Only add if it's a block (more than one cell)
						val b = Block(color = targetValue,
							coordinates = blockCoordinates)
						oneTrainInstance.blocks.add(b)
						continue
					}
					// this is a point
					val p = Point(color = targetValue,
						coordinate = Pair(row, col))
					oneTrainInstance.points.add(p)
				}
			}
		}
	}

	private fun exploreBlock(
		matrix: List<List<Int>>,
		row: Int,
		col: Int,
		targetValue: Int,
		blockCoordinates: MutableSet<Pair<Int, Int>>,
		visited: MutableSet<Pair<Int, Int>>,
		scanDiagonals: Boolean
	) {
		val numRows = matrix.size
		val numCols = matrix[0].size

		if (row in 0 until numRows &&
			col in 0 until numCols &&
			matrix[row][col] == targetValue &&
			Pair(row, col) !in visited
		) {
			visited.add(Pair(row, col))
			blockCoordinates.add(Pair(row, col))

			exploreBlock(matrix, row + 1, col, targetValue, blockCoordinates, visited, scanDiagonals)
			exploreBlock(matrix, row - 1, col, targetValue, blockCoordinates, visited, scanDiagonals)
			exploreBlock(matrix, row, col + 1, targetValue, blockCoordinates, visited, scanDiagonals)
			exploreBlock(matrix, row, col - 1, targetValue, blockCoordinates, visited, scanDiagonals)

			if (scanDiagonals) {
				exploreBlock(matrix, row + 1, col + 1, targetValue, blockCoordinates, visited, scanDiagonals)
				exploreBlock(matrix, row - 1, col - 1, targetValue, blockCoordinates, visited, scanDiagonals)
				exploreBlock(matrix, row + 1, col - 1, targetValue, blockCoordinates, visited, scanDiagonals)
				exploreBlock(matrix, row - 1, col + 1, targetValue, blockCoordinates, visited, scanDiagonals)
			}

		}
	}

	/**
	 * Good - please create a function that takes the ouput of this function and
	 * verifies that the block is rectangular.
	 *
	 * Explanation:
	 *
	 * Handle empty set: If the input blockCoordinates is empty, it returns false because an empty set cannot form a rectangle.
	 * Find boundaries: It calculates the minimum and maximum row and column values from the set of coordinates. This defines the bounding box of the potential rectangle.
	 * Iterate and check: It iterates through all the cells within the calculated bounding box. If any cell within these boundaries is not present in the blockCoordinates set, it means the shape is not a complete rectangle, so it returns false.
	 * Return true: If all cells within the bounds are found in the set, it means the block is rectangular, and the function returns true.
	 * How to use it with the previous function:
	 */

	fun verifyRectangularBlock(coordinates: Set<Pair<Int, Int>>): Boolean {
		if (coordinates.isEmpty()) return true

		val minX = coordinates.minOf { it.first }
		val maxX = coordinates.maxOf { it.first }
		val minY = coordinates.minOf { it.second }
		val maxY = coordinates.maxOf { it.second }

		// Calculate the expected number of coordinates
		val expectedSize = (maxX - minX + 1) * (maxY - minY + 1)

		// Check if the set has the expected number of coordinates
		if (coordinates.size != expectedSize) {
			return false
		}

		// Check if all coordinates within the boundaries exist in the set
		for (x in minX..maxX) {
			for (y in minY..maxY) {
				if (Pair(x, y) !in coordinates) {
					return false
				}
			}
		}

		return true
	}


	fun isBlockHollow(matrix: List<List<Int>>, blockCoordinates: Set<Pair<Int, Int>>): Boolean {
		if (blockCoordinates.size < 4) return false // A block needs at least 4 cells to have an interior

		val minRow = blockCoordinates.minOf { it.first }
		val maxRow = blockCoordinates.maxOf { it.first }
		val minCol = blockCoordinates.minOf { it.second }
		val maxCol = blockCoordinates.maxOf { it.second }

		val targetValue = matrix[minRow][minCol] // Get the value of the block

		for (row in minRow + 1 until maxRow) { // Check interior rows
			for (col in minCol + 1 until maxCol) { // Check interior columns
				if (matrix[row][col] != targetValue) {
					return true // Found a different value inside, so it's hollow
				}
			}
		}

		return false // No different value found inside, so it's solid
	}
}