@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)
package experiments

import MatrixDataInputAndOutput
import TaskCoordinateData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CellTranslationsAnalysisTest {

    lateinit var cellTranslations : CellTranslationsAnalysis
    @BeforeEach
    fun setUp() {
        cellTranslations = CellTranslationsAnalysis()
    }

    @Test
    @DisplayName("Basic test of cell translations analysis")
    fun cellTranslationsBasicTest() {
        val input = listOf(
            listOf(0, 5, 6, 6, 0)
        )
        val output = listOf( // identical
            listOf(5, 6, 6, 0, 0) // shift by -1 X position
        )
        val t = TaskCoordinateData(
            listOf(MatrixDataInputAndOutput(
                input, output
            )),
            listOf() // no test matrices
        )

        val result = cellTranslations.findAndGroupTranslations(t)
        println(result)

        val testIdenticalTranslations = cellTranslations.areAllTranslationsIdentical(result)
        println("Are all translations identical? $testIdenticalTranslations")
    }

    @Test
    @DisplayName("NonIdentical test of cell translations analysis")
    fun cellTranslationsNonIdenticalTest() {
        val input = listOf(
            listOf(0, 5, 6, 6, 0)
        )
        val output = listOf( // identical
            listOf(5, 6, 0, 0, 6) // shift by -1 X position, and one in +1 direction
        )
        val t = TaskCoordinateData(
            listOf(MatrixDataInputAndOutput(
                input, output
            )),
            listOf() // no test matrices
        )

        val result = cellTranslations.findAndGroupTranslations(t)
        println(result)

        val testIdenticalTranslations = cellTranslations.areAllTranslationsIdentical(result)
        println("Are all translations identical? $testIdenticalTranslations")
    }
}