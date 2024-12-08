@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
	"AssignmentInCondition"
)

package solutions.transformations.smaller

import Block
import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.translateBlockBy
import solutions.utilities.translatePointBy

// example: dae9d2b5 left right halves compositing

class S35LeftRightCompositing : BidirectionalBaseClass() {
	override val name: String = "left right halves compositing"

	var checkedOutput = false
	var numColOutput = 0
	var numRowOutput = 0
	var colDelta = 0

	var inputColMidpoint = 0

	var outputColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	// demand same row sizes input to output
	//  demand output is one half number of input columns
	override fun testTransform(): List<List<Int>> {

		if (taskName == "dae9d2b5") {
			println("here now")
		}

		if (!checkedOutput) {
			numRowOutput = outputMatrix.size
			numColOutput = outputMatrix[0].size

			val colCount = inputMatrix[0].size
			// input matrix must be exactly twice as wide as output
			if (numColOutput * 2 != colCount) {
				return emptyList()
			}
			inputColMidpoint = colCount / 2

			if (outputBlockList.isNotEmpty()) {
				outputColor = outputBlockList[0].color
			} else if (outputPointList.isNotEmpty()) {
				outputColor = outputPointList[0].color
			} else {
				return emptyList()
			}
			checkedOutput = true
		}

		val bList: MutableList<Block> = mutableListOf()
		// figure out which corner each block is located in
		for (b in inputBlockList) {
			var translated : Block
			val coordinates = b.coordinates
			val minRow = coordinates.minOf { it.first }
			val maxRow = coordinates.maxOf { it.first }
			val minCol = coordinates.minOf { it.second }
			val maxCol = coordinates.maxOf { it.second }

			if (minCol >= inputColMidpoint) {
				translated = translateBlockBy(b, 0, -inputColMidpoint)
			} else {
				translated = b
			}
			bList.add(translated)
		}

		val pList: MutableList<Point> = mutableListOf()
		// figure out which corner each block is located in
		for (p in inputPointList) {
			var translated : Point
			val coordinate = p.coordinate
			val col = p.coordinate.second

			if (col >= inputColMidpoint) {
				translated = translatePointBy(p, 0, -inputColMidpoint)
			} else {
				translated = p
			}
			pList.add(translated)
		}

		val retArray = recreateMatrix(
			numRowOutput, numColOutput,
			bList, pList,
			overrideColors = true,
			colorToUse = outputColor
		)

		return retArray
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}
}
