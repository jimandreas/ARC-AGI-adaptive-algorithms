@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package experiments

import TaskCoordinateData
import tTaskDataToTest

class CellTranslationsAnalysis {

	data class Translation(
		val fromRow: Int,
		val fromCol: Int,
		val toRow: Int,
		val toCol: Int,
		val value: Int
	)

	fun findAndGroupTranslations(taskData: TaskCoordinateData): Map<Int, Map<Pair<Int, Int>, Int>> {
		val translationsByValue = mutableMapOf<Int, MutableMap<Pair<Int, Int>, Int>>()

		for (example in taskData.train) {
			val inputCoordinates = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
			val outputCoordinates = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()

			// Collect coordinates of non-zero values in input and output matrices
			for (i in example.input.indices) {
				for (j in example.input[0].indices) {
					val value = example.input[i][j]
					if (value != 0) {
						inputCoordinates.getOrPut(value) { mutableListOf() }.add(Pair(i, j))
					}
				}
			}
			for (i in example.output.indices) {
				for (j in example.output[0].indices) {
					val value = example.output[i][j]
					if (value != 0) {
						outputCoordinates.getOrPut(value) { mutableListOf() }.add(Pair(i, j))
					}
				}
			}

			// Identify translations
			for ((value, inputCoords) in inputCoordinates) {
				val outputCoords = outputCoordinates[value]
				if (outputCoords != null && inputCoords.size == outputCoords.size) {
					for (i in inputCoords.indices) {
						val from = inputCoords[i]
						val to = outputCoords[i]
						val translation = Translation(from.first, from.second, to.first, to.second, value)

						// Calculate translation vector (x, y)
						val xTranslation = to.second - from.second
						val yTranslation = to.first - from.first

						translationsByValue.getOrPut(value) { mutableMapOf() }
							.compute(Pair(xTranslation, yTranslation)) { _, count -> (count ?: 0) + 1 }
					}
				}
			}
		}

		// Sort by value
		return translationsByValue.toSortedMap()
	}

	/**
	 * That is super code!  Please now take the output of
	 * "findAndGroupTranslations" and create a function to
	 * collapse the result ignoring the cell values - to see
	 * if all the translations are identical, regardless of cell value.
	 * Return true if all translations are identical.
	 */
	fun areAllTranslationsIdentical(groupedTranslations: Map<Int, Map<Pair<Int, Int>, Int>>): Boolean {
		val allTranslations = mutableMapOf<Pair<Int, Int>, Int>()

		// Collapse the translations, ignoring the cell values
		for (translationsForValues in groupedTranslations.values) {
			for ((translation, count) in translationsForValues) {
				allTranslations.compute(translation) { _, totalCount -> (totalCount ?: 0) + count }
			}
		}

		// Check if all translations are identical
		return allTranslations.size == 1
	}

	fun surveyTasksForIdenticalTranslations() {

		for (task in tTaskDataToTest) {
			val t = findAndGroupTranslations(task)
			if (areAllTranslationsIdentical(t)) {
				println("${task.name} All translations are identical!!!")
			}
		}
	}
}