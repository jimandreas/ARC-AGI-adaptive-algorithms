@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.computeBoundingBox
import solutions.utilities.recreateMatrix
import solutions.utilities.translateBlockBy

// E07 a79310a0 translate and change color

class E07TranslateAndChangeColor : BidirectionalBaseClass() {
	override val name: String = "translate and change color"

	var checkedOutput = false
	var outputColor = 0
	var rowDelta = 0
	var colDelta = 0

	override fun resetState() {
		checkedOutput = false
	}

	// obtain output color of exactly one block, then
	// calculate transform amount
	override fun testTransform(): List<List<Int>> {

//		if (taskName == "a79310a0") {
//			//println("here now")
//		}

		// redo the block abstraction allowing points
		val nIn = MatrixAbstractions()
		val bu = BlockUtilities()
		nIn.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			nIn,
			scanDiagonals = true,
			requireSameColor = true,
			allowPoints = true
		)

		val nOut = MatrixAbstractions()
		nOut.matrix = outputMatrix
		bu.findConnectedBlocksInMatrix(
			nOut,
			scanDiagonals = true,
			requireSameColor = true,
			allowPoints = true
		)

		if (nIn.blocks.size != 1) {
			return emptyList()
		}

		if (!checkedOutput) {
			checkedOutput = true

			if (nOut.blocks.size != 1) {
				return emptyList()
			}
			val bboxIn = computeBoundingBox(nIn.blocks, emptyList())
			val bboxOut = computeBoundingBox(nOut.blocks, emptyList())

			val pointSpanRow = bboxIn.maxRow - bboxIn.minRow
			val blockSpanRow = bboxOut.maxRow - bboxOut.minRow

			val pointSpanCol = bboxIn.maxCol - bboxIn.minCol
			val blockSpanCol = bboxOut.maxCol - bboxOut.minCol

			// make sure the block fits inside the point space
			if (blockSpanRow > pointSpanRow || blockSpanCol > pointSpanCol) {
				return emptyList()
			}

			// just do the easy delta here
			rowDelta = bboxOut.minRow - bboxIn.minRow
			colDelta = bboxOut.minCol - bboxIn.minCol

			outputColor = nOut.blocks[0].color
		}

		val newBlock = translateBlockBy(nIn.blocks[0], rowDelta, colDelta)

		val retList = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			listOf(newBlock),
			emptyList(),
			fillColor = 0,
			overrideColors = true,
			colorToUse = outputColor
		)

		return retList
	}

	/**
	same as testTransform
	 */
	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}