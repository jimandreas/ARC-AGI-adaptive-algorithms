@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import Point
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.findMinMaxPointCoordinates
import kotlin.test.assertEquals


internal class TestPointUtilities {

	@Test
	@DisplayName("test point list min/max function")
	fun testWeights() {
		val points = listOf(
			Point(1, Pair(1, 3)),
			Point(1, Pair(1, 4)),
			Point(1, Pair(2, 3)),
			Point(1, Pair(2, 4)),
			Point(2, Pair(5, 7)),
			Point(2, Pair(10, 7)),
			Point(2, Pair(10, 5)),
			Point(2, Pair(5, 5))
		)

		val result = findMinMaxPointCoordinates(points)

		val expectedResult: Map<Int, Pair<Pair<Int, Int>, Pair<Int, Int>>> =
			mapOf(
				1 to Pair(Pair(1, 2), Pair(3, 4)),
				2 to Pair(Pair(5, 10), Pair(5, 7)))

		assertEquals(result, expectedResult)
//		if (result == expectedResult) {
//			println("YES")
//		}
//
//		result.forEach { (color, coords) ->
//			println("Color $color: Rows=${coords.first}, Columns=${coords.second}")
//		}
	}



}