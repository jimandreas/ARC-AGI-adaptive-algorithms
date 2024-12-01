# Gemini code for mirroring matrices on the diagonal

## The prompt

You already helped me with mirroring a matrix in the vertical and 
horizontal directions.   It occurs to me that a matrix, if it is a 
square matrix with equal sized rows and columns, be mirrored diagonally.  
Please assume a square matrix in the ARC format (matrix: List<List<Int>>) - 
and create two functions - one to mirror the matrix on the "downward diagonal" 
and a second to mirror the matrix on the "upward diagonal".  
Please return the mirrored matrix as a List<List<Int>>.

## The Gemini code

```
fun mirrorMatrixDownwardDiagonal(matrix: List<List<Int>>): List<List<Int>> {
    val size = matrix.size // Assuming it's a square matrix
    val mirroredMatrix = MutableList(size) { MutableList(size) { 0 } }

    for (i in 0 until size) {
        for (j in 0 until size) {
            mirroredMatrix[j][i] = matrix[i][j]
        }
    }

    return mirroredMatrix
}

fun mirrorMatrixUpwardDiagonal(matrix: List<List<Int>>): List<List<Int>> {
    val size = matrix.size // Assuming it's a square matrix
    val mirroredMatrix = MutableList(size) { MutableList(size) { 0 } }

    for (i in 0 until size) {
        for (j in 0 until size) {
            mirroredMatrix[size - 1 - j][size - 1 - i] = matrix[i][j]
        }
    }

    return mirroredMatrix
}
```

Explanation:

mirrorMatrixDownwardDiagonal(matrix):

Creates a new mirroredMatrix with the same dimensions as the input matrix.
Iterates through the rows and columns of the input matrix.
For each cell at (i, j), it assigns the value to the cell at (j, i) in the mirroredMatrix. This effectively swaps the row and column indices, mirroring the matrix along the downward diagonal (from top-left to bottom-right).
mirrorMatrixUpwardDiagonal(matrix):

Creates a new mirroredMatrix with the same dimensions as the input matrix.
Iterates through the rows and columns of the input matrix.
For each cell at (i, j), it assigns the value to the cell at (size - 1 - j, size - 1 - i) in the mirroredMatrix. This maps the cell to its mirrored position along the upward diagonal (from bottom-left to top-right).

Example Usage:

```
fun main() {
val matrix = listOf(
listOf(1, 2, 3),
listOf(4, 5, 6),
listOf(7, 8, 9)
)

    val mirroredDownward = mirrorMatrixDownwardDiagonal(matrix)
    println("Mirrored Downward:")
    println(mirroredDownward)

    val mirroredUpward = mirrorMatrixUpwardDiagonal(matrix)
    println("Mirrored Upward:")
    println(mirroredUpward)
}
```

Output:

```
Mirrored Downward:
[[1, 4, 7], [2, 5, 8], [3, 6, 9]]

Mirrored Upward:
[[9, 6, 3], [8, 5, 2], [7, 4, 1]]
```
