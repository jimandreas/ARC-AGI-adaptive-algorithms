@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.convertToRelativeCoordinates
import solutions.utilities.recreateMatrix

// E10 a9f96cdd input point to output constellation

class E10InputPointToOutputConstellation : BidirectionalBaseClass() {
	override val name: String = "input point to output constellation"

	var checkedOutput = false
	data class PointToConstellation(
		val thePoint: Point,
		val constellation: List<Point>
	)
	lateinit var rData : PointToConstellation

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {



		if (inputBlockList.isNotEmpty() || outputBlockList.isNotEmpty()) {
			return emptyList()
		}
		// demand just one input point and up to 4 output points
		if (inputPointList.size != 1 || outputPointList.size > 4) {
			return emptyList()
		}

		if (!checkedOutput) {
			checkedOutput = true
			// set up null constellation data
			rData = PointToConstellation(
				thePoint =  Point(0, Pair(0,0)),
				constellation = emptyList())
		}

		if (outputPointList.size == 4) {
			val relativeList = convertToRelativeCoordinates(
				inputPointList[0], outputPointList
			)
			rData = PointToConstellation(
				thePoint =  inputPointList[0],
				constellation =  relativeList)
		}
		return outputMatrix
	}

	/**
	Use the saved point to constellation data if valid
	    Recreate the constellation but clip it to the matrix size!
	 */
	override fun returnTestOutput(): List<List<Int>> {
		if (rData.constellation.size != 4) { // four points in the constellation!
			return emptyList()
		}
		if (inputBlockList.isNotEmpty() || inputPointList.size != 1) {
			return emptyList()
		}
		val guideRow = inputPointList[0].coordinate.first
		val guideCol = inputPointList[0].coordinate.second

		val rowCount = inputMatrix.size
		val colCount = inputMatrix[0].size

		val newPointList : MutableList<Point> = mutableListOf()
		for (i in 0 until 4) {
			val p = rData.constellation[i]
			val newRow = p.coordinate.first + guideRow
			val newCol = p.coordinate.second + guideCol

			if (newRow < 0 || newRow >= rowCount) {
				continue
			}
			if (newCol < 0 || newCol >= colCount) {
				continue
			}
			val newP = Point(p.color, Pair(newRow, newCol))
			newPointList.add(newP)
		}

		val retArray = recreateMatrix(
			rowCount, colCount,
			emptyList(),
			newPointList)

		return retArray

	}


}