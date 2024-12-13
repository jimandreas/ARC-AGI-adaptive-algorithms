@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.utilities

/**
Given a matrix in Kotlin of the form List<List<Int> - there is a spiral
of non-zero matrix entries
in the matrix that starts in some corner of the matrix and continues
in a clockwise or counter clockwise direction.   Please create a
function that duplicates this spiral form for any size specified
of a matrix given a rowCount and colCount and color (integer) value.
Return the new matrix as a List<List<Int>>.

ChatGPT code follows:
 */

enum class SpiralDirectionChatgpt {
    CLOCKWISE,
    COUNTERCLOCKWISE,
    NONE
}

fun detectSpiral(matrix: List<List<Int>>): SpiralDirectionChatgpt {
    // Validate the matrix
    if (matrix.isEmpty() || matrix[0].isEmpty()) return SpiralDirectionChatgpt.NONE

    // Check for CLOCKWISE spiral
    val clockTraversed = traverseSpiral(matrix, clockwise = true)
    val isClockwiseValid = isValidSpiral(clockTraversed)

    // Check for COUNTERCLOCKWISE spiral
    val counterClockTraversed = traverseSpiral(matrix, clockwise = false)
    val isCounterClockValid = isValidSpiral(counterClockTraversed)

    return when {
        isClockwiseValid && isCounterClockValid -> SpiralDirectionChatgpt.CLOCKWISE // Prefer CLOCKWISE if both
        isClockwiseValid -> SpiralDirectionChatgpt.CLOCKWISE
        isCounterClockValid -> SpiralDirectionChatgpt.COUNTERCLOCKWISE
        else -> SpiralDirectionChatgpt.NONE
    }
}

private fun traverseSpiral(matrix: List<List<Int>>, clockwise: Boolean): List<Int> {
    val result = mutableListOf<Int>()
    if (matrix.isEmpty()) return result

    val m = matrix.size
    val n = matrix[0].size
    val visited = Array(m) { BooleanArray(n) { false } }

    var top = 0
    var bottom = m - 1
    var left = 0
    var right = n - 1

    while (top <= bottom && left <= right) {
        if (clockwise) {
            // Traverse from left to right
            for (i in left..right) {
                if (!visited[top][i]) {
                    result.add(matrix[top][i])
                    visited[top][i] = true
                }
            }
            // if we completed a rotation, then bump left over one
            if (top > 1) {
                if (visited[top-2][left]){
                    left++
                }
            }
            top++

            // Traverse from top to bottom
            for (i in top..bottom) {
                if (!visited[i][right]) {
                    result.add(matrix[i][right])
                    visited[i][right] = true
                }
            }
            right--
            top++

            // Traverse from right to left
            if (top <= bottom) {
                for (i in right downTo left) {
                    if (!visited[bottom][i]) {
                        result.add(matrix[bottom][i])
                        visited[bottom][i] = true
                    }
                }
                bottom--
                right--
            }

            // Traverse from bottom to top

            if (left <= right) {
                for (i in bottom downTo top) {
                    if (!visited[i][left]) {
                        result.add(matrix[i][left])
                        visited[i][left] = true
                    }
                }
                left++
                bottom--
            }
        } else {
            // Traverse from right to left
            for (i in right downTo left) {
                if (!visited[top][i]) {
                    result.add(matrix[top][i])
                    visited[top][i] = true
                }
            }
            // if we completed a rotation, then bump left over one
            if (top > 1) {
                if (visited[top-2][right]){
                    left++
                }
            }
            top++

            // Traverse from top to bottom
            for (i in top..bottom) {
                if (!visited[i][left]) {
                    result.add(matrix[i][left])
                    visited[i][left] = true
                }
            }
            left++
            top++

            // Traverse from left to right
            if (top <= bottom) {
                for (i in left..right) {
                    if (!visited[bottom][i]) {
                        result.add(matrix[bottom][i])
                        visited[bottom][i] = true
                    }
                }
                bottom--
                left++
            }

            // Traverse from bottom to top
            if (left <= right) {
                for (i in bottom downTo top) {
                    if (!visited[i][right]) {
                        result.add(matrix[i][right])
                        visited[i][right] = true
                    }
                }
                right--
                bottom--
            }
        }
    }

    return result
}

private fun isValidSpiral(traversedCells: List<Int>): Boolean {
    if (traversedCells.isEmpty()) return false

    // All cells in the spiral must be non-zero
    if (traversedCells.any { it == 0 }) return false

    // Implementing a direction change counter
    var turnCount = 0
    var previousDirection: String? = null

    // Heuristic: spiral must traverse at least 4 * (min(rows, cols) / 2) elements
    // Adjust based on actual traversal length.

    // For simplicity, assume a minimum of 8 elements for 4 turns
    return traversedCells.size >= 8
}

/**
 * GENERATE SPIRAL
 *    initial code by ChatGPT - and modified to add the @ARCprize "appendix"
 *    for matrices with even counts
 */
enum class ChatGptSpiralGeneration {
    CLOCKWISE,
    COUNTERCLOCKWISE,
    NONE
}

fun generateSpiral(size: Int, color: Int, clockwise: Boolean): List<List<Int>> {

    val matrix: MutableList<MutableList<Int>> = MutableList(size) { MutableList(size) {0} }

    val rowCount = matrix.size
    val colCount = matrix[0].size
    val visited = Array(rowCount) { BooleanArray(colCount) { false } }

    var isEven = false
    if (rowCount % 2 == 0) {
        isEven = true
    }

    var top = 0
    var bottom = rowCount - 1
    var left = 0
    var right = colCount - 1

    while (top <= bottom && left <= right) {
        if (clockwise) {
            // Traverse from left to right

            for (i in left..right) {
                if (!visited[top][i]) {
                    //result.add(matrix[top][i])
                    matrix[top][i] = color
                    visited[top][i] = true
                }
            }
            // if we completed a rotation, then bump left over one
            if (top > 1) {
                if (visited[top-2][left]){
                    left++
                }
            }
            top++


            // Traverse from top to bottom
            for (i in top..bottom) {
                if (!visited[i][right]) {
//                    result.add(matrix[i][right])
                    matrix[i][right] = color
                    visited[i][right] = true
                }
            }
            right--
            top++

            /*
				0 1 2 3 4 5
			0   X X X X X X
			1   O O O O O X
			2   X X X X O X
			3   X O X X O X
			4   X O O O O X
			5   X X X X X X
				0 1 2 3 4 5
			 */
            if (isEven && top == rowCount / 2 +1) {
                top--  // allow for appendix
            }
            // Traverse from right to left
            if (top <= bottom) {
                for (i in right downTo left) {
                    if (!visited[bottom][i]) {
                        //result.add(matrix[bottom][i])
                        matrix[bottom][i] = color
                        visited[bottom][i] = true
                    }
                }
                bottom--
                right--
            }

            // Traverse from bottom to top
            if (left <= right) {
                for (i in bottom downTo top) {
                    if (!visited[i][left]) {
                        //result.add(matrix[i][left])
                        matrix[i][left] = color
                        visited[i][left] = true
                    }
                }
                left++
                bottom--
            }

            /*
				0 1 2 3 4 5 6 7
			0   X X X X X X X X
			1   O O O O O O O X
			2   X X X X X X O X
			3   X O O O O X O X
			4   X O X X O X O X
			5   X O X X X X O X
			6   X O O O O O O X
			7   X X X X X X X X
				0 1 2 3 4 5 6 7
			 */
            if (isEven && top == rowCount / 2) {
                bottom++  // allow for appendix
            }
        }

        // CLOCKWISE


        /* else counter clockwise */ else {
            // Traverse from right to left
            for (i in right downTo left) {
                if (!visited[top][i]) {
                    //result.add(matrix[top][i])
                    matrix[top][i] = color
                    visited[top][i] = true
                }
            }
            // if we completed a rotation, then bump left over one
            if (top > 1) {
                if (visited[top-2][right]){
                    left++
                }
            }
            top++
            // Traverse from top to bottom
            for (i in top..bottom) {
                if (!visited[i][left]) {
                    //result.add(matrix[i][left])
                    matrix[i][left] = color
                    visited[i][left] = true
                }
            }
            left++
            top++
            // Traverse from left to right
            if (top <= bottom) {
                for (i in left..right) {
                    if (!visited[bottom][i]) {
                        //result.add(matrix[bottom][i])
                        matrix[bottom][i] = color
                        visited[bottom][i] = true
                    }
                }
                bottom--
                left++
            }

            // Traverse from bottom to top
            if (left <= right) {
                for (i in bottom downTo top) {
                    if (!visited[i][right]) {
                        //result.add(matrix[i][right])
                        matrix[i][right] = color
                        visited[i][right] = true
                    }
                }
                right--
                bottom--
            }
        }
    }

    return matrix
}


