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
import solutions.utilities.areAllBlocksHorizontalRows
import solutions.utilities.areAllBlocksVerticalColumns
import solutions.utilities.convertBlocksToMatrix
import solutions.utilities.hasHorizontalSymmetry
import solutions.utilities.sortBlocksByColumn
import kotlin.test.assertEquals
import kotlin.test.assertFalse


internal class TestBlockUtilities {

	@Test
	@DisplayName("test blocks to matrix function")
	fun testWeights() {
		val blocks = listOf(
			Block(1, setOf(Pair(0, 0), Pair(0, 1))),
			Block(2, setOf(Pair(0, 2), Pair(0, 3), Pair(0, 4))),
			Block(3, setOf(Pair(1, 0))),
			Block(4, setOf(Pair(1, 1), Pair(1, 2))),
			Block(5, setOf(Pair(1, 3), Pair(1, 4))),
			Block(7, setOf(Pair(2, 0), Pair(2, 1), Pair(2, 2), Pair(2, 3), Pair(2, 4)))
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

	@Test
	@DisplayName("test sort by column")
	fun testSortBlocksByColumn() {

		val blocks = listOf(
			Block(1, setOf(Pair(0, 2), Pair(1, 3))),
			Block(2, setOf(Pair(2, 1), Pair(3, 4))),
			Block(3, setOf(Pair(4, 0), Pair(5, 1)))
		)

		val sortedBlocks = sortBlocksByColumn(blocks)
		val expectedBlocks = listOf(
			Block(3, setOf(Pair(4, 0), Pair(5, 1))),
			Block(2, setOf(Pair(2, 1), Pair(3, 4))),
			Block(1, setOf(Pair(0, 2), Pair(1, 3)))
		)

		assertEquals(expectedBlocks, sortedBlocks)
	}

	@Test
	@DisplayName("test all all blocks horizontal rows")
	fun testAreAllBlocksHorizontalRows() {
		val b01 = listOf(
			Block(1, setOf(Pair(0, 0), Pair(0,1))),
			Block(2, setOf(Pair(1, 0), Pair(1, 1)))
		)
		val matrix = listOf(listOf(0, 0), listOf(0, 0))

		val result1 = areAllBlocksHorizontalRows(matrix, b01)
		assertTrue(result1)

		val b02 = listOf(
			Block(1, setOf(Pair(0, 0), Pair(0,1))),
			Block(2, setOf(Pair(1, 0)))
		)
		val result2 = areAllBlocksHorizontalRows(matrix, b02)
		assertFalse(result2)
	}

	@Test
	@DisplayName("test all all blocks vertical columns")
	fun testAreAllBlocksVerticalColumns() {
		val b01 = listOf(
			Block(1, setOf(Pair(0, 0), Pair(1, 0))),
			Block(2, setOf(Pair(0, 1), Pair(1, 1)))
		)
		val matrix = listOf(listOf(0, 0), listOf(0, 0))

		val result1 = areAllBlocksVerticalColumns(matrix, b01)
		assertTrue(result1)

		val b02 = listOf(
			Block(1, setOf(Pair(0, 0), Pair(1, 0))),
			Block(2, setOf(Pair(0, 1)))
		)
		val result2 = areAllBlocksVerticalColumns(matrix, b02)
		assertFalse(result2)
	}


}