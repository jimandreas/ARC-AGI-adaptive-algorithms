@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.recreateMatrix

// example: E05 aedd82e4 color points in output

class E05ColorPointsInOutput : BidirectionalBaseClass() {
	override val name: String = "color points in output"

	var checkedOutput = false
	var colorPointsAlgoValid = false
	var outputPointColor = 0

	override fun resetState() {
		checkedOutput = false
		colorPointsAlgoValid = false
	}

	// basically color the points as given in the first example output
	override fun testTransform(): List<List<Int>> {


		if (inputPointList.isEmpty()) {
			return emptyList()
		}
		if (!checkedOutput) {
			checkedOutput = true
			if (outputPointList.isEmpty()) {
				return emptyList()
			}
			val cIn = inputPointList[0].color
			val cOut = outputPointList[0].color
			if (cIn == cOut) {
				return emptyList()
			}
			// try the transformation - change all input points to the
			// output point color
			val pList : MutableList<Point> = mutableListOf()
			for (p in inputPointList) {
				pList.add(Point(cOut, p.coordinate ))
			}

			val retArray = recreateMatrix(
				inputMatrix.size,
				inputMatrix[0].size,
				inputBlockList,
				pList)
			if (retArray == outputMatrix) {
				colorPointsAlgoValid = true
				outputPointColor = cOut
				return retArray
			}
		}

		if (colorPointsAlgoValid) {
			val pList : MutableList<Point> = mutableListOf()
			for (p in inputPointList) {
				pList.add(Point(outputPointColor, p.coordinate ))
			}

			val retArray = recreateMatrix(
				inputMatrix.size,
				inputMatrix[0].size,
				inputBlockList,
				pList)

			return retArray
		}
		return emptyList()
	}

	/**
	 same as testTransform
	 */
	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}