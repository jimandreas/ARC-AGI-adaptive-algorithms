@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

import experiments.CellTranslationsAnalysis
import experiments.ExperimentalDatasets
import solutions.MirrorMatrixSolution
import solutions.SolutionOrganizer

val temp: MutableList<Int> = mutableListOf()

fun main() {

//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    readTaskData()

    tTaskDataToTest = tAllTaskData

    val dataSets = ExperimentalDatasets(tTaskDataToTest)
    val originalList = tTaskDataToTest

    val solutionOrganizer = SolutionOrganizer(dataSets)

    solutionOrganizer.trySolutions()

    // hack in a test of the equal dataset
    //listOfTaskData = dataSets.taskDataSortedByEqualCellCount.toMutableList()

    // hack the hack - scan for tasks where things are only added
    val temp = dataSets.taskDataWhereThereAreOnlyAdditions(
        dataSets.taskSameMatrixSizes)
    println("${temp.size} - number of Tasks where things are only added - equal cell counts")

    // hack - display only Tasks where input and ouput "populations" match
    val populationsMatch = dataSets.taskDataWhereElementAbundancesAreIdentical.toMutableList()
    println("${populationsMatch.size}: number of input and ouput populations match")

    tTaskDataToTest = populationsMatch
    // an experiment to sort for identical translations
    val identicalTranslations = CellTranslationsAnalysis()
    identicalTranslations.surveyTasksForIdenticalTranslations()

    tTaskDataToTest = temp.toMutableList() // only things added

    // print solutions so far!
    prettyPrintSolutions(solvedTasks.toList())
    val graphics = GraphicsDisplayMatrix()
    graphics.setupGraphics()
    graphics.displayMatrices()

//    val dops = DatabaseOps()
//    dops.createDatabaseAndAddAllTaskData(databasePath)
}

fun prettyPrintSolutions(solved: List<SolvedTasks>) {
    for (s in solved) {
        println("Task ${s.taskname} solved by ${s.solvedBy}")
    }
}