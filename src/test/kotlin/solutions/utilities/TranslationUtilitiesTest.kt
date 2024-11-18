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
     * check that the map of translations is consistent in direction
     * The data structure is:
     *
     * groupedTranslationsMap: Map<Int, Map<Pair<Int, Int>, Int>>
     *
     *     This is a <Map<Int, Map<Pair<Int, Int>, Int>>
     *         where the first Int is the cell value,
     *         the Pair<Int,Int> shows the movement of the value in the matrix,
     *         and the ,Int at the end is the number of occurrences.
     */
    @Test
    @DisplayName("test of translation consistency")
    fun translationConsistencyTest() {

        val map = mapOf(3 to mapOf(Pair(-1, -1) to 2, Pair(-2, 2) to 1)) // Uniform but not consistent with others
        val result = isEntireMapConsistent(map) // Expected: false since not all maps are in the same direction
        assert(!result)

        val map2 = mapOf(1 to mapOf(Pair(1, 1) to 3, Pair(2, 2) to 1)) // Uniform movement
        val result2 = isEntireMapConsistent(map2) // Expected: true - all same (positive positive) direction
        assert(result2)
    }
}
