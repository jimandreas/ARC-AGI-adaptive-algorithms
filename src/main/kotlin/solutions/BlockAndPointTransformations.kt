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
import TaskCoordinateData
import taskAbstractionsList

data class Example(val input: Pair<List<Block>, List<Point>>, val output: Pair<List<Block>, List<Point>>)

sealed class Transformation {
	abstract val name: String
	abstract fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>>
}


object ShiftBlocksDown : Transformation() {
	override val name = "Shift blocks down one cell"

	override fun apply(input: Pair<List<Block>, List<Point>>): Pair<List<Block>, List<Point>> {
		val (blocks, points) = input
		val transformedBlocks = blocks.map { block ->
			block.copy(coordinates = block.coordinates.map { (row, col) -> Pair(row + 1, col) }.toSet())
		}
		return Pair(transformedBlocks, points)
	}
}

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

val transformations = listOf(
	ShiftBlocksLeft,
	ShiftBlocksDown,
	ColorBlocksByFirstPoint
)

class BlockAndPointTransformations {

	data class Example(
		val input: Pair<List<Block>, List<Point>>,
		val output: Pair<List<Block>, List<Point>>
	)

	fun testTransformation(transformation: Transformation, e: Example): Boolean {
		val transformedInput = transformation.apply(e.input)
		if (transformedInput != e.output) {
			return false
		}
		return true
	}

	fun scanTransformations(): Boolean {
		for (atask in taskAbstractionsList) {

			//   run through the transformations to see if one "works"

			val inputBlocks = atask.abstractionsList[0].input.blocks
			val inputPoints = atask.abstractionsList[0].input.points

			val outputBlocks = atask.abstractionsList[0].output.blocks
			val outputPoints = atask.abstractionsList[0].output.points

			val example = Example(Pair(inputBlocks, inputPoints), Pair(outputBlocks, outputPoints))

			for (t in transformations) {
				if (testTransformation(t, example)) {
					println("Transform ${t.name} - TRANSFORMATION SUCCESS!!")
					return true
				}
			}
		}
		return false
	}
}





