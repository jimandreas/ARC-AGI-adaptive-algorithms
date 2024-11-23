@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package solutions.transformationsBidirectional

import Block
import Point

abstract class BidirectionalBaseClass {
	var blockList: List<Block> = listOf()
	var pointList: List<Point> = listOf()

	var outputBlockList: List<Block> = listOf()
	var outputPointList: List<Point> = listOf()
	var outputMatrix: List<List<Int>> = listOf()
	abstract val name: String

	fun setInput(inputBlockList: List<Block>, inputPointList: List<Point>) {
		blockList = inputBlockList
		pointList = inputPointList
	}

	fun setOutput(
		outputBlockListIn: List<Block>,
		outputPointListIn: List<Point>,
		outputMatrixIn: List<List<Int>>
	) {
		outputBlockList = outputBlockListIn
		outputPointList = outputPointListIn
		outputMatrix = outputMatrixIn
	}
	abstract fun testTransform(): List<List<Int>>
	abstract fun returnTestOutput(): List<List<Int>>
}

class TestTrans() : BidirectionalBaseClass() {
	override val name: String = "test"

	override fun testTransform(): List<List<Int>> {
		return listOf()
	}

	override fun returnTestOutput(): List<List<Int>> {
		return listOf()
	}
}


