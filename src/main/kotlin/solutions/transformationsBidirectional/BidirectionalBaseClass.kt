@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.transformationsBidirectional

import Block
import Point

abstract class BidirectionalBaseClass {
	private var blockList: List<Block> = listOf()
	private var pointList: List<Point> = listOf()

	private var outputBlockList: List<Block> = listOf()
	private var outputPointList: List<Point> = listOf()
	private var outputMatrix: List<List<Int>> = listOf()
	abstract val name: String

	fun setInput(input: Pair<List<Block>, List<Point>>) {
		blockList = input.first
		pointList = input.second
		// init other states
		outputBlockList = listOf()
		outputPointList = listOf()
		outputMatrix = listOf()
	}

	fun setOutput(
		output: Pair<List<Block>, List<Point>>,
		outputMatrixIn: List<List<Int>>
	) {
		outputBlockList = output.first
		outputPointList = output.second
		outputMatrix = outputMatrixIn
	}
	abstract fun testTransform(): List<List<Int>>
	abstract fun returnTestOutput(input: Pair<List<Block>, List<Point>>): List<List<Int>>
}

class TestTrans() : BidirectionalBaseClass() {
	override val name: String = "test"

	override fun testTransform(): List<List<Int>> {
		return listOf()
	}

	override fun returnTestOutput(input: Pair<List<Block>, List<Point>>): List<List<Int>> {
		return listOf()
	}
}

val bidiTransformList: List<BidirectionalBaseClass> = listOf(
	TestTrans(),
	BidiFullRowColoring()
)
