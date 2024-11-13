import com.jimandreas.*
import kotlinx.serialization.json.Json
import java.io.File

fun readTaskData() {
    println("reading ${trainingNames.size} training tasks")
    val t = System.currentTimeMillis()

    for (name in trainingNames) {
        val filePath2 = "$pathPrefix$trainingPrefix$name.json"

        val filePath = File(filePath2)
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

        // add the output matrix size as a sorting parameter
        taskData.matrixSize = calculateMatrixSize(taskData.train)

        listOfTaskData.add(taskData)

        // don't display / evaluate the evaluation data!!
//    for (t in evaluationNames) {
//        val filePath = "$pathPrefix$evaluationPrefix$t.json"
//        openIt(filePath)
//    }
    }
    val endTime = System.currentTimeMillis()
    val elapsed = endTime - t
    println("elapsed time = $elapsed in milliseconds")
}

/**
 * iterate through the training examples, and add the cell count of
 * all the output matrices to a grand total for this task.
 * This provides a rough complexity parameter for later sorting.
 */
private fun calculateMatrixSize(train: List<MatrixDataInputAndOutput>): Int {
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