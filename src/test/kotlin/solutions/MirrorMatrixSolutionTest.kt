@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName", "RedundantSuppression",
    "RedundantSuppression", "RedundantSuppression"
)

package solutions

import compareMatrices
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

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
        val result = compareMatrices(output, expectedOutput)

        val failInput = listOf(
            listOf(0, 1, 2),
            listOf(0, 1, 0),
            listOf(1, 0, 1),
            listOf(0, 1, 0),
            listOf(0, 1, 3)
        )

        assert(result)

        val failOutput = mirrorAlg.mirrorMatrixVertically(failInput)
        val failResult = compareMatrices(failOutput, expectedOutput)

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
        val result = compareMatrices(output, expectedOutput)

        val failInput = listOf(
            listOf(2, 0, 3),
            listOf(1, 0, 1),
            listOf(0, 1, 0)
        )

        assert(result)

        val failOutput = mirrorAlg.mirrorMatrixHorizontally(failInput)
        val failResult = compareMatrices(failOutput, expectedOutput)

        assert(!failResult)

    }

    @Test
    fun mirrorMatrixDownwardDiagonalTest() {
        val matrix = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9)
        )

        val result = mirrorAlg.mirrorMatrixDownwardDiagonal(matrix)

        println("Mirrored Downward:")
        println(result)

        val matrixExpected = listOf(
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(3, 6, 9)
        )

        val areTheyEqual = compareMatrices(result, matrixExpected)
        println("are they equal? $areTheyEqual")

    }

    @Test
    fun mirrorMatrixUpwardDiagonalTest() {
        val matrix = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9)
        )

        val mirroredUpward = mirrorAlg.mirrorMatrixUpwardDiagonal(matrix)
        println("Mirrored Upward:")
        println(mirroredUpward)

        val matrixExpected = listOf(
            listOf(9, 6, 3),
            listOf(8, 5, 2),
            listOf(7, 4, 1)
        )

        val areTheyEqual = compareMatrices(mirroredUpward, matrixExpected)
        println("are they equal? $areTheyEqual")
    }
}
