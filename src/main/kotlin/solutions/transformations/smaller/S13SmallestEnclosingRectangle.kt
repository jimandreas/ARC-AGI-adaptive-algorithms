@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.changeMatrixColor
import solutions.utilities.computeBoundingBox
import solutions.utilities.returnSubmatrix

// example: a740d043 smallest enclosing rectangle

class S13SmallestEnclosingRectangle : BidirectionalBaseClass() {
	override val name: String = "**** smallest enclosing rectangle"

	var checkedOutput = false
	var backgroundColor = -1
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (!checkedOutput) {
			 // set background color here
			checkedOutput = true
		}

		if (inputBlockList.isEmpty()) {
			return emptyList()
		}
		val firstBlock = inputBlockList[0]
		// assume the first Block is a big one and is the backgroun
		//  color for this Task.  It needs to get set to 0

		val amountOfBackground = firstBlock.coordinates.size.toFloat()
		val matrixSize = inputMatrix.size * inputMatrix[0].size

		if (amountOfBackground / matrixSize.toFloat() < 0.4f) {
			// just a wild guess at a discriminator for how much background we should see
			return emptyList()
		}
		val newInputMatrix = changeMatrixColor(firstBlock.color, 0, inputMatrix)

		val newBlockList = inputBlockList.drop(1)  // lose the background

		val bb = computeBoundingBox(newBlockList, inputPointList)
		val retMatrix = returnSubmatrix(newInputMatrix, bb)

		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
