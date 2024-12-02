@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findMajorityColorInMatrix
import solutions.utilities.translateBlockBy

// example: 5ad4f10b quantize and summarize with largest common denominator

class S21QuantizeAndSummarize : BidirectionalBaseClass() {
	override val name: String = "**** quantize and summarize"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	// basically figure out the domininate color,
	//   then pull out the blocks with that color
	// Then quantize!!
	override fun testTransform(): List<List<Int>> {

		if (taskName == "5ad4f10b") {
			println("here now")
		}

		val majorityColor = findMajorityColorInMatrix(inputMatrix)

		val blocksFiltered = inputBlockList.filter { it.color == majorityColor }
		if (blocksFiltered.isEmpty()) {
			return emptyList()
		}

		val allBlockCoordinates = blocksFiltered.flatMap { it.coordinates }
		val minRow = allBlockCoordinates.minOf { it.first }
		val minCol = allBlockCoordinates.minOf { it.second }

		val newBlockList : MutableList<Block> = mutableListOf()
		for (b in blocksFiltered) {
			newBlockList.add(translateBlockBy(b, -minRow, -minCol))
		}


		//val blocksSortedBySize = blocksFiltered.sortedBy { it.coordinates.size }

		return emptyList()
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
