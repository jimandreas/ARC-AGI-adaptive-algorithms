@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
	"AssignmentInCondition"
)

package solutions.transformations.smaller

import entities.coordinateSetToMatrix
import entities.findAllIsolatedThings
import entities.findTheMostOf
import entities.matrixToCoordinateSet
import entities.rankInts
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.countColorBasedOnCoordinates

// example: e50d258f entity with the most of

class S39EntityWithTheMostOf : BidirectionalBaseClass() {
	override val name: String = "entity with the most of"

	var checkedOutput = false
	var backgroundColor = 0
	var targetColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "e50d258f") {
			println("Here now")
		}

		// need at least two things
		val isolatedThings = findAllIsolatedThings(inputMatrix)
		if (isolatedThings.size  < 2) {
			return emptyList()
		}

		// figure out the background color, and then
		//   look to see if the #2 color is the winner
		//   There has to be at least a background color
		// and two competing non-background colors
		if (!checkedOutput) {
			val mostFound = findTheMostOf(inputMatrix, isolatedThings)
			// pass 1 - obtain the background color
			if (mostFound.second.isEmpty()) {  // if error, give up
				return emptyList()
			}
			backgroundColor = mostFound.first

			val outputSet = matrixToCoordinateSet(outputMatrix)
			val secondMostFound = rankInts(
				outputMatrix,
				listOf(outputSet),
				excludingMode = true,
				excludedColor = backgroundColor)

			// needs to be two secondary colors for this competition
			if (secondMostFound.isEmpty() || secondMostFound.size < 2) {
				return emptyList()
			}
			// punt for first round - just return output matrix
			//  but save the target color!

			targetColor = secondMostFound[0].first
			checkedOutput = true
			return outputMatrix
		}

		// cycle through the entities (blocks) and
		//   mark which entity has the max of the 2nd ranked color
		var colorCountMax = 0
		var indexMax = -1
		for (i in 0 until isolatedThings.size) {
			val count = countColorBasedOnCoordinates(targetColor, inputMatrix, isolatedThings[i])
			if (count > colorCountMax) {
				colorCountMax = count
				indexMax = i
			}
		}
		if (colorCountMax == 0) {
			return emptyList() // not found!
		}

		val retList = coordinateSetToMatrix(isolatedThings[indexMax], inputMatrix)

		return retList
	}

	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()
	}


}
