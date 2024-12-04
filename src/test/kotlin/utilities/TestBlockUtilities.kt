@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import Block
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.convertBlocksToMatrix
import solutions.utilities.hasHorizontalSymmetry
import kotlin.test.assertEquals


internal class TestBlockUtilities {

	@Test
	@DisplayName("test blocks to matrix function")
	fun testWeights() {
		val blocks = listOf(
			listOf(
				Block(1, setOf(Pair(0, 0), Pair(0, 1))),
				Block(2, setOf(Pair(0, 2), Pair(0, 3), Pair(0, 4)))
			),
			listOf(
				Block(3, setOf(Pair(1, 0))),
				Block(4, setOf(Pair(1, 1), Pair(1, 2))),
				Block(5, setOf(Pair(1, 3), Pair(1, 4)))
			),
			listOf(Block(7, setOf(Pair(2, 0), Pair(2, 1), Pair(2, 3), Pair(2, 3), Pair(2, 4))))
		)

		val matrix = convertBlocksToMatrix(blocks)
		println(matrix)
		// [[1, 1, 2, 2, 2], [3, 4, 4, 5, 5], [7, 7, 7, 7, 7]]
		val expectedList = listOf(
			listOf(1, 1, 2, 2, 2),
			listOf(3, 4, 4, 5, 5),
			listOf(7, 7, 7, 7, 7)
		)
		assertEquals(matrix, expectedList)
	}


	@Test
	@DisplayName("test symmetry 1")
	fun testSymmetry1() {
		val figure = setOf(Pair(0, 0), Pair(0, 1))
		val result = hasHorizontalSymmetry(figure)
		assertTrue(result)
	}

	@Test
	@DisplayName("test symmetry 2")
	fun testSymmetry2() {
		val figure = setOf(
			Pair(0, 0), Pair(0, 1),
			Pair(1, 0), Pair(1, 1)
		)
		val result = hasHorizontalSymmetry(figure)
		assertTrue(result)
	}

	@Test
	@DisplayName("test symmetry 3")
	fun testSymmetry3() {
		val figure = setOf(
			Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3),
			Pair(1, 1), Pair(1, 2)
		)
		val result = hasHorizontalSymmetry(figure)
		assertTrue(result)
	}

	@Test
	@DisplayName("test symmetry 4")
	fun testSymmetry4() {
		val figure = setOf(
			Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3),
			Pair(1, 1), Pair(1, 2), Pair(1, 3)
		)
		val result = hasHorizontalSymmetry(figure)
		assertEquals(result, false)
	}

	@Test
	@DisplayName("test symmetry 5")
	fun testSymmetry5() {
		val figure = setOf(
			Pair(0, 0), Pair(0, 1), Pair(0, 2),
			Pair(1, 1)
		)
		val result = hasHorizontalSymmetry(figure)
		assertTrue(result)
	}

}