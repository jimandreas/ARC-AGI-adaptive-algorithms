@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package experiments

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName

class MirrorMatrixSolutionTest {

    private lateinit var mirrorAlg: MirrorMatrixSolution

    @BeforeEach
    fun setUp() {
        mirrorAlg = MirrorMatrixSolution()
    }

    /**
     * just a quick check to make sure the inversion of the matrices
     * produces expected results
     */
    @Test
    @DisplayName("test of basic matrix inversion vertically")
    fun mirrorMatrixVertically() {
        val input = listOf(
            listOf(0, 1, 2),
            listOf(0, 1, 0),
            listOf(1, 0, 1),
            listOf(0, 1, 0),
            listOf(0, 1, 2)
        )

        val output = mirrorAlg.mirrorMatrixVertically(input)

        val expectedOutput = listOf(
            listOf(0, 1, 2),
            listOf(0, 1, 0),
            listOf(1, 0, 1),
            listOf(0, 1, 0),
            listOf(0, 1, 2)
        )
        val result = mirrorAlg.compareMatrices(output, expectedOutput)

        val failInput = listOf(
            listOf(0, 1, 2),
            listOf(0, 1, 0),
            listOf(1, 0, 1),
            listOf(0, 1, 0),
            listOf(0, 1, 3)
        )

        assert(result)

        val failOutput = mirrorAlg.mirrorMatrixVertically(failInput)
        val failResult = mirrorAlg.compareMatrices(failOutput, expectedOutput)

        assert(!failResult)

    }


    /**
     * just a quick check to make sure the inversion of the matrices
     * produces expected results
     */
    @Test
    @DisplayName("test of basic matrix inversion horizontally")
    fun mirrorMatrixHorizontally() {
        val input = listOf(
            listOf(2, 0, 2),
            listOf(1, 0, 1),
            listOf(0, 1, 0)
        )

        val output = mirrorAlg.mirrorMatrixHorizontally(input)

        val expectedOutput = listOf(
            listOf(2, 0, 2),
            listOf(1, 0, 1),
            listOf(0, 1, 0),
        )
        val result = mirrorAlg.compareMatrices(output, expectedOutput)

        val failInput = listOf(
            listOf(2, 0, 3),
            listOf(1, 0, 1),
            listOf(0, 1, 0)
        )

        assert(result)

        val failOutput = mirrorAlg.mirrorMatrixHorizontally(failInput)
        val failResult = mirrorAlg.compareMatrices(failOutput, expectedOutput)

        assert(!failResult)

    }
}