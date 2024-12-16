@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.*

// example: S43 8e1813be block directed lines assembly

class S43BlockDirectedLinesAssembly : BidirectionalBaseClass() {
	override val name: String = "block directed lines assembly"

	var checkedOutput = false
	var layoutVertical = false

	override fun resetState() {
		checkedOutput = false
	}

	// figure out if there is a block to direct the size
	//  of output.  No points in output allowed.

	override fun testTransform(): List<List<Int>> {



		if (!checkedOutput) {
			checkedOutput = true
			if (outputPointList.isNotEmpty()) {
				return emptyList()
			}
			if (areAllBlocksVerticalColumns(outputMatrix, outputBlockList)) {
				layoutVertical = true
			} else if (!areAllBlocksHorizontalRows(outputMatrix, outputBlockList)) {
				return emptyList()
			}
		}

		// sort the input block list into vertical or horizontal
		// bars and the signal block

		val barBlockList: MutableList<Block> = mutableListOf()
		var signalBlock: Block
		var rowSize = 0
		var colSize = 0

		for (b in inputBlockList) {
			if (barBlockList.find { it.color == b.color } == null) {
				// the block b is not already in the list
				val coordinates = b.coordinates
				val minRow = coordinates.minOf { it.first }
				val maxRow = coordinates.maxOf { it.first }
				val minCol = coordinates.minOf { it.second }
				val maxCol = coordinates.maxOf { it.second }

				if ((maxRow - minRow) >= 2 && (maxCol - minCol) >= 2) {
					signalBlock = b
					rowSize = maxRow - minRow + 1
					colSize = maxCol - minCol + 1
				} else {
					barBlockList.add(b)
				}
			}
		}

		var retArray: MutableList<List<Int>> = mutableListOf()
		if (isOneBlockHorizontal(inputMatrix, barBlockList)) {
			// check that output block and bar count matches
			if (barBlockList.size != rowSize) {
				return emptyList()
			}
			val barSorted = sortBlocksByRow(barBlockList)
			for (i in 0 until rowSize) {
				val rowList = List(colSize) { barSorted[i].color }
				retArray.add(rowList)
			}
			return retArray
		}

		// check that output block and bar count matches
		// vertical list
		if (barBlockList.size != colSize) {
			return emptyList()
		}
		val barSorted = sortBlocksByColumn(barBlockList)
		val rowList : MutableList<Int> = mutableListOf()
		// assemble all colors into one row template
		for (col in 0 until colSize) {
			rowList.add(barSorted[col].color)
		}
		for (row in 0 until rowSize) {
			retArray.add(rowList)
		}
		return retArray
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
