@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "RedundantIf"
)

// note that part of this code follows an example by Google Gemini
// see docs/10 GeminiTransformTestArchitecture.md FMI

package solutions

import Block
import Point
import taskAbstractionsList

class TransformationsBlockAndPoint {

	data class ExampleBlockAndPoint(
		val input: Pair<List<Block>, List<Point>>,
		val output: Pair<List<Block>, List<Point>>)

	sealed class Transformation {
		abstract val name: String
		abstract fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>>
	}

	object ShiftBlocksDown : Transformation() {
		override val name = "SOL-TX01 Shift blocks down one cell"

		override fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>> {
			val (blocks, points) = input
			val transformedBlocks = blocks.map { block ->
				block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row + 1, col) }.toSet())
			}
			return Pair(transformedBlocks, points)
		}
	}

	object ShiftBlocksLeft : Transformation() {
		override val name = "SOL-TX02 Shift blocks left one cell"

		override fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>> {
			val (blocks, points) = input
			val transformedBlocks = blocks.map { block ->
				block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row, col - 1) }.toSet())
			}
			return Pair(transformedBlocks, points)
		}
	}

	object ColorBlocksByFirstPoint : Transformation() {
		override val name = "SOL-TX03 Color all blocks based on the color of the first point"

		override fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>> {
			val (blocks, points) = input
			val firstPointColor = points.firstOrNull()?.color ?: return input // Handle case with no points
			val transformedBlocks = blocks.map { block ->
				block.copy(color = firstPointColor)
			}
			return Pair(transformedBlocks, points)
		}
	}

	val transformations = listOf(
		ShiftBlocksLeft,
		ShiftBlocksDown,
		ColorBlocksByFirstPoint
	)

	fun testTransformation(transformation: Transformation, e: ExampleBlockAndPoint): Boolean {
		val transformedInput = transformation.apply(e.input)
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
	fun scanTransformations(): Boolean {
		for (atask in taskAbstractionsList) {
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

			for (t in transformations) {
				if (testTransformation(t, example)) {
					//println("Transform ${t.name} - TRANSFORMATION SUCCESS!!")

					// check remaining examples using the same transformation

					var success = true
					for (j in 1 until numExamples) {
						if (testTransformation(t, example)) {

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

							if (!testTransformation(t, example2)) {
								success = false
								break  // failed on subsequent test
							}
						}
					}
					if (success) {
						// success ! all subsequent transformations worked on this task
						println("Transform ${t.name} - TRANSFORMATION SUCCESS on *all* Examples!!")
						// TODO: confirm the transformation by checking the test matrix - To be continued
						return true
					}
					// continue looping through the transformations
				}
			}
		}
		return false
	}
}





