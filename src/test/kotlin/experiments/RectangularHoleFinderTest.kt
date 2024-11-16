@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName", "RedundantSuppression",
	"RedundantSuppression", "UNUSED_EXPRESSION"
)

package experiments

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

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

	@Test
	@DisplayName("followon testing of rectangular holes")
	fun nextRectangularHoleTest() {
		val matrix = listOf(
			listOf(0, 7, 7),
			listOf(7, 7, 7),
			listOf(7, 7, 7)
		)

		val holes = rectangularHoleFinder.findRectangularHoles(matrix)
		println(holes)
	}
}