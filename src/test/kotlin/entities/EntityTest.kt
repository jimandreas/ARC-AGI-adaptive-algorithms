@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

package entities

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.utilities.countColorBasedOnCoordinates
import kotlin.test.assertEquals

internal class EntityTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    @DisplayName("find isolated thing test 01")
    fun basicIsolatedThingTest() {
        val matrix = listOf(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 1, 1, 0, 0),
            listOf(0, 1, 1, 0, 0)
        )

        val isolatedEntity = findIsolatedThing(matrix)

        val expectedResult = setOf(
            Pair(1,1), Pair(1,2), Pair(2, 1), Pair(2,2 ))

        assertEquals(isolatedEntity, expectedResult)
        // Expected output: [(1, 1), (1, 2), (2, 1), (2, 2)]
    }

    @Test
    @DisplayName("find isolated things test 02")
    fun basicIsolatedMoreThanOneThingsTest() {
        val matrix = listOf(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 1, 1, 0, 0),
            listOf(0, 1, 1, 0, 0),
            listOf(0, 0, 0, 2, 2),
            listOf(0, 0, 0, 0, 0)
        )

        val allIsolatedEntities = findAllIsolatedThings(matrix)
        //println(allIsolatedEntities)
        //  Kindly provided by Google Gemini:
        // Expected output: [[(1, 1), (1, 2), (2, 1), (2, 2)], [(3, 3), (3, 4)]]

        val numList = allIsolatedEntities.size
        assertEquals(numList, 2)
        val l0 = allIsolatedEntities[0]
        val expectedResult = setOf(
            Pair(1,1), Pair(1,2), Pair(2, 1), Pair(2,2 ))
        assertEquals(l0, expectedResult)

        val l1 = allIsolatedEntities[1]
        val expectedResult1 = setOf(
            Pair(3,3), Pair(3,4))
        assertEquals(l1, expectedResult1)
    }

    @Test
    @DisplayName("rank the colors (ints) and give their quantities")
    fun rankIntsTest() {
        val m = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9)
        )

        val entities = listOf(
            setOf(Pair(0, 0), Pair(1, 1)), // 1, 5
            setOf(Pair(2, 2))               // 9
        )

        val result = rankInts(m, entities)

        val expectedResult = listOf(
            Pair(1, 1), Pair(5,1), Pair(9,1)
        )

        assertEquals(result, expectedResult)
        /// [(1, 1), (5, 1), (9, 1)]
    }


    @Test
    @DisplayName("rank the colors (ints) and give their quantities")
    fun countColorBasedOnCoordinatesTest() {
        val m = listOf(
            listOf(1, 1, 0), // count the 1's
            listOf(1, 2, 0),
            listOf(0, 0, 0)
        )

        val entity = setOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(1, 1))

        val result = countColorBasedOnCoordinates(1, m, entity)

        assertEquals(result, 3)

    }

}