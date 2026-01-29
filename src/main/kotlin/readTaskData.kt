@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
    "ConstantConditionIf", "KotlinConstantConditions"
)
import kotlinx.serialization.json.Json
import java.io.File

fun readTaskData() {

	val t = System.currentTimeMillis()

	var useTrainingSet = true
	var listToUse: List<String>
    var filePath2: String

	if (useTrainingSet) {
		println("reading ${trainingNames.size} training tasks")
		listToUse = trainingNames
		filePath2 = "$pathPrefix$trainingPrefix"
	} else { // use evaluation set
		println("reading ${evaluationNames.size} evaluation tasks")
        listToUse = evaluationNames
        filePath2 = "$pathPrefix$evaluationPrefix"
	}

	val dataDir = File(filePath2)
	if (!dataDir.exists() || !dataDir.isDirectory) {
		println()
		println("ERROR: ARC task data directory not found:")
		println("  $filePath2")
		println()
		println("Please download the ARC-AGI dataset and place it at:")
		println("  $pathPrefix")
		println()
		println("Expected structure:")
		println("  ${pathPrefix}training/   (400 JSON task files)")
		println("  ${pathPrefix}evaluation/ (100 JSON task files)")
		println()
		println("The dataset is available at: https://github.com/fchollet/ARC-AGI")
		println()
		println("To change the data directory, edit pathPrefix in TaskNames.kt")
		System.out.flush()
		return
	}

	for (name in listToUse) {
        val pathAndName = "$filePath2$name.json"
		val filePath = File(pathAndName)
		val exists = filePath.exists()
		val isAFile = filePath.isFile
		val canRead = filePath.canRead()

		if (!(exists && isAFile && canRead)) {
			println()
			println("ERROR: Task file not found or not readable:")
			println("  $filePath")
			println()
			println("The data directory exists but is missing task file: $name.json")
			println("Please ensure all task JSON files are present in:")
			println("  $filePath2")
			println()
			println("The dataset is available at: https://github.com/fchollet/ARC-AGI")
			println()
			println("To change the data directory, edit pathPrefix in TaskNames.kt")
			System.out.flush()
			return
		}

		lateinit var taskData: TaskCoordinateData

		try {
			taskData = Json.decodeFromString<TaskCoordinateData>(filePath.readText())
		} catch (e: Exception) {
			println("ERROR on json decode on file: $name")
			return
		}

		taskData.name = name

		// add the input and output matrix cell counts as a sorting parameters
		taskData.inputMatrixCellCount = calculateMatrixInputCellCount(taskData.train)
		taskData.outputMatrixCellCount = calculateMatrixOuputCellCount(taskData.train)

		tAllTaskData.add(taskData)
	}
	val endTime = System.currentTimeMillis()
	val elapsed = endTime - t
	if (verboseFlag) println("elapsed time = $elapsed in milliseconds")
}

/**
 * iterate through the training examples, and add the cell count of
 * all the output matrices to a grand total for this task.
 * This provides a rough complexity parameter for later sorting.
 */
// TODO: there is certainly clever kotlin way of doing this but for now hack it
private fun calculateMatrixInputCellCount(train: List<MatrixDataInputAndOutput>): Int {
	var totalMatrixCells = 0
	for (example in train) {
		val matrix = example.input
		val numRows = matrix.size
		val numCols = matrix[0].size
		val numMatrixCells = numRows * numCols
		totalMatrixCells += numMatrixCells
	}

	return totalMatrixCells
}

private fun calculateMatrixOuputCellCount(train: List<MatrixDataInputAndOutput>): Int {
	var totalMatrixCells = 0
	for (example in train) {
		val matrix = example.output
		val numRows = matrix.size
		val numCols = matrix[0].size
		val numMatrixCells = numRows * numCols
		totalMatrixCells += numMatrixCells
	}

	return totalMatrixCells
}