@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package experiments


import TaskCoordinateData
import verboseFlag

class ExperimentalDatasets(taskData: List<TaskCoordinateData>) {

	// sort the Task data by (1) equivalence of input and output cell counts
	// and (2) then the output matrix cell count
	val taskSameMatrixSizes: List<TaskCoordinateData> =
		taskData.filter { it.inputMatrixCellCount == it.outputMatrixCellCount }
			.sortedBy { it.outputMatrixCellCount }

	val taskNotSameMatrixSizes: List<TaskCoordinateData> =
		taskData.filter { it.inputMatrixCellCount != it.outputMatrixCellCount }
			.sortedBy { it.outputMatrixCellCount }

	val taskSmallerMatrixSizes: List<TaskCoordinateData> =
		taskNotSameMatrixSizes.filter { it.inputMatrixCellCount < it.outputMatrixCellCount }
			.sortedBy { it.outputMatrixCellCount }

	val taskBiggerMatrixSizes: List<TaskCoordinateData> =
		taskNotSameMatrixSizes.filter { it.inputMatrixCellCount > it.outputMatrixCellCount }
			.sortedBy { it.outputMatrixCellCount }

	// now see if the size of the above to lists adds to 400 - the number of training tasks
	init {  // reality check.
		val tss = taskSameMatrixSizes.size
		val tnss = taskNotSameMatrixSizes.size
		println("Reality check: equal sized tasks quantity $tss not same $tnss total ${tss + tnss} ")
		// also check to see if the small/bigger add to tnss
		val tsmaller = taskSmallerMatrixSizes.size
		val tbigger = taskBiggerMatrixSizes.size
		println("Reality check: smaller size quantity $tsmaller bigger $tbigger total ${tsmaller + tbigger} ")


	}

	// specialized subsets:

	// square matrix - used for mirroring, ...
	val taskSquareMatrix: List<TaskCoordinateData> = filterTasksForSquareMatrices(taskSameMatrixSizes)

	// this filter is really for comparing the abundance of the
	//   element values from the input to the output.
	//   If the abundance matches, i.e. the same number of 1's, 2's etc,
	//   then the Task is included in the list.
	val taskDataWhereElementAbundancesAreIdentical: List<TaskCoordinateData> =
		taskData.filter { compareValueQuantities(it) }

	// tasks where cells are added in the output, otherwise nothing is changed.
	val taskDataWhereThereAreOnlyAdditions  = findTasksWithOnlyAdditions(taskData)

	// experimental analysis:
	// sort the Task data by the total cell count of the output matrices
	val taskDataSortedByOutputCellCount: List<TaskCoordinateData> = taskData.sortedBy { it.outputMatrixCellCount }

	init {
		println("${taskDataWhereThereAreOnlyAdditions.size} - number of Tasks where things are only added")

	}

	// survey the data for rectangular holes
    // the list is accumulated in taskDataWithRectangularHoles
	val taskDataWithRectangularHoles: MutableList<TaskCoordinateData> = mutableListOf()
	init {
		val rectangularHoleFinder = RectangularHoleFinder()
		for (t in taskData) {
			var holeFound = true
			for (example in t.train) {
				val result = rectangularHoleFinder.findRectangularHoles(example.input)
				if (result.isEmpty()) {
					holeFound = false
				} else {
					if (verboseFlag) ("Hole Found ${t.name}")
				}
			}
			if (holeFound) {
				taskDataWithRectangularHoles.add(t)
			}
		}
	}



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

	fun filterTasksForSquareMatrices(tasks: List<TaskCoordinateData>): List<TaskCoordinateData> {
        val result = mutableListOf<TaskCoordinateData>()
		for (task in tasks) {
            var squareFlag = true
			for (example in task.train) {
				val inputRowSize = example.input.size
				val outputRowSize = example.output.size
				val inputColSize = example.input[0].size
				val outputColSize = example.output[0].size

				if ((inputRowSize != outputRowSize) || (inputColSize != outputColSize)) {
                    squareFlag = false
					continue
				}
				if ((inputRowSize != outputColSize)) {
                    squareFlag = false
					continue
				}
			}
            if (squareFlag) {
                result.add(task)
            }
		}
		return result
	}

}
