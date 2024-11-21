@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "RedundantIf"
)

// note that part of this code follows an example by Google Gemini
// see docs/10 GeminiTransformTestArchitecture.md FMI

package solutions

import Block
import MatrixDataInputAndOutput
import Point
import taskAbstractionsList
import kotlin.collections.List

class TransformationBlockAndPointToMatrix {

	data class ExampleTransform(
		val input: Pair<List<Block>, List<Point>>,
		val output: List<List<Int>>
	)

	sealed class TransformationToMatrix {
		abstract val name: String
		abstract fun apply(
			input: Pair<List<Block>, List<Point>>,
			size: Pair<Int, Int>
		): List<List<Int>>
	}

	object ColorByMajority : TransformationToMatrix() {
		override val name = "SOL-TXTM01 Create an output matrix that is colored by the majority of input cell colors"

		override fun apply(input: Pair<List<Block>, List<Point>>, size: Pair<Int, Int>): List<List<Int>> {

			// have to determine the majority color
			val colorCounts = mutableMapOf<Int, Int>()

			// Count colors in blocks
			for (block in input.first) {
				val area = block.coordinates.size
				colorCounts[block.color] = colorCounts.getOrDefault(block.color, 0) + area
			}

			// Count colors in points
			for (point in input.second) {
				colorCounts[point.color] = colorCounts.getOrDefault(point.color, 0) + 1
			}

			// Find the color with the maximum count
			var majorityColor = -1
			var maxCount = 0
			for ((color, count) in colorCounts) {
				if (count > maxCount) {
					majorityColor = color
					maxCount = count
				}
			}

			val rows = size.first
			val cols = size.second
			val retMatrix = List(rows) {
				List(cols) { majorityColor }
			}
			return retMatrix
		}
	}

	val transformations = listOf(
		ColorByMajority
	)

	data class Example(
		val input: Pair<List<Block>, List<Point>>,
		val output: Pair<List<Block>, List<Point>>,
		val originalMatrices: MatrixDataInputAndOutput
	)

	/**
	 * The various transforms look at the input matrix.
	 *     The transforms form various theories about what
	 *     the output matrix could look like, and
	 *     form the output matrix (in the size given) and
	 *     return that matrix.
	 */
	fun testTransformation(
		transformation: TransformationToMatrix,
			input: Pair<List<Block>, List<Point>>,
			size: Pair<Int, Int>): List<List<Int>> {
		val outputMatrix = transformation.apply(input, size)
		return outputMatrix
	}

	/**
	 * for each task that has been broken down into Blocks and Points,
	 * see if that Task can be solved by various Transformations.
	 * Check the first example for validity, then verify
	 * the validity with the subsequent examples.
	 */
	fun scanTransformations(): Boolean {

		for (atask in taskAbstractionsList) {
			val taskName = atask.taskData.name

			if (taskName == "5582e5ca") {
				println("we have 5582e5ca")
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

			val abstraction = atask.abstractionsList[0]
			val inputBlocks = abstraction.input.blocks
			val inputPoints = abstraction.input.points
			val originalMatrixInputAndOutput = taskCoordinateData.train[0]
			val outputMatrix = originalMatrixInputAndOutput.output
			val outputRowCount = outputMatrix.size
			val outputColCount = outputMatrix[0].size
			val size = Pair(outputRowCount, outputColCount)
			val	input = Pair(inputBlocks, inputPoints)

			for (t in transformations) {
				val resultMatrix = testTransformation(t, input, size)
				if (resultMatrix == originalMatrixInputAndOutput.output) {
					var success = true

					// we had a match on the output matrix.
					//    repeat the transform with the rest of the examples.

					for (j in 1 until numExamples) {

						val abstraction2 = atask.abstractionsList[j]
						val inputBlocks2 = abstraction2.input.blocks
						val inputPoints2 = abstraction2.input.points
						val originalMatrixInputAndOutput2 = taskCoordinateData.train[j]
						val outputMatrix2 = originalMatrixInputAndOutput.output
						val outputRowCount2 = outputMatrix2.size
						val outputColCount2 = outputMatrix2[j].size
						val size2 = Pair(outputRowCount2, outputColCount2)
						val	input2 = Pair(inputBlocks2, inputPoints2)

						val resultMatrix2 = testTransformation(t, input2, size2)
						if (resultMatrix2 != originalMatrixInputAndOutput2.output) {
							success = false
							break
						}
					}
					if (success) {
						// success ! all subsequent transformations worked on this task
						println("Transform ${t.name} - TRANSFORMATION TO MATRIX SUCCESS on *all* Examples!!")
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


