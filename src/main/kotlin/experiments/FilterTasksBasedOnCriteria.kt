@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package experiments

/*
Gemini prompt: The data in the input and output matrices are
integers in the range of 0 to 9.  Please create a
function that compiles the quantities of each value
in the matrix for the input and the output matrices.
Then the function should compare the quantities of each
value for the input and output.  Return true if the
quantities match for all the entries in the "train" list.
 */

import TaskCoordinateData

fun compareValueQuantities(taskData: TaskCoordinateData): Boolean {
    for (example in taskData.train) {
        val inputCounts = mutableMapOf<Int, Int>()
        val outputCounts = mutableMapOf<Int, Int>()

        // Count value occurrences in the input matrix
        for (row in example.input) {
            for (value in row) {
                inputCounts[value] = inputCounts.getOrDefault(value, 0) + 1
            }
        }

        // Count value occurrences in the output matrix
        for (row in example.output) {
            for (value in row) {
                outputCounts[value] = outputCounts.getOrDefault(value, 0) + 1
            }
        }

        // Compare the counts
        if (inputCounts != outputCounts) {
            return false // Quantities don't match
        }
    }

    return true // Quantities match for all examples
}


class ExperimentalDatasets(taskData: List<TaskCoordinateData>) {

    val originalTaskData = taskData
    // experimental analysis:
    // sort the Task data by the total cell count of the output matrices
    val taskDataSortedByOutputCellCount: List<TaskCoordinateData>
            = taskData.sortedBy { it.outputMatrixCellCount }
    // sort the Task data by (1) equivalence of input and output cell counts
    // and (2) then the output matrix cell count
    val taskDataSortedByEqualCellCount: List<TaskCoordinateData>
            = taskData.filter { it.inputMatrixCellCount == it.outputMatrixCellCount}
        .sortedBy { it.outputMatrixCellCount }

    val taskDataWhereElementsAreIdendical : List<TaskCoordinateData>
            = taskData.filter { compareValueQuantities(it) }

}
