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

	for (name in listToUse) {
        val pathAndName = "$filePath2$name.json"
		val filePath = File(pathAndName)
		val exists = filePath.exists()
		val isAFile = filePath.isFile
		val canRead = filePath.canRead()

		if (!(exists && isAFile && canRead)) {
			throw Exception("file not found. $name, $filePath")
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