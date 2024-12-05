@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.translateBlocksToOrigin

// example: 6ecd11f4 quantize to index


class S28QuantizeBlocks : BidirectionalBaseClass() {
	override val name: String = "**** quantize to index"

	var checkedOutput = false
	var quantaInRegion = -1

	override fun resetState() {
		checkedOutput = false
		quantaInRegion = -1
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "6ecd11f4") {
			println("here now")
		}

		// record the "quanta" - the size of the input square
		//   that represents a point in the output matrix
		if (!checkedOutput) {
			if (outputMatrix.size != outputMatrix[0].size) {
				return emptyList()
			}
			quantaInRegion = outputMatrix.size
			checkedOutput = true
		}

		if (inputBlockList.isEmpty()) {
			return emptyList()
		}
		val biggestBlock = inputBlockList.sortedByDescending { it.coordinates.size }
		val quantColor = biggestBlock[0].color

		val guideBlocks = inputBlockList.filter { it.color == quantColor }
		val guideBlocksToZero = translateBlocksToOrigin(guideBlocks)

		val coords = guideBlocksToZero.flatMap { it.coordinates }
		val minRow = coords.minOf { it.first }
		val maxRow = coords.maxOf { it.first }
		val minCol = coords.minOf { it.second }
		val maxCol = coords.maxOf { it.second }

		val divisorRow = (maxRow + 1) / quantaInRegion
		val divisorCol = (maxCol + 1) / quantaInRegion
		if (divisorRow != divisorCol) {
			return emptyList()
		}

		val retList = MutableList(quantaInRegion) { MutableList(quantaInRegion) { 0 } }
		for (row in 0 until quantaInRegion) {
			for (col in 0 until quantaInRegion) {
				if (coords.contains(Pair(row * divisorRow, col * divisorCol))) {
					retList[row][col] = 1
				} else {
					retList[row][col] = 0
				}
			}
		}
		println(retList)





		return emptyList()
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
