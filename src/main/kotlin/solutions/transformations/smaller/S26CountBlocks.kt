@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass

// example: d0f5fe59 count blocks


class S26CountBlocks : BidirectionalBaseClass() {
	override val name: String = "count blocks"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "d0f5fe59") {
			// // println("here now")
		}

		// return number of blocks as downward diagonal pattern
		if (inputPointList.isNotEmpty()) {
			return emptyList()
		}
		if (inputBlockList.isEmpty()) {
			return emptyList()
		}
		val blockCount = inputBlockList.size
		val color = inputBlockList[0].color

		val array = MutableList(blockCount){ MutableList(blockCount) {0} }
		for (d in 0 until blockCount) {
			array[d][d] = color
		}
		return array

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
