@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import Block
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.connectionBetweenTwoBlocks
import solutions.utilities.recreateMatrix


internal class TestConnectionBetweenTwoBlocks {

	@Test
	@DisplayName("test connection between two blocks 1")
	fun testConnectionBetweenTwoBlocks01() {
		val b1 = Block(1, setOf(Pair(0,0), Pair(0,1), Pair(1,0), Pair(0,1)))
		val b2 = Block(1, setOf(Pair(8,8), Pair(8,9), Pair(9,8), Pair(9,9)))
		val m = recreateMatrix(10, 10, listOf(b1, b2), emptyList())

		val result = connectionBetweenTwoBlocks(m, b1, b2)
		assertFalse(result)
	}
	
	@Test
	@DisplayName("test connection between two blocks 2")
	fun testConnectionBetweenTwoBlocks02() {
		val b1 = Block(1, setOf(Pair(0,0), Pair(0,1), Pair(1,0), Pair(0,1)))
		val b2 = Block(1, setOf(Pair(8,8), Pair(8,9), Pair(9,8), Pair(9,9)))
		
		// the connection:  across the top and then down
		val c1 = Block(2, setOf(
			Pair(0,2),
			Pair(0,3),
			Pair(0,4),
			Pair(0,5),
			Pair(0,6),
			Pair(0,7),
			Pair(0,8),
			Pair(0,9)
		))
		val c2 = Block(2, setOf(
			Pair(1,9),
			Pair(2,9),
			Pair(3,9),
			Pair(4,9),
			Pair(5,9),
			Pair(6,9),
			Pair(7,9)
		))
		val m = recreateMatrix(10, 10, listOf(b1, b2, c1, c2), emptyList()).toMutableList()

		val result = connectionBetweenTwoBlocks(m, b1, b2)
		assertTrue(result)

	}

	@Test
	@DisplayName("test connection between two blocks 3")
	fun testConnectionBetweenTwoBlocks03() {
		val b1 = Block(1, setOf(Pair(0,0), Pair(0,1), Pair(1,0), Pair(0,1)))
		val b2 = Block(1, setOf(Pair(8,8), Pair(8,9), Pair(9,8), Pair(9,9)))

		// the connection:
		val c1 = Block(2, setOf(
			Pair(0,2),
			Pair(0,3),
			Pair(0,4),
			Pair(0,5),
			Pair(0,6),
			Pair(0,7),
			Pair(0,8),
			Pair(0,9)
		))
		val c2 = Block(2, setOf(
			Pair(1,9),
			Pair(2,9),
			Pair(3,9),
			Pair(4,9),
			Pair(5,9),
			Pair(6,9)/*,  remove one point
			Pair(7,9)*/
		))
		val m = recreateMatrix(10, 10, listOf(b1, b2, c1, c2), emptyList()).toMutableList()

		val result = connectionBetweenTwoBlocks(m, b1, b2)
		assertFalse(result)

	}
}