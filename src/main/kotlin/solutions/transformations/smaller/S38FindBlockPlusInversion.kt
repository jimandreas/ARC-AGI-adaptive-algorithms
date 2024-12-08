@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
	"AssignmentInCondition"
)

package solutions.transformations.smaller

import Block
import Point
import entities.*
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.invertMatrixWithColorsSpecified

// example: b94a9452 find block plus inversion

class S38FindBlockPlusInversion : BidirectionalBaseClass() {
	override val name: String = "find block plus inversion"

	var checkedOutput = false
	var color1 = 0
	var color2 = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "b94a9452") {
			println("here now")
		}

		// demand two blocks or one block and one point

		if (inputBlockList.size == 2) {
			if (inputPointList.isNotEmpty()) {
				return emptyList()
			}
			color1 = inputBlockList[0].color
			color2 = inputBlockList[1].color
		} else {
			if (inputBlockList.size == 1) {
				if (inputPointList.size != 1) {
					return emptyList()
				}
				color1 = inputBlockList[0].color
				color2 = inputPointList[0].color
			}
		}

		// need ONE isolated thing
		val aThing = findIsolatedThing(inputMatrix)
		val isolatedThings = findAllIsolatedThings(inputMatrix)
		if (isolatedThings.size != 1) {
			return emptyList()
		}

		// make sure this thing has our values
		val validatedMatrix = validateThingWithBlocksAndPoints(
			isolatedThings[0], inputBlockList, inputPointList
		)

		// swap the colors
		val retMatrix = invertMatrixWithColorsSpecified(
			validatedMatrix, color1, color2)
		return retMatrix
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}

	fun validateThingWithBlocksAndPoints(
		coordinates: Set<Pair<Int, Int>>, bList: List<Block>, pList: List<Point>
	): List<List<Int>> {

		val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first }
		val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second }

		val retList: MutableList<List<Int>> = mutableListOf()
		for (row in minRow..maxRow) {
			val rowList: MutableList<Int> = mutableListOf()
			for (col in minCol..maxCol) {
				if (!isPointInBlockList(row, col, bList)
					&& !isPointInPointList(row, col, pList)
				) {
					return emptyList()
				}
				rowList.add(inputMatrix[row][col])
			}
			retList.add(rowList)
		}
		return retList
	}

	fun isPointInBlockList(row: Int, col: Int, bList: List<Block>): Boolean {
		for (b in bList) {
			if (b.coordinates.contains(Pair(row, col)))
				return true
		}
		return false
	}

	fun isPointInPointList(row: Int, col: Int, pList: List<Point>): Boolean {
		for (p in pList) {
			if (p.coordinate == Pair(row, col))
				return true
		}
		return false
	}
}
