@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package experiments

class RectangularHoleFinder {
	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
	}

	// This is version 2 of the hole finder from Google Gemini
	//   Plus debugging and refinement added.
	//   The hole must be COMPLETELY SURROUNDED by non-zero values!

	fun findRectangularHoles(matrix: List<List<Int>>): List<Set<Pair<Int, Int>>> {
		val rows = matrix.size
		val cols = matrix[0].size

		// the matrix must be at least a 3x3 in size to surround a hole!
		if ((rows < 3) || (cols < 3)) {
			return(listOf())
		}

		val visited = mutableSetOf<Pair<Int, Int>>()
		val holes = mutableListOf<Set<Pair<Int, Int>>>()

		fun isValid(row: Int, col: Int): Boolean {
			return row in 0 until rows && col in 0 until cols && matrix[row][col] == 0 && (row to col) !in visited
		}

		fun exploreHole(row: Int, col: Int): Set<Pair<Int, Int>> {
			val hole = mutableSetOf<Pair<Int, Int>>()
			val queue = ArrayDeque<Pair<Int, Int>>()
			queue.add(row to col)
			visited.add(row to col)

			while (queue.isNotEmpty()) {
				val (r, c) = queue.removeFirst()
				hole.add(r to c)

				// Check adjacent cells
				for ((dr, dc) in listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)) {
					val newRow = r + dr
					val newCol = c + dc
					if (isValid(newRow, newCol)) {
						queue.add(newRow to newCol)
						visited.add(newRow to newCol)
					}
				}
			}

			return hole
		}

		fun isEnclosedRectangular(hole: Set<Pair<Int, Int>>): Boolean {
			val minRow = hole.minOf { it.first }
			val maxRow = hole.maxOf { it.first }
			val minCol = hole.minOf { it.second }
			val maxCol = hole.maxOf { it.second }

			// Check top and bottom edges
			for (c in minCol..maxCol) {
				if (minRow > 0 && matrix[minRow - 1][c] == 0 ||
					maxRow < rows - 1 && matrix[maxRow + 1][c] == 0) {
					return false
				}
			}

			// Check left and right edges
			for (r in minRow..maxRow) {
				if (minCol > 0 && matrix[r][minCol - 1] == 0 ||
					maxCol < cols - 1 && matrix[r][maxCol + 1] == 0) {
					return false
				}
			}

			// Check if all cells within the bounding box are in the hole
			for (r in minRow..maxRow) {
				for (c in minCol..maxCol) {
					if (r to c !in hole) {
						return false
					}
				}
			}

			return true
		}

		for (row in 1 until rows -1) {
			for (col in 1 until cols-1) {
				if (isValid(row, col)) {
					val hole = exploreHole(row, col)
					if (isEnclosedRectangular(hole)) {
						holes.add(hole)
					}
				}
			}
		}

		return holes
	}

}