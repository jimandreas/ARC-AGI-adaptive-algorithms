@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

package entities

import com.jimandreas.*
import com.jimandreas.entities.BlockCompletion
import com.jimandreas.entities.BlockUtilities
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

internal class BlockUtilitiesTest {

    val pp = PrintUtilities()
    val blockUtil = BlockUtilities()
    val blockCompletion = BlockCompletion()

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

    // NOTES: good but the tasks are more oriented to "hollowness" than "blockiness"

    @Test
    @DisplayName("examine blocks in on task")
    fun testBlockRecognizerOnOneTask() {
//        val t = "05f2a901" // https://arc-visualizations.github.io/05f2a901.html
//        val t = "a5313dff" // https://arc-visualizations.github.io/a5313dff.html
        val t = "3aa6fb7a" // https://arc-visualizations.github.io/3aa6fb7a.html
        val filePath = "$pathPrefix$trainingPrefix$t.json"
        openIt(t, filePath)


    }

    @Test
    @DisplayName("scan blocks in all tasks")
    fun scanBlocksInAllTasks() {
        println("touching ${trainingNames.size} training tasks and ${evaluationNames.size} evaluation tasks")
        for (t in trainingNames) {
            val filePath = "$pathPrefix$trainingPrefix$t.json"
            openIt(t, filePath)
        }

        for (t in evaluationNames) {
            val filePath = "$pathPrefix$evaluationPrefix$t.json"
            openIt(t, filePath)
        }
    }

    private fun openIt(name: String, path: String) {

        val file = File(path)
        val exists = file.exists()
        val isAFile = file.isFile
        val canRead = file.canRead()

        if (!(exists && isAFile && canRead)) {
            throw Exception("file not found.")
        }

        lateinit var myData : TaskCoordinateData

        try {
            myData = Json.decodeFromString<TaskCoordinateData>(file.readText())
        } catch (e: Exception) {
            println("ERROR on json decode on file: $path")
        }

        for (d in myData.train) {
            val blocks = blockUtil.findRectangularBlocks(d.input)

            println("$name ${blocks.size}")

            val bIter = blocks.iterator().withIndex()
            while (bIter.hasNext()) {
                val bData = bIter.next()
                val setOfPairs = bData.value
                val validFlag = blockUtil.verifyRectangularBlock(setOfPairs)

                println("valid block: $validFlag")

                val isHollow = blockUtil.isBlockHollow(d.input, setOfPairs)

                println("hollow: $isHollow")

                val completionSet = blockCompletion.completeRectangularBlock(setOfPairs)

                println(completionSet)
            }
        }

//        println("\n")
//        pp.prettyPrintOneMatrixWithEntityDesignation(d, entities)
//        println("Done\n")
    }
}