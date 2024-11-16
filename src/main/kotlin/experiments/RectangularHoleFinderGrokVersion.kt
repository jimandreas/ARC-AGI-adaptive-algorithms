@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package experiments

class RectangularHoleFinderGrokVersion {
	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
	}

	/*

		fun findEnclosedRectangle(matrix: Array<IntArray>): String {
			val rows = matrix.size
			val cols = matrix[0].size

			fun isNonZero(cell: Int) = cell != 0

			// Scan for regions
			for (i in 0 until rows) {
				for (j in 0 until cols) {
					if (isNonZero(matrix[i][j])) {
						val startRegionRow = i
						val startRegionCol = j
						var top = startRegionRow
						var bottom = startRegionRow
						var left = startRegionCol
						var right = startRegionCol

						// Expand to find the full extent of the region
						while (top > 0 && isNonZero(matrix[top-1][j])) top--
						while (bottom < rows - 1 && isNonZero(matrix[bottom+1][j])) bottom++
						while (left > 0 && isNonZero(matrix[i][left-1])) left--
						while (right < cols - 1 && isNonZero(matrix[i][right+1])) right++

						// Now check for an enclosed rectangle within this region
						for (r in top..bottom) {
							for (c in left..right) {
								if (isNonZero(matrix[r][c])) {
									// Check if this cell could be part of an enclosed rectangle
									if (r < bottom && c < right) {
										var innerTop = r
										var innerBottom = r
										var innerLeft = c
										var innerRight = c

										// Expand to find inner rectangle
										while (innerTop < bottom && isNonZero(matrix[innerTop+1][c])) innerTop++
										while (innerLeft < right && isNonZero(matrix[r][innerLeft+1])) innerLeft++
										while (innerBottom > r && isNonZero(matrix[innerBottom-1][c])) innerBottom--
										while (innerRight > c && isNonZero(matrix[r][innerRight-1])) innerRight--

										// Verify if it's an enclosed rectangle
										if (innerTop > top || innerBottom < bottom ||
											innerLeft > left || innerRight < right) {
											// This condition means we found an enclosed rectangle
											return "Enclosed rectangle found at: ($innerTop, $innerLeft) to ($innerBottom, $innerRight)"
										}
									}
								}
							}
						}
					}
				}
			}
			return "No enclosed rectangle found"
		}
	*/

	fun findEnclosedRectangle(matrix: List<List<Int>>): String {
		val rows = matrix.size
		val cols = matrix[0].size

		fun isNonZero(cell: Int) = cell != 0

		// Scan for regions
		for (i in 0 until rows) {
			for (j in 0 until cols) {
				if (isNonZero(matrix[i][j])) {
					val startRegionRow = i
					val startRegionCol = j
					var top = startRegionRow
					var bottom = startRegionRow
					var left = startRegionCol
					var right = startRegionCol

					// Expand to find the full extent of the region
					while (top > 0 && isNonZero(matrix[top - 1][j])) top--
					while (bottom < rows - 1 && isNonZero(matrix[bottom + 1][j])) bottom++
					while (left > 0 && isNonZero(matrix[i][left - 1])) left--
					while (right < cols - 1 && isNonZero(matrix[i][right + 1])) right++

					// Now check for an enclosed rectangle within this region
					for (r in top..bottom) {
						for (c in left..right) {
							if (isNonZero(matrix[r][c])) {
								// Check if this cell could be part of an enclosed rectangle
								if (r < bottom && c < right) {
									var innerTop = r
									var innerBottom = r
									var innerLeft = c
									var innerRight = c

									// Expand to find inner rectangle
									while (innerTop < bottom && isNonZero(matrix[innerTop + 1][c])) innerTop++
									while (innerLeft < right && isNonZero(matrix[r][innerLeft + 1])) innerLeft++
									while (innerBottom > r && isNonZero(matrix[innerBottom - 1][c])) innerBottom--
									while (innerRight > c && isNonZero(matrix[r][innerRight - 1])) innerRight--

									// Verify if it's an enclosed rectangle
									if (innerTop > top || innerBottom < bottom ||
										innerLeft > left || innerRight < right
									) {
										// This condition means we found an enclosed rectangle
										return "Enclosed rectangle found at: ($innerTop, $innerLeft) to ($innerBottom, $innerRight)"
									}
								}
							}
						}
					}
				}
			}
		}
		return "No enclosed rectangle found"
	}
}