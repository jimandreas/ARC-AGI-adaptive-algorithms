# Mirror the Matrix Solution by Google Gemini

## The Prompt

Some ARC puzzles can be solved by mirroring the puzzle matrix.  Please 
create a kotlin solution for testing if mirroring the matrix vertically 
solves the puzzle.  Mirroring vertically means that the top row of the matrix 
is swapped with the bottom row, the second row is swapped with the second from 
the bottom, and so on.  The resulting matrix should first be checked with the 
output matrix of the training set, and then if this results in matches for all 
training examples, then try inverting the matrices in the test list and check 
if the inverted input matches the test output.

The datastructure for the puzzle matrices is as follows

```
@Serializable
data class TaskCoordinateData(
val train: List<MatrixDataInputAndOutput>,
val test: List<MatrixDataInputAndOutput>,   // note that there sometimes MORE THAN ONE entry in this list!!
val name: String = ""
)

@Serializable
data class MatrixDataInputAndOutput(
val input: List<List<Int>>,
val output: List<List<Int>>
)
```

Please generate a kotlin program that does all of the following: 
inverts the matrices in the inputs, compares to the outputs, and then 
if this is successful, inverts the test matrices and checks to see if this 
solution matches the test output matrices.

## Google Gemini response

```kotlin
    fun mirrorMatrixVertically(matrix: List<List<Int>>): List<List<Int>> {
        return matrix.reversed()
    }

    fun compareMatrices(matrix1: List<List<Int>>, matrix2: List<List<Int>>): Boolean {
        if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
            return false // Matrices have different dimensions
        }
        for (i in matrix1.indices) {
            for (j in matrix1[0].indices) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false // Cells don't match
                }
            }
        }
        return true // Matrices are identical
    }

    fun testVerticalMirroringSolution(taskData: TaskCoordinateData): Boolean {
        // Check if mirroring solves the training examples
        for (example in taskData.train) {
            val mirroredInput = mirrorMatrixVertically(example.input)
            if (!compareMatrices(mirroredInput, example.output)) {
                return false // Mirroring doesn't solve this example
            }
        }

        // If mirroring solves all training examples, check the test examples
        for (example in taskData.test) {
            val mirroredInput = mirrorMatrixVertically(example.input)
            if (!compareMatrices(mirroredInput, example.output)) {
                return false // Mirroring doesn't solve this test example
            }
        }

        return true // Mirroring solves all examples
    }

    fun main() {
        // Example task data (replace with your actual data)
        val taskData = TaskCoordinateData(
            train = listOf(
                MatrixDataInputAndOutput(
                    input = listOf(
                        listOf(0, 1, 0),
                        listOf(1, 0, 1),
                        listOf(0, 1, 0)
                    ),
                    output = listOf(
                        listOf(0, 1, 0),
                        listOf(1, 0, 1),
                        listOf(0, 1, 0)
                    )
                )
            ),
            test = listOf(
                MatrixDataInputAndOutput(
                    input = listOf(
                        listOf(0, 0, 1),
                        listOf(0, 1, 0),
                        listOf(1, 0, 0)
                    ),
                    output = listOf(
                        listOf(1, 0, 0),
                        listOf(0, 1, 0),
                        listOf(0, 0, 1)
                    )
                )
            )
        )

        if (testVerticalMirroringSolution(taskData)) {
            println("Vertical mirroring solves this task!")
        } else {
            println("Vertical mirroring does not solve this task.")
        }
    }
```

Explanation:

mirrorMatrixVertically(matrix): This function takes a matrix 
(as List<List<Int>>) and returns a new matrix with the rows reversed, 
effectively mirroring it vertically.

compareMatrices(matrix1, matrix2): This function compares two matrices 
and returns true if they are identical, false otherwise.

testVerticalMirroringSolution(taskData):

This function takes a TaskCoordinateData object as input.
It iterates through the train examples. For each example, it mirrors the 
input matrix and compares it with the output matrix. If any comparison fails, it returns false.
If all training examples are solved by mirroring, it then iterates through 
the test examples and performs the same check.
If all training and test examples are solved, it returns true.

main():
Creates an example TaskCoordinateData object (replace with your actual data).
Calls testVerticalMirroringSolution to check if mirroring solves the task.
Prints the result.
This program will test if mirroring the input matrices vertically solves the ARC 
task and print the result accordingly. You can integrate this code into your larger 
program to apply this solution to different ARC tasks.

## Follow up - add "Horizontal" mirroring - Prompt

That was good!   Please modify the program so that it 
now mirrors the data "horizontally" - that is the first column 
is swapped for the last column, and the second column is swapped 
for the second to last column.

## Google Gemini reponse

```
import kotlinx.serialization.Serializable

// ... (Your data classes for TaskCoordinateData, MatrixDataInputAndOutput) ...

fun mirrorMatrixHorizontally(matrix: List<List<Int>>): List<List<Int>> {
    return matrix.map { row -> row.reversed() }
}

fun compareMatrices(matrix1: List<List<Int>>, matrix2: List<List<Int>>): Boolean {
    // ... (same as before) ...
}

fun testHorizontalMirroringSolution(taskData: TaskCoordinateData): Boolean {
    // Check if mirroring solves the training examples
    for (example in taskData.train) {
        val mirroredInput = mirrorMatrixHorizontally(example.input)
        if (!compareMatrices(mirroredInput, example.output)) {
            return false // Mirroring doesn't solve this example
        }
    }

    // If mirroring solves all training examples, check the test examples
    for (example in taskData.test) {
        val mirroredInput = mirrorMatrixHorizontally(example.input)
        if (!compareMatrices(mirroredInput, example.output)) {
            return false // Mirroring doesn't solve this test example
        }
    }

    return true // Mirroring solves all examples
}

fun main() {
    // ... (Example task data - you might need to adjust this for horizontal mirroring) ...

    if (testHorizontalMirroringSolution(taskData)) {
        println("Horizontal mirroring solves this task!")
    } else {
        println("Horizontal mirroring does not solve this task.")
    }
}
```

Changes:

mirrorMatrixHorizontally(matrix):

This function now uses matrix.map { row -> row.reversed() }. 
This iterates through each row in the matrix and reverses the 
elements within each row, effectively mirroring the matrix horizontally.
Example task data: You might need to adjust the example taskData in 
the main() function to include a task that can be solved by horizontal mirroring.

The rest of the code (comparison and testing logic) remains the same.

Now, this program will test if mirroring the input matrices horizontally 
solves the ARC task and print the result. You can use this function to check 
for horizontal mirroring solutions in your ARC-AGI tasks.