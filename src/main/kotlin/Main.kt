@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

import experiments.CellTranslationsAnalysis
import experiments.ExperimentalDatasets
import solutions.SolutionOrganizer

val temp: MutableList<Int> = mutableListOf()

fun main() {

//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    readTaskData()

    tTaskDataToTest = tAllTaskData

    val dataSets = ExperimentalDatasets(tTaskDataToTest)
    val originalList = tTaskDataToTest

    // this is now the master route to formally solving Tasks.
    //    experimental stuff will still go in "experiments" randomly.
    val solutionOrganizer = SolutionOrganizer(dataSets)
    solutionOrganizer.trySolutions()


    // hack - display only Tasks where input and ouput "populations" match
    val populationsMatch = dataSets.taskDataWhereElementAbundancesAreIdentical.toMutableList()
    println("${populationsMatch.size}: number of input and ouput populations match")

    tTaskDataToTest = populationsMatch

    // an experiment to sort for identical translations
    val identicalTranslations = CellTranslationsAnalysis()
    identicalTranslations.surveyTasksForIdenticalTranslations()

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