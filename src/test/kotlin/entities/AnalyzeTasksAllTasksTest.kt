@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

package entities

import com.jimandreas.*
import com.jimandreas.entities.EntityUtilities
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

internal class AnalyzeTasksAllTasksTest {

    val pp = PrintUtilities()
    val entUtil = EntityUtilities()

    @BeforeEach
    fun setUp() {
        // do something
    }

    @Test
    @DisplayName("run all Tasks through the analysis work, input and output data")
    fun doAnalysisOfAllTasks() {
        println("touching ${trainingNames.size} training tasks and ${evaluationNames.size} evaluation tasks")
        for (t in trainingNames) {
            val filePath = "$pathPrefix$trainingPrefix$t.json"
            openIt(filePath)
        }

        for (t in evaluationNames) {
            val filePath = "$pathPrefix$evaluationPrefix$t.json"
            openIt(filePath)
        }
    }

    private fun openIt(name: String) {

        val file = File(name)
        val exists = file.exists()
        val isAFile = file.isFile
        val canRead = file.canRead()

        println(name)

        if (!(exists && isAFile && canRead)) {
            throw Exception("file not found.")
        }

        lateinit var taskData : TaskCoordinateData

        try {
            taskData = Json.decodeFromString<TaskCoordinateData>(file.readText())
        } catch (e: Exception) {
            println("ERROR on json decode on file: $name")
        }

        val analyze = AnalyzeTasks()
        analyze.analyzeTrainingData(taskData)

        taskTrainDataList.clear()
        taskTestDataList.clear()
    }
}