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

class RectangularHoleFinderTest {

	lateinit var rectangularHoleFinder: RectangularHoleFinder

	@BeforeEach
	fun setUp() {
		rectangularHoleFinder = RectangularHoleFinder()
	}

	@Test
	@DisplayName("basic rectangular hole as given by Google Gemini")
	fun basicRectangularHoleTest() {
		val matrix = listOf(
			listOf(1, 1, 1, 1, 1),
			listOf(1, 0, 0, 1, 1),
			listOf(1, 0, 0, 1, 1),
			listOf(1, 1, 1, 1, 1)
		)

		val holes = rectangularHoleFinder.findRectangularHoles(matrix)
		println(holes)  // Output: [[(1, 1), (1, 2), (2, 1), (2, 2)]]
	}
}