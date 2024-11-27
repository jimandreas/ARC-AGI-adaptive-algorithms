@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformationsBidirectional

// example 1fad071e  block count reporter
//   make this a bit more general - look for an output that is a List<List<Int>
//  that is a horizontal block one cell high
//  the first color is the key.   Count the number of blocks of that
//   color and return it in the style of the key

class SmallerCountBlocksOfGivenColor : BidirectionalBaseClass() {
	override val name: String = "SMALLER block count reporter"

	var blockColor = -1
	var blockColorFound = false
	var retListSize = 0

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {

		if (!blockColorFound) {
			if (outputMatrix.size != 1) {   // has to be one-dimensional (single row) array
				return emptyList()
			}
			blockColor = outputMatrix[0][0]
			retListSize = outputMatrix[0].size
			blockColorFound = true
		}

		val blocks = inputBlockList.filter{ it.color  == blockColor }
		if (blocks.size > retListSize) {
			// oops overflow - this must not be our Task
			return emptyList()
		}

		val retList: MutableList<Int> = MutableList(retListSize) { 0 } //.apply { fill(5) }
		for (i in 0 until blocks.size) {
			retList[i] = blockColor
		}
		return listOf(retList)
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}