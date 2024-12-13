@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.computeBoundingBox
import solutions.utilities.recreateMatrix
import solutions.utilities.translateBlockBy

// example: E06 a1570a43 center the block using points
// TODO: would more intelligent centering help? E06 a1570a43 center the block using points

class E06CenterBlockUsingPoints : BidirectionalBaseClass() {
	override val name: String = "center the block using points"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	// demand 4 points and a block.
	//   then move the block to the middle of the points
	override fun testTransform(): List<List<Int>> {

		if (taskName == "a1570a43") {
			//println("here now")
		}
		if (inputPointList.size != 4) {
			return emptyList()
		}
		if (inputBlockList.size != 1) {
			return emptyList()
		}
		val bbox = computeBoundingBox(emptyList(), inputPointList)
		val bboxBlock = computeBoundingBox(inputBlockList, emptyList())

		val pointSpanRow = bbox.maxRow - bbox.minRow
		val blockSpanRow = bboxBlock.maxRow - bboxBlock.minRow

		val pointSpanCol = bbox.maxCol - bbox.minCol
		val blockSpanCol = bboxBlock.maxCol - bboxBlock.minCol

		// make sure the block fits inside the point space
		if (blockSpanRow > pointSpanRow || blockSpanCol > pointSpanCol) {
			return emptyList()
		}

		// just do the easy delta here
		val rowDelta = bbox.minRow - bboxBlock.minRow + 1
		val colDelta = bbox.minCol - bboxBlock.minCol + 1

		val newBlock = translateBlockBy(inputBlockList[0], rowDelta, colDelta)

		val retList = recreateMatrix(
			inputMatrix.size,
			inputMatrix[0].size,
			listOf(newBlock),
			inputPointList
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