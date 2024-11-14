@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package experiments


import TaskCoordinateData

class ExperimentalDatasets(taskData: List<TaskCoordinateData>) {

    val originalTaskData = taskData

    // sort the Task data by (1) equivalence of input and output cell counts
    // and (2) then the output matrix cell count
    val taskSameMatrixSizes: List<TaskCoordinateData> =
        taskData.filter { it.inputMatrixCellCount == it.outputMatrixCellCount }
            .sortedBy { it.outputMatrixCellCount }

    val taskNotSameMatrixSizes: List<TaskCoordinateData> =
        taskData.filter { it.inputMatrixCellCount != it.outputMatrixCellCount }
            .sortedBy { it.outputMatrixCellCount }

    // now see if the size of the above to lists adds to 400 - the number of training tasks

    init {
        val tss = taskSameMatrixSizes.size
        val tnss = taskNotSameMatrixSizes.size
        println("equal sized tasks quantity $tss not same $tnss total ${tss + tnss} ")
    }

    // this filter is really for comparing the abundance of the
    //   element values from the input to the output.
    //   If the abundance matches, i.e. the same number of 1's, 2's etc,
    //   then the Task is included in the list.
    val taskDataWhereElementAbundancesAreIdentical: List<TaskCoordinateData> =
        taskData.filter { compareValueQuantities(it) }

    fun taskDataWhereThereAreOnlyAdditions(theList: List<TaskCoordinateData>)
            : List<TaskCoordinateData> {
        return (findTasksWithOnlyAdditions(theList))
    }

    // experimental analysis:
    // sort the Task data by the total cell count of the output matrices
    val taskDataSortedByOutputCellCount: List<TaskCoordinateData>
     = taskData.sortedBy { it.outputMatrixCellCount }

    /*
    Gemini prompt: The data in the input and output matrices are
    integers in the range of 0 to 9.  Please create a
    function that compiles the quantities of each value
    in the matrix for the input and the output matrices.
    Then the function should compare the quantities of each
    value for the input and output.  Return true if the
    quantities match for all the entries in the "train" list.
     */
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

    fun findTasksWithOnlyAdditions(tasks: List<TaskCoordinateData>): List<TaskCoordinateData> {
        val result = mutableListOf<TaskCoordinateData>()

        for (task in tasks) {
            var onlyAdditions = true

            for (example in task.train) {
                val inputValues = example.input.flatten().toSet()
                val outputValues = example.output.flatten().toSet()

                // Check if all input values are present in the output
                if (!outputValues.containsAll(inputValues)) {
                    onlyAdditions = false
                    break
                }

                // Check if any new values were added in the output
                if (outputValues.size <= inputValues.size) {
                    onlyAdditions = false
                    break
                }

                // Check if the input cells remain unchanged
                for (i in example.input.indices) {
                    for (j in example.input[0].indices) {
                        val inputValue = example.input[i][j]
                        val outputValue = example.output[i][j]
                        if (inputValue != 0 && inputValue != outputValue) {
                            onlyAdditions = false
                            break
                        }
                    }
                    if (!onlyAdditions) break
                }
            }

            if (onlyAdditions) {
                result.add(task)
            }
        }
        return result
    }

}