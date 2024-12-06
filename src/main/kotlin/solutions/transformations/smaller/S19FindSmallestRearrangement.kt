@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.HorizontalDirection
import solutions.utilities.OpeningDirection
import solutions.utilities.VerticalDirection
import solutions.utilities.findOpeningDirection
import solutions.utilities.recreateMatrix
import solutions.utilities.relocateToOrigin
import solutions.utilities.translateBlockBy

// example: a61ba2ce find smallest rearrangement

class S19FindSmallestRearrangement : BidirectionalBaseClass() {
	override val name: String = "rearrange the blocks to the smallest connected rearragement"

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

//		if (taskName == "a61ba2ce") {
//			// // println("here now")
//		}

		data class BlockOpeningDirection(
			val b: Block,
			val od: OpeningDirection
		)
		val bodList: MutableList<BlockOpeningDirection> = mutableListOf()

		for (b in inputBlockList) {
			val relocatedBlock = relocateToOrigin(block = b)
			val coor = relocatedBlock.coordinates
			val opening = findOpeningDirection(coor)
			bodList.add(BlockOpeningDirection(b = relocatedBlock, od = opening))
			//println(opening)
		}

		// origin block opens down and right

		val blockAtOrigin = bodList.filter {
			it.od.vertical == VerticalDirection.DOWN
					&& it.od.horizontal == HorizontalDirection.RIGHT }

		if (blockAtOrigin.isEmpty()) {
			return emptyList()
		}
		val originBlock = blockAtOrigin[0].b

		val coordinates = originBlock.coordinates
		//val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first } + 1
		//val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second } + 1

		val blockAtBottomLeft = bodList.filter {
			it.od.vertical == VerticalDirection.UP
					&& it.od.horizontal == HorizontalDirection.RIGHT }
		if (blockAtBottomLeft.isEmpty()) {
			return emptyList()
		}
		var bottomLeftBlock = blockAtBottomLeft[0].b
		bottomLeftBlock = translateBlockBy(bottomLeftBlock, maxRow, 0) // shove down

		val blockAtUpperRight   = bodList.filter {
			it.od.vertical == VerticalDirection.DOWN
					&& it.od.horizontal == HorizontalDirection.LEFT }
		if (blockAtUpperRight.isEmpty()) {
			return emptyList()
		}
		var upperRightBlock = blockAtUpperRight[0].b
		upperRightBlock = translateBlockBy(upperRightBlock, 0, maxCol) // shove right

		val blockAtLowerRight = bodList.filter {
			it.od.vertical == VerticalDirection.UP
					&& it.od.horizontal == HorizontalDirection.LEFT }
		if (blockAtLowerRight.isEmpty()) {
			return emptyList()
		}
		var bottomRightBlock = blockAtLowerRight[0].b
		bottomRightBlock = translateBlockBy(bottomRightBlock, maxRow, maxCol) // shove down and right

		val compositeBlockList = listOf(originBlock, bottomLeftBlock, upperRightBlock, bottomRightBlock)
		val maxRowComp =bottomRightBlock.coordinates.maxOf { it.first }
		val maxColComp = bottomRightBlock.coordinates.maxOf { it.second }
		val retMatrix = recreateMatrix(
			maxRowComp + 1,
			maxColComp + 1,
			compositeBlockList, emptyList())
		return retMatrix

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
