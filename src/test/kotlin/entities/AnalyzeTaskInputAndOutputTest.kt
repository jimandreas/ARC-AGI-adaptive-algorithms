@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

package entities

internal class AnalyzeTaskInputAndOutputTest {
// historical - needs revision
/*    val pp = PrintUtilities()
    val blockUtil = BlockUtilities()
    val blockCompletion = BlockCompletion()
    val taskData = DataForOneTrainExample()
    val analyze = AnalyzeTasks()

    @BeforeEach
    fun setUp() {
        // do something
    }

    @AfterEach
    fun cleanHeap() {
        // release the task global data structure to prevent memory leak
        //   and reset the analysis
        taskTrainDataList.clear()
        taskTestDataList.clear()
    }

    @Test
    @DisplayName("try analyzing both input and output")
    fun testBlockRecognizerOnOneTask() {

        val t = "3aa6fb7a" // https://arc-visualizations.github.io/3aa6fb7a.html

        // 60b61512 NOTE that this one exposed that the diagonal cell was not found!

        //val t = "60b61512" // https://arc-visualizations.github.io/60b61512.html

        // 6d75e8bb is similar to the above - a filled rectangle

//        val t = "6d75e8bb" // https://arc-visualizations.github.io/60b61512.html

        val filePath = "$pathPrefix$trainingPrefix$t.json"
        openIt(t, filePath)


    }

    private fun openIt(name: String, path: String) {

        val d = DataForOneTrainExample()

        val file = File(path)
        val exists = file.exists()
        val isAFile = file.isFile
        val canRead = file.canRead()

        if (!(exists && isAFile && canRead)) {
            throw Exception("file not found.")
        }

        lateinit var taskData : TaskCoordinateData

        try {
            taskData = Json.decodeFromString<TaskCoordinateData>(file.readText())
        } catch (e: Exception) {
            println("ERROR on json decode on file: $path")
        }

        analyze.analyzeTrainingData(taskData)

    }*/
}