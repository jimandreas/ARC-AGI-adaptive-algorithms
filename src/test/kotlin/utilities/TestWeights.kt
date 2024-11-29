@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.calculateWeights
import solutions.utilities.findOptimalTotalWeight
import kotlin.test.assertEquals


internal class WeightsTest {
	@Test
	@DisplayName("test weights")
	fun testWeights() {
		val values = listOf(1, 1, 2, 1)
		val totalWeight = 5

		val weights = calculateWeights(values, totalWeight)
		assertEquals(weights, listOf(1, 1, 2, 1))


		val values2 = listOf(99, 101, 201, 95)
		val totalWeight2 = 5

		val weights2 = calculateWeights(values2, totalWeight2)
		println(weights2)
		assertEquals(weights2, listOf(1, 1, 2, 1))
	}

	@Test
	@DisplayName("test total weight for matrix")
	fun testTotalWeight() {
//		val matrix = listOf(
//			listOf(0, 1, 1, 0, 0),
//			listOf(0, 1, 1, 0, 0),
//			listOf(0, 0, 0, 2, 2),
//			listOf(0, 0, 2, 2, 0),
//			listOf(0, 0, 0, 0, 0)
//		)

		val matrix = listOf(
			listOf(1, 2),
			listOf(1, 1, 1),
			listOf(2, 1)
		)

		val totalWeight = findOptimalTotalWeight(matrix)
		println(totalWeight)
		assertEquals(totalWeight, 3)

	}

	@Test
	@DisplayName("test total weight for matrix")
	fun testTotalWeight3() {

		val matrix3 = listOf(
			listOf(6, 6),
			listOf(12),
			listOf(6, 6)
		)
		val totalWeight3 = findOptimalTotalWeight(matrix3)
		println(totalWeight3)
		assertEquals(totalWeight3, 2)

	}

}