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


}