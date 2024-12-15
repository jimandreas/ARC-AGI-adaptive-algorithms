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
import solutions.utilities.createVerticalMatrixWithColors
import solutions.utilities.hasHorizontalSymmetry
import solutions.utilities.sortBlocksByColumn
import kotlin.math.exp
import kotlin.test.assertEquals

internal class TestMatrixUtilities {

	@Test
	@DisplayName("test create vertical matrix with colors")
	fun testCreateVerticalMatrixWithColors() {
		// Example usage:
		val rowSize = 3
		val colSize = 4
		val colors = listOf(1, 2, 3, 4)

		val result = createVerticalMatrixWithColors(rowSize, colSize, colors)

		val expectedMatrix = listOf(
			listOf(1, 2, 3, 4),
			listOf(1, 2, 3, 4),
			listOf(1, 2, 3, 4)
		)
		assertEquals(expectedMatrix, result)
	}


}