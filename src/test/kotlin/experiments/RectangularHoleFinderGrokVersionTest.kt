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

class RectangularHoleFinderGrokVersionTest {

	lateinit var rectangularHoleFinderGrok: RectangularHoleFinderGrokVersion

	@BeforeEach
	fun setUp() {
		rectangularHoleFinderGrok = RectangularHoleFinderGrokVersion()
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

		val result = rectangularHoleFinderGrok.findEnclosedRectangle(matrix)
		println(result)

	}

	@Test
	@DisplayName("followon testing of rectangular holes")
	fun nextRectangularHoleTest() {
		val matrix = listOf(
			listOf(0, 7, 7),
			listOf(7, 7, 7),
			listOf(7, 7, 7)
		)

		val result1 = rectangularHoleFinderGrok.findEnclosedRectangle(matrix)
		println(result1)

		val matrix2 = listOf(
			listOf(7, 7, 7),
			listOf(7, 0, 7),
			listOf(7, 7, 7)
		)
		val result2 = rectangularHoleFinderGrok.findEnclosedRectangle(matrix2)
		println(result2)

//		val expectedResult: List<Set<Pair<Int, Int>>> = listOf(setOf(Pair(1, 1)))
//		if (expectedResult == holes2) {
//			println("yup")
//		}
//		println(holes2)

		val matrix3 = listOf(
			listOf(0,0,0),
			listOf(0,0,0),
			listOf(0,0,0)
		)
		val result3 = rectangularHoleFinderGrok.findEnclosedRectangle(matrix3)
		println(result3)

	}
}