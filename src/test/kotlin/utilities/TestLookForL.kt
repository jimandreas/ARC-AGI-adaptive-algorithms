@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package utilities

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.findSpanningLineWithBUMP
import kotlin.test.assertFalse


internal class TestLookForL {

	@Test
	@DisplayName("test look for block with an L at the end right")
	fun testLookForLonrightofverticalline() {
		//    val coords = setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0))  // just a line
//    val coords = setOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 0))  // line with L at left
//    val coords = setOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 2))  // line with L at right
		// vert line with L at right
		val coords = setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(2, 1))
		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertTrue(r)
	}

	@Test
	@DisplayName("test look for block with an L at the end down")
	fun testLookForLondownonhorizontalline() {
		//    val coords = setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0))  // just a line
//    val coords = setOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 0))  // line with L at left
		// line with L at right horizontal line down
		val coords = setOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 2))
		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertTrue(r)
	}

	@Test
	@DisplayName("test look for block with an L at the start down")
	fun testLookForLhorizontallinedownatstart() {
		//    val coords = setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0))  // just a line
		// line with L at left
		val coords = setOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 0))

		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertTrue(r)
	}

	@Test
	@DisplayName("test look for block with an L at the start down")
	fun testLookForLjusthorizontalline() {

		val coords = setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0))  // just a line
		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertFalse(r)
	}

	@Test
	@DisplayName("test look for block with an L middle fail case")
	fun testLookForLmiddlefailcase() {

		val coords = setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(1, 1))
		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertFalse(r)
	}

	@Test
	@DisplayName("test look for block with an L middle fail case2")
	fun testLookForLmiddlefailcase2() {

		val coords = setOf(
			Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0),
			Pair(1, 1), // false bump
			Pair(3, 1)
		)
		val r = findSpanningLineWithBUMP(coords, 4, 4)
		assertFalse(r)
	}

	@Test
	@DisplayName("test look for block with an L middle fail case2")
	fun testLookForLmiddlegoodCase2() {

		val coords = setOf(
			Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0),
			//Pair(1, 1), // false bump remove false bump
			Pair(3, 1)
		)
		val r = findSpanningLineWithBUMP(coords, 4, 4)
		assertTrue(r)
	}


	@Test
	@DisplayName("test look for block with an L middle fail case3")
	fun testLookForLmiddlefailcase3() {
		// vertical line at col 1 with bump to right
		val coords = setOf(
			Pair(0, 1), Pair(1, 1), Pair(2, 1), Pair(3, 1),
			Pair(1, 2), // false bump
			Pair(3, 2)
		)
		val r = findSpanningLineWithBUMP(coords, 4, 4)
		assertFalse(r)
	}

	@Test
	@DisplayName("test look for block with an L middle fail case3")
	fun testLookForLmiddlegoodCase3() {
		// vertical line at col 1 with bump to right
		val coords = setOf(
			Pair(0, 1), Pair(1, 1), Pair(2, 1), Pair(3, 1),
			//Pair(1, 2), // false bump
			Pair(3, 2)
		)
		val r = findSpanningLineWithBUMP(coords, 4, 4)
		assertTrue(r)
	}

	@Test
	@DisplayName("test look for block with an L middle fail case4")
	fun testLookForLmiddlegoodCase4() {
		// vertical line at col 1 with bump to left 662c240a
		val coords = setOf(
			Pair(0, 1), Pair(1, 1), Pair(2, 1), Pair(0,0),
		)
		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertTrue(r)
	}

	@Test
	@DisplayName("test look for block with an L middle fail case4")
	fun testLookForLmiddlefailCase5() {
		// vertical line at col 1 with bump to left 662c240a
		val coords = setOf(
			Pair(0, 1), Pair(1, 1), Pair(2, 1),
			Pair(1,0), // bump in middle, should fail
		)
		val r = findSpanningLineWithBUMP(coords, 3, 3)
		assertFalse(r)
	}


}