# Google Gemini Code

## Block finder

The Gemini prompt:

**Please assume kotlin. A matrix is given in the form of integers in List<List<Int>>. Therefore it is a two dimensional matrix.  Assume an int with value of zero is a background. The desired function returns a set of (row, column) pairs.  The function should scan the matrix and return regions that define a rectangular block of identical values.  The pairs defining the block should have all the identical value.  The boundaries of the block can be the edge of the matrix or cells with other values than that of the blocks.**

```
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
    }
}
```

## Verification function

The Gemini Prompt:

**Good - please create a function that takes the ouput of this function and verifies that the block is rectangular.**

```
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
```

Use code with caution.

Explanation:

Handle empty set: If the input blockCoordinates is empty, it returns false because an empty set cannot form a rectangle.
Find boundaries: It calculates the minimum and maximum row and column values from the set of coordinates. This defines the bounding box of the potential rectangle.
Iterate and check: It iterates through all the cells within the calculated bounding box. If any cell within these boundaries is not present in the blockCoordinates set, it means the shape is not a complete rectangle, so it returns false.
Return true: If all cells within the bounds are found in the set, it means the block is rectangular, and the function returns true.
How to use it with the previous function:

```
Kotlin
fun main() {
val matrix = listOf(
// ... your matrix data ...
)

    val rectangularBlocks = findRectangularBlocks(matrix)
    for ((coordinates, isHollow) in rectangularBlocks) {
        val isRectangular = verifyRectangularBlock(coordinates)
        println("Coordinates: $coordinates, Is Hollow: $isHollow, Is Rectangular: $isRectangular")
    }
}
Use code with caution.

This will now print whether each detected block is rectangular or not, along with the other information.
```

## Hollow test

The Prompt:

**Good, assume the block test is true.  Please examine the block to see if it is hollow - that is it has a boundary of the block value, but there is an "interior" to the block with another value.  Return true if the block is hollow.**

```
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
```

Explanation:

Minimum size check: If the block has less than 4 cells, it cannot have an interior, so it returns false.
Find boundaries: It calculates the minimum and maximum row and column values to define the bounding box of the block.
Get target value: It gets the value of the block from one of its corner cells.
Check interior: It iterates through the interior cells of the block (excluding the boundary). If it finds a cell with a value different from the targetValue, it means the block is hollow and returns true.
Return false: If the loop completes without finding any different values inside, it means the block is solid, and the function returns false.
How to use with the previous functions:

```
Kotlin
fun main() {
val matrix = listOf(
// ... your matrix data ...
)

    val rectangularBlocks = findRectangularBlocks(matrix)
    for (coordinates in rectangularBlocks) {
        val isRectangular = verifyRectangularBlock(coordinates) // Assuming you have this function
        if (isRectangular) {
            val isHollow = isBlockHollow(matrix, coordinates)
            println("Coordinates: $coordinates, Is Hollow: $isHollow")
        }
    }
}

```
Use code with caution.

This will now print whether each detected rectangular block is hollow or solid.

## Block Completion

The Prompt:

```
fun completeRectangularBlock(blockCoordinates: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    if (blockCoordinates.isEmpty()) return emptySet() // Nothing to complete

    val minRow = blockCoordinates.minOf { it.first }
    val maxRow = blockCoordinates.maxOf { it.first }
    val minCol = blockCoordinates.minOf { it.second }
    val maxCol = blockCoordinates.maxOf { it.second }

    val missingCoordinates = mutableSetOf<Pair<Int, Int>>()

    for (row in minRow..maxRow) {
        for (col in minCol..maxCol) {
            if (Pair(row, col) !in blockCoordinates) {
                missingCoordinates.add(Pair(row, col))
            }
        }
    }

    return missingCoordinates
}
```

Explanation:

Handle empty set: If the input blockCoordinates is empty, it returns an empty set.
Find boundaries: It calculates the minimum and maximum row and column values from the set of coordinates. This defines the bounding box of the potential rectangle.
Find missing coordinates: It iterates through all the cells within the calculated bounding box. If any cell within these boundaries is not present in the blockCoordinates set, it adds that coordinate pair to the missingCoordinates set.
Return missing coordinates: The function returns the missingCoordinates set, which contains the pairs needed to complete the rectangular block.
Example Usage:

```
Kotlin
fun main() {
val blockCoordinates = setOf(
Pair(1, 1), Pair(1, 2),
Pair(2, 1), Pair(2, 2),
Pair(3, 1)
)

    val missingCoordinates = completeRectangularBlock(blockCoordinates)
    println(missingCoordinates) // Output: [(3, 2)]
}
```