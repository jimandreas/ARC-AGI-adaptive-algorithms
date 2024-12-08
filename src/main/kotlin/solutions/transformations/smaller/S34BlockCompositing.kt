@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix
import solutions.utilities.translateBlockBy

// example: bc1d5164 block compositing

class S34BlockCompositing : BidirectionalBaseClass() {
	override val name: String = "block compositing"

	var checkedOutput = false
	var numRowOutput = 0
	var numColOutput = 0
	var rowDelta = 0
	var colDelta = 0

	var inputRowMidpoint = 0
	var inputColMidpoint = 0

	override fun resetState() {
		checkedOutput = false
	}

	// translate all blocks based on their initial position to
	//  composite them
	override fun testTransform(): List<List<Int>> {

		if (taskName == "bc1d5164") {
			println("here now")
		}

		if (!checkedOutput) {
			// demand square matrix
			if (outputMatrix.size != outputMatrix[0].size) {
				return emptyList()
			}
			numRowOutput = outputMatrix.size
			numColOutput = outputMatrix.size

			if ((inputMatrix.size <= numRowOutput)
				|| (inputMatrix[0].size <= numColOutput)
			) {
				return emptyList() // output has to be smaller
			}

			rowDelta = inputMatrix.size - numRowOutput
			colDelta = inputMatrix[0].size - numColOutput

			inputRowMidpoint = inputMatrix.size / 2
			inputColMidpoint = inputMatrix[0].size / 2

			checkedOutput = true
		}

		// redo the block abstraction allowing diagonals
		val nIn = MatrixAbstractions()
		val bu = BlockUtilities()
		nIn.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			nIn,
			scanDiagonals = true,
			requireSameColor = true,
			allowPoints = true
		)

		val numRow = inputMatrix.size
		val numCol = inputMatrix[0].size
		val bList: MutableList<Block> = mutableListOf()
		// figure out which corner each block is located in
		for (b in nIn.blocks) {
			var rowOffset = 0
			var colOffset = 0
			val coordinates = b.coordinates
			val minRow = coordinates.minOf { it.first }
			val maxRow = coordinates.maxOf { it.first }
			val minCol = coordinates.minOf { it.second }
			val maxCol = coordinates.maxOf { it.second }

			// for block at origin offset is 0, 0 already. Handle other cases

			// if upper right
			if ((maxRow <= inputRowMidpoint) && (minCol >= inputColMidpoint)) {
				colOffset = colDelta
			} else if (
				(minRow >= inputRowMidpoint) && (minCol >= inputColMidpoint)) {
				// bottom right corner
				rowOffset = rowDelta
				colOffset = colDelta
			} else if (
				(minRow >= inputRowMidpoint) /*&& (minCol <= inputColMidpoint)*/) {
				// lower right
				rowOffset = rowDelta
			}

			val translated = translateBlockBy(b, -rowOffset, -colOffset)
			bList.add(translated)
		}

		val retArray = recreateMatrix(
			numRowOutput, numColOutput,
			bList, emptyList()
		)

		return retArray
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}
}
