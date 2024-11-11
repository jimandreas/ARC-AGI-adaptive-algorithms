import com.jimandreas.*
import kotlinx.serialization.json.Json
import java.io.File

fun ReadTaskData() {
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