@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import Block
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.calculateWeights
import solutions.utilities.convertBlocksToMatrix
import solutions.utilities.findOptimalTotalWeight
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


}