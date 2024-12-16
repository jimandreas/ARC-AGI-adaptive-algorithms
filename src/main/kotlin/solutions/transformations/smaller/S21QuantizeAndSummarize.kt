@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findMajorityColorBasedOnCoordinates
import solutions.utilities.findMajorityColorInMatrix
import solutions.utilities.quantize
import solutions.utilities.translateBlockBy

// example: 5ad4f10b quantize and summarize with largest common denominator

class S21QuantizeAndSummarize : BidirectionalBaseClass() {
	override val name: String = "quantize and summarize"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	// basically figure out the domininate color,
	//   then pull out the blocks with that color
	// Then quantize!!
	override fun testTransform(): List<List<Int>> {



		if (inputPointList.isEmpty()) {
			return emptyList()
		}
		// oddly for this task the return color is the color of a point
		//   TODO: color - Probably could make this more parametric (?)
		val retColor = inputPointList[0].color

		val allCoords = inputBlockList.flatMap { it.coordinates }
		if (allCoords.isEmpty()) {
			return emptyList()
		}
		val majorityColor = findMajorityColorBasedOnCoordinates(inputMatrix, allCoords.toSet())

		val blocksFiltered = inputBlockList.filter { it.color == majorityColor }
		if (blocksFiltered.isEmpty()) {
			return emptyList()
		}

		val allBlockCoordinates = blocksFiltered.flatMap { it.coordinates }
		var minRow = allBlockCoordinates.minOf { it.first }
		var minCol = allBlockCoordinates.minOf { it.second }

		// move the GROUP of blocks to the upper left origin
		//   based on minRow and minCol
		val newBlockList: MutableList<Block> = mutableListOf()
		for (b in blocksFiltered) {
			newBlockList.add(translateBlockBy(b, -minRow, -minCol))
		}

		val retMatrix = quantize(newBlockList, retColor)

		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
