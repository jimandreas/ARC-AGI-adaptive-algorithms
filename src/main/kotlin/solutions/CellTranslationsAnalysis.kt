@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions

import SolvedTasks
import TaskCoordinateData
import experiments.ExperimentalDatasets
import solutions.utilities.isEntireMapConsistent
import solvedTasks

/**
 * The filter has found where the input and output matrix cell populations
 * are identical. The change from input to output matrices appears to be
 * mostly a matter of some cells "translating" from one location to
 * another in the matrix. Please write a function that (1) lists the
 * translations in a data structure sorted by cell content (ignore cells
 * with zero as the content), and (2) groups the "x" and "y" directions
 * identical translations, and (3) provide a count of the quantity of translations
 * in the group.
 */

/**
 * Notes:
 * There are tons of translation "types" here:
 * uniform direction - all colors, one color
 * "in direction of" - move in direction of something (one color to another)
 * "go to corners of"
 * "go to interior of", etc
 * Easy to eyeball with the visualization.   Harder to formally filter.
 *
 * These patterns will need a big of work to deduce from the translation data!
 */
class CellTranslationsAnalysis {

	data class Translation(
		val fromRow: Int,
		val fromCol: Int,
		val toRow: Int,
		val toCol: Int,
		val value: Int
	)

	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
	}

	/**
	 * look at all training input and output matrices and
	 *    collect "approximately" where things have moved.
	 *    This is set up as a map of (1) key the integer cell value
	 *    and (2) the row/col offset of the move, and
	 *    (3) the number of times this has been observed.
	 */
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

						if ((xTranslation == 0) && (yTranslation == 0)) {
							continue
						}
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

	/**
	 * begin working through various translation "strategies"
	 *    to look for solutions.
	 */
	fun surveyTranslations() {
		surveyTasksForIdenticalTranslations()

	}

	// look for Tasks where all blocks move uniformly in some direction
	fun surveyTasksForIdenticalTranslations() {
		for (task in ed.taskDataWhereElementAbundancesAreIdentical) {

			// here is the "movement" analysis
			val t = findAndGroupTranslations(task)

			// now check to see if they are all the same
			if (areAllTranslationsIdentical(t)) {
				//println("${task.name} All translations are identical!!!")
				solvedTasks.add(SolvedTasks(task, task.name,"identical translations"))
			}

			if (task.name == "3906de3d") {
				//println("3906de3d here now")
			}
			if (isEntireMapConsistent(t)) {
				//println("${task.name} All translations are consistent!!!")
				ed.taskWithTranslations.add(task)
			}
		}
	}

}