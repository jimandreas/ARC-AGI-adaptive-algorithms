@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)
package testUtilityFunctions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * this solution was coded by Grok (11/2024)
 * for strings of the form:
 * "(0, 1), (0, 2), (1, 1), (1, 2)"
 */
fun splitStringIntoPairs(s: String): Set<Pair<Int, Int>> {

	// Remove any leading or trailing whitespace and parentheses
	val cleanedInput = s.trim().removeSurrounding("(", ")")

	// Split the string by "), (" to get each pair as a separate string
	val pairs = cleanedInput.split("), (")

	// Convert each split string into a Pair of Integers
	val pairList = pairs.map { s1 ->
		val (first, second) = s1.split(", ").map { it.toInt() }
		Pair(first, second)
	}

	return pairList.toSet()
}

/**
 * Kotlin: parsing a list of pairs delimited by parentheses into a list of Pairs()
 *  A solution kindly provided by @Joffrey via stackoverflow:
 *  @link: https://stackoverflow.com/a/68682137/3853712
 */
private fun String.parsePairs(): List<Pair<Int, Int>> = removePrefix("(")
	.removeSuffix(")")
	.split("), (")
	.map { it.parsePair() }

private fun String.parsePair(): Pair<Int, Int> =
	split(", ").let { it[0].toInt() to it[1].toInt() }

private fun String.parsePairsToList(): List<Int> = removePrefix("(")
	.removeSuffix(")")
	.split("), (", ", ")
	.map { it.toInt() }

private fun pairTheList(l: List<Int>): List<Pair<Int, Int>> {
	val outList: MutableList<Pair<Int, Int>> = mutableListOf()
	var i = 0
	while (i < l.size) {
		outList.add(Pair(l[i], l[i + 1]))
		i += 2
	}
	return outList
}

internal class TestUtilsTest {  // sort of a recursive name!
	@Test
	@DisplayName("test split string into pairs")
	fun testSpitStringIntoPairs() {
		val testString1 = "(1, 1), (2, 2)"
		val result1 = splitStringIntoPairs(testString1)
		// reverse the pairs to make sure the "set equivalence" works
		val expectedResult1 = setOf(Pair(2, 2), Pair(1, 1))
		assertEquals(result1, expectedResult1)
	}
}