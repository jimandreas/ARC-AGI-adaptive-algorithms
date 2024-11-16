@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName", "RedundantSuppression",
	 "RedundantSuppression", "UNUSED_EXPRESSION"
)

package experiments

import MatrixDataInputAndOutput
import TaskCoordinateData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import solutions.CellTranslationsAnalysis
import verboseFlag

class CellTranslationsAnalysisTest {

	lateinit var cellTranslations: CellTranslationsAnalysis

	@BeforeEach
	fun setUp() {
		cellTranslations = CellTranslationsAnalysis()
	}

	/**
	 * What is interesting -
	 *    The algorithm misses that a cell was added in the "output" matrix.
	 *    This doesn't register as a "translation" as it popped up in the
	 *    output and doesn't exist in the input.
	 *
	 *    Is this a "feature" or a "bug"?
	 *    I think I will add an override to see what happens when these
	 *    "popups" are excluded.
	 */
	@Test
	@DisplayName("Basic test of cell translations analysis")
	fun cellTranslationsBasicTest() {
		val input = listOf(
			listOf(0, 5, 6, 6, 0)
		)
		val output = listOf( // identical
			listOf(5, 6, 6, 0, 7) // shift by -1 X position, plus a popped up value
		)
		val t = TaskCoordinateData(
			listOf(
				MatrixDataInputAndOutput(
					input, output
				)
			),
			listOf() // no test matrices
		)

		val result = cellTranslations.findAndGroupTranslations(t)
		if (verboseFlag) println(result)

		val testIdenticalTranslations = cellTranslations.areAllTranslationsIdentical(result)
		if (verboseFlag) "Are all translations identical? $testIdenticalTranslations"
		assert(testIdenticalTranslations)
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
			listOf(
				MatrixDataInputAndOutput(
					input, output
				)
			),
			listOf() // no test matrices
		)

		val result = cellTranslations.findAndGroupTranslations(t)
		println(result)

		val testIdenticalTranslations = cellTranslations.areAllTranslationsIdentical(result)
		// println("Are all translations identical? $testIdenticalTranslations")
		assert(!testIdenticalTranslations)
	}
}