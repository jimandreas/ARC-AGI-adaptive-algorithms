@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.SpiralDirectionChatgpt
import solutions.utilities.detectSpiral
import solutions.utilities.generateSpiral
import kotlin.test.assertEquals


internal class TestSpiral {

	@Test
	@DisplayName("test spiral detection")
	fun testSpiralDetection() {

		// Example Matrix with CLOCKWISE spiral
		val clockwiseMatrix = listOf(
			listOf(1, 1, 1, 1, 1),
			listOf(0, 0, 0, 0, 1),
			listOf(1, 1, 1, 0, 1),
			listOf(1, 0, 0, 0, 1),
			listOf(1, 1, 1, 1, 1)
		)

		// this test case is lefted from 28e73c20
		val clockwiseMatrix2 = listOf(
			listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
			listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
			listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
			listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
			listOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1),
			listOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
			listOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
			listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
			listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
		)

		// Example Matrix with COUNTERCLOCKWISE spiral
		val counterClockwiseMatrix = listOf(
			listOf(1, 1, 1, 1, 1),
			listOf(1, 0, 0, 0, 0),
			listOf(1, 0, 1, 1, 1),
			listOf(1, 0, 0, 0, 1),
			listOf(1, 1, 1, 1, 1)
		)

		// Example Matrix with no spiral
		val noSpiralMatrix = listOf(
			listOf(1, 0, 1),
			listOf(0, 1, 0),
			listOf(1, 0, 1)
		)

		val result1 = detectSpiral(clockwiseMatrix)
		assertEquals(result1,SpiralDirectionChatgpt.CLOCKWISE )

		val result2 = detectSpiral(clockwiseMatrix2)
		assertEquals(result2,SpiralDirectionChatgpt.CLOCKWISE )

		val result3 = detectSpiral(counterClockwiseMatrix)
		assertEquals(result3,SpiralDirectionChatgpt.COUNTERCLOCKWISE )

		val result4 = detectSpiral(noSpiralMatrix)
		assertEquals(result4,SpiralDirectionChatgpt.NONE )

//			println("Clockwise Matrix2: ${detectSpiral(clockwiseMatrix2)}") // Expected: CLOCKWISE
//			println("Clockwise Matrix: ${detectSpiral(clockwiseMatrix)}") // Expected: CLOCKWISE
//			println("CounterClockwise Matrix: ${detectSpiral(counterClockwiseMatrix)}") // Expected: COUNTERCLOCKWISE
//			println("No Spiral Matrix: ${detectSpiral(noSpiralMatrix)}") // Expected: NONE
	}

	@Test
	@DisplayName("test spiral generation")
	fun testSpiralGeneration() {

		// this test case is lefted from 28e73c20
		val clockwiseMatrix6Count = listOf(
			listOf( 1, 1, 1, 1, 1, 1),
			listOf( 0, 0, 0, 0, 0, 1),
			listOf( 1, 1, 1, 1, 0, 1),
			listOf( 1, 0, 1, 1, 0, 1),
			listOf( 1, 0, 0, 0, 0, 1),
			listOf( 1, 1, 1, 1, 1, 1))

		val result6Count = generateSpiral(6, 1, clockwise = true)
//		if (result6Count == clockwiseMatrix6Count) {
//			println("WINNER6Count")
//		}
		assertEquals(result6Count, clockwiseMatrix6Count)

		/*
		0 1 2 3 4 5 6 7
	0   X X X X X X X X
	1   O O O O O O O X
	2   X X X X X X O X
	3   X O O O O X O X
	4   X O X X O X O X
	5   X O X X X X O X
	6   X O O O O O O X
	7   X X X X X X X X
		0 1 2 3 4 5 6 7
		 */

		val clockwiseMatrix8count = listOf(
			listOf( 1, 1, 1, 1, 1, 1, 1, 1),
			listOf( 0, 0, 0, 0, 0, 0, 0, 1),
			listOf( 1, 1, 1, 1, 1, 1, 0, 1),
			listOf( 1, 0, 0, 0, 0, 1, 0, 1),
			listOf( 1, 0, 1, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 1, 1, 1, 0, 1),
			listOf( 1, 0, 0, 0, 0, 0, 0, 1),
			listOf( 1, 1, 1, 1, 1, 1, 1, 1)
		)
		val result8count = generateSpiral(8, 1, clockwise = true)
//		if (result8count == clockwiseMatrix8count) {
//			println("WINNER 8 Count")
//		}
		assertEquals(result8count, clockwiseMatrix8count)

		val clockwiseMatrix5 = listOf(
			listOf(1, 1, 1, 1, 1),
			listOf(0, 0, 0, 0, 1),
			listOf(1, 1, 1, 0, 1),
			listOf(1, 0, 0, 0, 1),
			listOf(1, 1, 1, 1, 1)
		)

		val result5 = generateSpiral(5, 1, clockwise = true)
//		if (result5 == clockwiseMatrix5) {
//			println("WINNER 5 count")
//		}

		assertEquals(result5, clockwiseMatrix5)


		// this test case is lefted from 28e73c20
		val clockwiseMatrix15count = listOf(
			listOf( 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
			listOf( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
			listOf( 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
			listOf( 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
			listOf( 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1),
			listOf( 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
			listOf( 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
			listOf( 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
			listOf( 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
		)

		val result15Count = generateSpiral(15, 1, clockwise = true)
//		if (result15Count == clockwiseMatrix15count) {
//			println("WINNER 15 count")
//		}
		assertEquals(result15Count, clockwiseMatrix15count)

		// Example Matrix with COUNTERCLOCKWISE spiral

		// COUNTERCLOCKWISE spirals NEED WORK
/*		val counterClockwiseMatrix = listOf(
			listOf(1, 1, 1, 1, 1),
			listOf(1, 0, 0, 0, 0),
			listOf(1, 0, 1, 1, 1),
			listOf(1, 0, 0, 0, 1),
			listOf(1, 1, 1, 1, 1)
		)

		// Example Matrix with no spiral
		val noSpiralMatrix = listOf(
			listOf(1, 0, 1),
			listOf(0, 1, 0),
			listOf(1, 0, 1)
		)*/

	}

}