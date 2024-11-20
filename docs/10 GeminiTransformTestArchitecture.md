# Gemini Transform Test Architecture

## The prompt

I have decomposed the ARCprize input and output matrices for the 
training examples into the following two lists - for "blocks", 
i.e. adjacent cells with the same color, and "points", i.e. cells that
do not have an adjacent cell with the same color.   The lists have an 
Int that represents the color of the block or point, and then for 
blocks a Set of row and column coordinates that form the block.  
Points also have an Int for the color, and then one Pair of row 
and column Ints for the point coordinate.

    var blocks: MutableList<Pair<Int, Set<Pair<Int, Int>>>> = mutableListOf()
    var points: MutableList<Pair<Int, Pair<Int, Int>>> = mutableListOf()

The training example input matrix is decomposed into blocks and points.   
The output matrix is decomposed of blocks and points for the corresponding 
training example.   The Task can contain several training examples.

In Kotlin please create an architecture that (1) specifies a 
transformation in some human understandable form - such as "shift 
blocks left one cell" or "color all blocks based on the color of 
the first point" - and then this architecture applies the transformation 
to the input data of the first example and compares the result to the 
output data of the first example.

If this transformed input block and point data match the output 
block and point data, then continue with the test by applying the same 
transformation to the 2nd and subsequent training examples.

If the transformation is successful for all training examples, then 
print the name of the transformation and "success!".

## The code

```
data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
data class Point(val color: Int, val coordinate: Pair<Int, Int>)

data class Task(val input: Pair<List<Block>, List<Point>>, val output: Pair<List<Block>, List<Point>>)

sealed class Transformation {
    abstract val name: String
    abstract fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>>
}

// Example transformations
object ShiftBlocksLeft : Transformation() {
    override val name = "Shift blocks left one cell"

    override fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>> {
        val (blocks, points) = input
        val transformedBlocks = blocks.map { block ->
            block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row, col - 1) }.toSet())
        }
        return Pair(transformedBlocks, points)
    }
}

object ColorBlocksByFirstPoint : Transformation() {
    override val name = "Color all blocks based on the color of the first point"

    override fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>> {
        val (blocks, points) = input
        val firstPointColor = points.firstOrNull()?.color ?: return input // Handle case with no points
        val transformedBlocks = blocks.map { block ->
            block.copy(color = firstPointColor)
        }
        return Pair(transformedBlocks, points)
    }
}

fun testTransformation(transformation: Transformation, tasks: List<Task>): Boolean {
    for (task in tasks) {
        val transformedInput = transformation.apply(task.input)
        if (transformedInput != task.output) {
            return false
        }
    }
    return true
}

fun main() {
    // Example usage
    val tasks = listOf(
        Task(
            input = Pair(
                listOf(Block(1, setOf(Pair(0, 0), Pair(0, 1)))),
                listOf(Point(2, Pair(1, 0)))
            ),
            output = Pair(
                listOf(Block(1, setOf(Pair(0, -1), Pair(0, 0)))),
                listOf(Point(2, Pair(1, 0)))
            )
        ),
        // Add more tasks here
    )

    val transformations = listOf(ShiftBlocksLeft, ColorBlocksByFirstPoint)

    for (transformation in transformations) {
        if (testTransformation(transformation, tasks)) {
            println("${transformation.name} - success!")
        }
    }
}
```

Explanation:

Data Classes: Block and Point represent the decomposed matrix elements. 
Task holds the input and output data for a single training example.

Transformation Class: This sealed class defines the structure for all 
transformations. It has two properties:

name: A human-readable string describing the transformation.
apply: An abstract function that takes the input data and returns the transformed data.
Example Transformations:

ShiftBlocksLeft: Shifts all blocks one cell to the left by decreasing the 
column coordinate of each block's cell.
ColorBlocksByFirstPoint: Changes the color of all blocks to the color of 
the first point in the input.
testTransformation Function: This function iterates through the list of 
tasks and applies the given transformation to the input data of each task. 
It then compares the transformed input with the expected output. If any 
of the transformations fail to produce the expected output, it returns false. 
Otherwise, it returns true.

main Function:

Creates a list of Task objects, each representing a training example.

Creates a list of Transformation objects to test.

Iterates through the transformations and calls testTransformation for each one.

If a transformation is successful for all tasks, it prints the transformation name and "success!".
To use this architecture:

Add more concrete Transformation classes to define new transformations.

Populate the tasks list with your actual training data.

Run the main function to test the transformations against your data.

This architecture provides a structured way to define, apply, and test 
transformations on the decomposed matrix data. The use of a sealed class for 
Transformation ensures that you have a defined set of transformations and 
makes it easy to add new ones. The human-readable names help in understanding 
and debugging the transformations.
