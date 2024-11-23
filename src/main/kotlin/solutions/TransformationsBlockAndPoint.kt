@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "RedundantIf"
)

// note that part of this code follows an example by Google Gemini
// see docs/10 GeminiTransformTestArchitecture.md FMI

// note that these transformations work in a uni-directional
// manner - inspection / transformation of the input blocks and points
// to a supposed solution with output blocks and points
// that are then compared to the earlier assembled output blocks and points.

// a transform that iterates over all input / output and forms a model
//    is BEYOND the scope of this Transformation set.  Stay tuned.

package solutions

import Block
import Point
import SolutionMatrix
import SolvedTasks
import solutions.utilities.recreateMatrix
import solvedTasks
import taskAbstractionsList

class TransformationsBlockAndPoint {

	data class ExampleBlockAndPoint(
		val input: Pair<List<Block>, List<Point>>,
		val output: Pair<List<Block>, List<Point>>
	)

	sealed class Transformation {
		abstract val name: String
		abstract fun apply(
			input: Pair<List<Block>, List<Point>>,
			numRows: Int,
			numCols: Int
		): Pair<List<Block>, List<Point>>
	}

	object ShiftBlocksDown : Transformation() {
		override val name = "SOL-TX Shift blocks down one cell"

		override fun apply(
			input: Pair<List<Block>, List<Point>>,
			numRows: Int,
			numCols: Int
		): Pair<List<Block>, List<Point>> {
			val (blocks, points) = input
			val transformedBlocks = blocks.map { block ->
				block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row + 1, col) }.toSet())
			}
			return Pair(transformedBlocks, points)
		}
	}

	object ShiftBlocksLeft : Transformation() {
		override val name = "SOL-TX Shift blocks left one cell"

		override fun apply(
			input: Pair<List<Block>, List<Point>>,
			numRows: Int,
			numCols: Int
		): Pair<List<Block>, List<Point>> {
			val (blocks, points) = input
			val transformedBlocks = blocks.map { block ->
				block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row, col - 1) }.toSet())
			}
			return Pair(transformedBlocks, points)
		}
	}

	object ColorBlocksByFirstPoint : Transformation() {
		override val name = "SOL-TX Color all blocks based on the color of the first point"

		override fun apply(
			input: Pair<List<Block>, List<Point>>,
			numRows: Int,
			numCols: Int
		): Pair<List<Block>, List<Point>> {
			val (blocks, points) = input
			val firstPointColor = points.firstOrNull()?.color ?: return input // Handle case with no points
			val transformedBlocks = blocks.map { block ->
				block.copy(color = firstPointColor)
			}
			return Pair(transformedBlocks, points)
		}
	}

	/*

	this one needs to wait - as the biggest block is important -
	but the remainder of the matrix needs to be colored to some
	value only indicated by the output matrix
	see 25d8a9c8

	object BiggestBlock : Transformation() {
		override val name = "SOL-TX Find Biggest Block"

		override fun apply(
			input: Pair<List<Block>, List<Point>>,
			numRows: Int,
			numCols: Int
		): Pair<List<Block>, List<Point>> {

			val blocks = input.first
			val defaultResult = Pair(listOf<Block>(), listOf<Point>())
			// Filter blocks with size greater than numRow
			val filteredBlocks = blocks.filter { it.coordinates.size > numRows }

			// Return null if no blocks meet the criteria
			if (filteredBlocks.isEmpty()) return defaultResult

			// Find the biggest block among the filtered ones
			var filtered = filteredBlocks.maxByOrNull { it.coordinates.size }

			val retVal = Pair(filteredBlocks, listOf<Point>())
			return retVal
		}
	}*/

	val transformations = listOf(
		ShiftBlocksLeft,
		ShiftBlocksDown,
		ColorBlocksByFirstPoint
	)

	fun testTransformation(
		transformation: Transformation,
		e: ExampleBlockAndPoint,
		numRows: Int,
		numCols: Int
	): Boolean {
		val transformedInput = transformation.apply(e.input, numRows, numCols)
		if (transformedInput != e.output) {
			return false
		}
		return true
	}

	/**
	 * for each task that has been broken down into Blocks and Points,
	 * see if that Task can be solved by various Transformations.
	 * Check the first example for validity, then verify
	 * the validity with the subsequent examples.
	 */
	fun scanTransformations() {
		val theList = taskAbstractionsList // for debugging visibility
		for (atask in theList) {
			val taskName = atask.taskData.name

			if (taskName == "0d3d703e") {
				println("we have 0d3d703e") // mapping of colors
			}
			val numExamples = atask.abstractionsList.size

			val taskCoordinateData = atask.taskData

			/*
			 * for a given task cycle look at the first example.
			 * cycle through the transformations.
			 *    If a transformation appears to solve the example,
			 * then try the remaining examples with the same transformation.
			 *    If all examples are solved with the transformation,
			 * the WE HAVE A WINNER!
			 */

			val originalMatrixInputAndOutput = taskCoordinateData.train[0]
			val abstraction = atask.abstractionsList[0]
			val inputBlocks = abstraction.input.blocks
			val inputPoints = abstraction.input.points

			val outputBlocks = abstraction.output.blocks
			val outputPoints = abstraction.output.points

			val originalMatrices = Pair(
				atask.abstractionsList[0].input,
				atask.abstractionsList[0].output
			)
			val example = ExampleBlockAndPoint(
				input = Pair(inputBlocks, inputPoints),
				output = Pair(outputBlocks, outputPoints)
			)

			val numRows = originalMatrixInputAndOutput.input.size
			val numCols = originalMatrixInputAndOutput.input[0].size
			for (t in transformations) {
				if (testTransformation(t, example, numRows, numCols)) {

					// OK it worked. check remaining examples using the same transformation

					var success = true
					for (j in 1 until numExamples) {

						val originalMatrixInputAndOutput2 = taskCoordinateData.train[j]
						val abstraction2 = atask.abstractionsList[j]
						val inputBlocks2 = abstraction.input.blocks
						val inputPoints2 = abstraction.input.points

						val outputBlocks2 = abstraction.output.blocks
						val outputPoints2 = abstraction.output.points

						val example2 = ExampleBlockAndPoint(
							input = Pair(inputBlocks2, inputPoints2),
							output = Pair(outputBlocks2, outputPoints2)
						)

						val numRows2 = originalMatrixInputAndOutput2.input.size
						val numCols2 = originalMatrixInputAndOutput2.input[0].size
						if (!testTransformation(t, example2, numRows2, numCols2)) {
							success = false
							break  // failed on subsequent test
						}
					}

					// success ! all subsequent transformations worked on this task

					println("Transform ${t.name} - WORKED - continuing")

					// re-create the test "key" matrix and compare to the real thing
					//   Do this for all test matrix input and output pairs

					val solutionMatrices = mutableListOf<SolutionMatrix>()

					for (j in 0 until taskCoordinateData.test.size) {

						val abstraction3 = atask.abstractionsInTestMatrices[j]
						val inputBlocks3 = abstraction3.blocks
						val inputPoints3 = abstraction3.points

						val originalTestMatrixInputAndOutput3 = taskCoordinateData.test[j]

						// note - this is the test "key" -
						val outputMatrix3 = originalMatrixInputAndOutput.output
						val outputRowCount3 = outputMatrix3.size
						val outputColCount3 = outputMatrix3[j].size

						val input3 = Pair(inputBlocks3, inputPoints3)

						val numRows3 = originalTestMatrixInputAndOutput3.input.size
						val numCols3 = originalTestMatrixInputAndOutput3.input[0].size
						val transformedInput = t.apply(input3, numRows3, numCols3)

						val resultMatrix3 = recreateMatrix(
							outputRowCount3,
							outputColCount3,
							transformedInput.first, // blocks
							transformedInput.second
						) // points

						if (resultMatrix3 != originalTestMatrixInputAndOutput3.output) {
							success = false
							break
						}
						// record the solution matrix
						solutionMatrices.add(SolutionMatrix(resultMatrix3))
					}
					println("Transform ${t.name} for $taskName - VERIFIED!!")

					val solved = SolvedTasks(
						atask.taskData,
						taskName,
						t.name,
						solutionMatrices
					)

					solvedTasks.add(solved)

					break
				}
				// continue looping through the transformations
			}
		}
	}
}
