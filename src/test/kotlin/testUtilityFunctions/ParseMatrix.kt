@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package testUtilityFunctions

import compareMatrices
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue


fun parseTestMatrix(strList: List<String>): List<List<Int>> {
	val retList: MutableList<List<Int>> = mutableListOf()

	val size = strList.size
	for (i in 0 until size) {
		val tokens = strList[i].split("\\s+".toRegex())
		val rowList: MutableList<Int> = mutableListOf()
		for (j in 0 until size) {
			rowList.add(tokens[j].toInt())
		}
		retList.add(rowList)
	}

	return retList
}

internal class TestParseMatrix {
	@Test
	@DisplayName("test parseTestMatrix")
	fun testParseTestMatrix() {
		val matrixString = """
			0 0 0 0
			0 1 2 3
			0 0 0 0
			3 2 1 0
        """.trimIndent().lines()

		val retList = parseTestMatrix(matrixString)

		val expectedList = listOf(
			listOf(0, 0, 0, 0),
			listOf(0, 1, 2, 3),
			listOf(0, 0, 0, 0),
			listOf(3, 2, 1, 0)
		)

		val result = compareMatrices(retList, expectedList)
		assertTrue(result)
	}
}

