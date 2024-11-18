@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName", "RedundantSuppression",
    "RedundantSuppression", "RedundantSuppression"
)

package solutions.utilities


import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TranslationUtilitiesTest {



    @BeforeEach
    fun setUp() {

    }

    /**
     * check that the list of translations is consistent in direction
     * The data structure is:
     *
     * groupedTranslationsList: List<Map<Int, Map<Pair<Int, Int>, Int>>>
     *
     *     This is a List of <Map<Int, Map<Pair<Int, Int>, Int>>
     *         where the first Int is the cell value,
     *         the Pair<Int,Int> shows the movement of the value in the matrix,
     *         and the ,Int at the end is the number of occurrences.
     */
    @Test
    @DisplayName("test of translation consistency")
    fun translationConsistencyTest() {

        val list = listOf(
            mapOf(1 to mapOf(Pair(1, 1) to 3, Pair(2, 2) to 1)), // Uniform movement
            mapOf(2 to mapOf(Pair(1, 1) to 2, Pair(2, 2) to 1)), // Uniform and consistent with the first map
            mapOf(3 to mapOf(Pair(-1, -1) to 2, Pair(-2, -2) to 1)) // Uniform but not consistent with others
        )

        val result = isEntireListConsistent(list) // Expected: false since not all maps are in the same direction
        assert(!result)

        val list2 = listOf(
            mapOf(1 to mapOf(Pair(1, 1) to 3, Pair(2, 2) to 1)), // Uniform movement
            mapOf(2 to mapOf(Pair(1, 1) to 2, Pair(2, 2) to 6)), // Uniform and consistent with the first map
            mapOf(3 to mapOf(Pair(5, 5) to 2, Pair(3, 3) to 4)) // Uniform but not consistent with others
        )
        val result2 = isEntireListConsistent(list2) // Expected: true - all same (positive positive) direction
        assert(result2)

        val list3 = listOf(
            mapOf(1 to mapOf(Pair(1, 1) to 3, Pair(2, 2) to 1)), // Uniform movement
            mapOf(2 to mapOf(Pair(1, 1) to 2, Pair(2, 2) to 6)), // Uniform and consistent with the first map
            mapOf(3 to mapOf(Pair(5, 5) to 2, Pair(3, -3) to 4)) // Uniform but not consistent with others
        )
        val result3 = isEntireListConsistent(list3) // Expected: false one exception
        assert(!result3)

        val list4 = listOf(
            mapOf(1 to mapOf(Pair(1, -1) to 3, Pair(2, -2) to 1)), // Uniform movement
            mapOf(2 to mapOf(Pair(1, -1) to 2, Pair(2, -2) to 6)), // Uniform and consistent with the first map
            mapOf(3 to mapOf(Pair(5, -5) to 2, Pair(3, -3) to 4)) // Uniform but not consistent with others
        )
        val result4 = isEntireListConsistent(list4) // Expected: true - all movements are positive / negative
        assert(result4)
    }
}
