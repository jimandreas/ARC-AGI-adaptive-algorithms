@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.checkBlockSpan
import solutions.utilities.convertList

// example: 4be741c5
//   return a stacked color set based on bands of color with directionality

class S10ColorBandRepresentation : BidirectionalBaseClass() {
	override val name: String = "SMALLER color band summary"

	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {

		// check characteristics of input and output
		//   just a list of points for output
		// bands of colors on input

		if (outputBlockList.isNotEmpty()) {
			return emptyList()
		}
		val inputBlockColors = inputBlockList.map { it.color }

		if (inputBlockColors.size != outputPointList.size) {
			return emptyList()
		}

		val blockSpanIndicator = checkBlockSpan(inputMatrix, inputBlockList)
		when (blockSpanIndicator.first) {
			0 -> return emptyList()
			1-> {
				val theList = blockSpanIndicator.second
				return listOf(theList)
			}
			2-> {
				val theList = blockSpanIndicator.second
				val retList = convertList(theList)
				return retList
			}
		}
		return emptyList()
	}

	override fun returnTestOutput(): List<List<Int>> {
		val blockSpanIndicator = checkBlockSpan(inputMatrix, inputBlockList)
		when (blockSpanIndicator.first) {
			0 -> return emptyList()
			1-> {
				val theList = blockSpanIndicator.second
				return listOf(theList)
			}
			2-> {
				val theList = blockSpanIndicator.second
				val retList = convertList(theList)
				return retList
			}
		}
		return emptyList()
	}
}