@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

package entities

class BlockUtilities {

    /*
    Explanation:

findRectangularBlocks(matrix) function:

Initializes an empty list blocks to store the rectangular blocks found.
Creates a visited set to keep track of visited cells.
Iterates through the matrix, looking for unvisited non-zero cells.
For each unvisited non-zero cell, it calls exploreBlock to find the connected block of cells with the same value.
Adds the block coordinates to the blocks list if the block has more than one cell.
exploreBlock(matrix, row, col, targetValue, blockCoordinates, visited) function:

Takes the matrix, starting cell coordinates, the target value, a set to store block coordinates, and a set to track visited cells.
Recursively explores connected cells that have the same targetValue.
Adds the coordinates of the valid cells to the blockCoordinates set.
The base case for recursion is when the cell is out of bounds, has a different value, or has already been visited.
Example Usage:

Kotlin
fun main() {
    val matrix = listOf(
        listOf(0, 1, 1, 0, 0),
        listOf(0, 1, 1, 0, 0),
        listOf(0, 0, 0, 2, 2),
        listOf(0, 0, 2, 2, 0),
        listOf(0, 0, 0, 0, 0)
    )

    val rectangularBlocks = findRectangularBlocks(matrix)
    for (coordinates in rectangularBlocks) {
        println("Coordinates: $coordinates")
    }
}
Use code with caution.

Output:

Coordinates: [(0, 1), (0, 2), (1, 1), (1, 2)]
Coordinates: [(2, 3), (2, 4), (3, 2), (3, 3), (3, 4)]
     */

    fun findRectangularBlocks(matrix: List<List<Int>>): List<Set<Pair<Int, Int>>> {
        val blocks = mutableListOf<Set<Pair<Int, Int>>>()
        val numRows = matrix.size
        val numCols = matrix[0].size
        val visited = mutableSetOf<Pair<Int, Int>>()

        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                if (matrix[row][col] != 0 && Pair(row, col) !in visited) {
                    val blockCoordinates = mutableSetOf<Pair<Int, Int>>()
                    exploreBlock(matrix, row, col, matrix[row][col], blockCoordinates, visited)
                    if (blockCoordinates.size > 1) { // Only add if it's a block (more than one cell)
                        blocks.add(blockCoordinates)
                    }
                }
            }
        }

        return blocks
    }

    fun exploreBlock(
        matrix: List<List<Int>>,
        row: Int,
        col: Int,
        targetValue: Int,
        blockCoordinates: MutableSet<Pair<Int, Int>>,
        visited: MutableSet<Pair<Int, Int>>
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

            exploreBlock(matrix, row + 1, col, targetValue, blockCoordinates, visited)
            exploreBlock(matrix, row - 1, col, targetValue, blockCoordinates, visited)
            exploreBlock(matrix, row, col + 1, targetValue, blockCoordinates, visited)
            exploreBlock(matrix, row, col - 1, targetValue, blockCoordinates, visited)

            exploreBlock(matrix, row + 1, col + 1, targetValue, blockCoordinates, visited)
            exploreBlock(matrix, row - 1, col - 1, targetValue, blockCoordinates, visited)
            exploreBlock(matrix, row + 1, col - 1, targetValue, blockCoordinates, visited)
            exploreBlock(matrix, row - 1, col + 1, targetValue, blockCoordinates, visited)
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

    fun verifyRectangularBlock(blockCoordinates: Set<Pair<Int, Int>>): Boolean {
        if (blockCoordinates.isEmpty()) return false // Empty set is not rectangular

        val minRow = blockCoordinates.minOf { it.first }
        val maxRow = blockCoordinates.maxOf { it.first }
        val minCol = blockCoordinates.minOf { it.second }
        val maxCol = blockCoordinates.maxOf { it.second }

        for (row in minRow..maxRow) {
            for (col in minCol..maxCol) {
                if (Pair(row, col) !in blockCoordinates) {
                    return false // If any cell within the bounds is missing, it's not rectangular
                }
            }
        }

        return true // All cells within the bounds are present, so it's rectangular
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