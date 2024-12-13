@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.convertBlocksToMatrix
import solutions.utilities.createVerticalMatrixWithColors
import solutions.utilities.sortBlocksByColumn
import solutions.utilities.translateBlockBy
import solutions.utilities.translateBlocksToOrigin

// example: S41 a3325580 biggest blocks returned as color stripe

class S41BiggestBlocksReturnedAsStripe : BidirectionalBaseClass() {
	override val name: String = "biggest blocks returned as color stripe"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	// just short out the largest by coordinates blocks and return
	// them as colored stripes

	override fun testTransform(): List<List<Int>> {

		// no points allowed
		if (inputPointList.isNotEmpty()) {
			return emptyList()
		}
		if (inputBlockList.size < 2) {
			return emptyList()
		}

		val sortedBlocks = inputBlockList.sortedByDescending(){ it.coordinates.size }
		val biggestSize = sortedBlocks[0].coordinates.size

		val biggestList = sortedBlocks.filter { it.coordinates.size == biggestSize }

		val biggestLeftToRight = sortBlocksByColumn(biggestList)

		val colors = biggestLeftToRight.map { it.color }

		val retMatrix = createVerticalMatrixWithColors(biggestSize, colors.size, colors)

		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
