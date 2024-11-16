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

	data class TaskCoordinateData(
		val train: List<MatrixDataInputAndOutput>,
		val test: List<MatrixDataInputAndOutput>,
		var name: String = ""
	)

	data class MatrixDataInputAndOutput(
		val input: List<List<Int>>,
		val output: List<List<Int>>
	)

	fun findRectangularHoles(matrix: List<List<Int>>): List<Set<Pair<Int, Int>>> {
		val rows = matrix.size
		val cols = matrix.firstOrNull()?.size ?: 0
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

		fun isRectangular(hole: Set<Pair<Int, Int>>): Boolean {
			val minRow = hole.minOf { it.first }
			val maxRow = hole.maxOf { it.first }
			val minCol = hole.minOf { it.second }
			val maxCol = hole.maxOf { it.second }

			for (r in minRow..maxRow) {
				for (c in minCol..maxCol) {
					if (r to c !in hole) {
						return false
					}
				}
			}
			return true
		}

		for (row in 0 until rows) {
			for (col in 0 until cols) {
				if (isValid(row, col)) {
					val hole = exploreHole(row, col)
					if (isRectangular(hole)) {
						holes.add(hole)
					}
				}
			}
		}

		return holes
	}

}