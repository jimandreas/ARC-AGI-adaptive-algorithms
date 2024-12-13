@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Point
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findMajorityColorInMatrix
import solutions.utilities.findMinMaxPointCoordinates
import solutions.utilities.recreateMatrix

// example: S42 c8cbb738 clever merging of all points into gestalt

class S42CleverMergingOfPoints : BidirectionalBaseClass() {
	override val name: String = "clever merging of all points into gestalt"

	var checkedOutput = false
	var outputColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	// just short out the largest by coordinates blocks and return
	// them as colored stripes

	override fun testTransform(): List<List<Int>> {

//		if (taskName == "c8cbb738") {
//			//println("here now")
//		}

		if (inputPointList.isEmpty()) {
			return emptyList()
		}

		//val pByColorGrouping = inputPointList.groupBy() { it.color }

		// Group points by color and filter groups with exactly 4 points
		val filteredGroups = inputPointList.groupBy { it.color }
			.filter { (_, colorPoints) -> colorPoints.size == 4 }

		// Flatten the map back into a list of points
		val prunedPointList =  filteredGroups.values.flatten()
		val pByColor = prunedPointList.sortedBy { it.color }

		outputColor = findMajorityColorInMatrix(inputMatrix)

		// the max overall row and col spans
		//   will determine the small offset
		//   in relocating the point cluster to 0,0
		val minMaxMap = findMinMaxPointCoordinates(prunedPointList)
		var maxRowSpan = 0
		var maxColSpan = 0
		for (key in minMaxMap.keys) {
			val spans = minMaxMap[key]
			if (spans == null) {
				return emptyList()
			}
			val rowSpan = spans.first.second - spans.first.first
			val colSpan = spans.second.second - spans.second.first

			if (rowSpan > maxRowSpan) {
				maxRowSpan = rowSpan
			}
			if (colSpan > maxColSpan) {
				maxColSpan = colSpan
			}
		}

		val reclocatedPoints: MutableList<Point> = mutableListOf()
		for (p in pByColor) {
			val spans = minMaxMap[p.color]
			if (spans == null) {
				return emptyList()
			}

			var row = p.coordinate.first
			var col = p.coordinate.second
			var adjustment = 0

			row = row - spans.first.first // subtract min
			adjustment = spans.first.second - spans.first.first
			adjustment = (maxRowSpan - adjustment) / 2
			row = row + adjustment

			col = col - spans.second.first // subtract min col
			adjustment = spans.second.second - spans.second.first
			adjustment = (maxColSpan - adjustment) / 2
			col = col + adjustment

			val newP = Point(p.color, Pair(row, col))
			reclocatedPoints.add(newP)
		}

		val retMatrix = recreateMatrix(
			maxRowSpan + 1,
			maxColSpan + 1,
			emptyList(),
			reclocatedPoints,
			outputColor
		)
		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}


}
