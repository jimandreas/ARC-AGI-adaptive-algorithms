@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

import experiments.ExperimentalDatasets
import solutions.SolutionOrganizer

val temp: MutableList<Int> = mutableListOf()

fun main() {

//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    readTaskData()
    val ed = ExperimentalDatasets(tAllTaskData)

    // this is now the master route to formally solving Tasks.
    //    experimental stuff will still go in "experiments" randomly.
    val solutionOrganizer = SolutionOrganizer(ed)
    solutionOrganizer.trySolutions()

    // hack - display only Tasks where input and ouput "populations" match
    val populationsMatch = ed.taskDataWhereElementAbundancesAreIdentical.toMutableList()
    println("${populationsMatch.size}: number of input and ouput populations match")

    // print solutions so far!
    prettyPrintSolutions(solvedTasks.toList())

    // set up the data for the graphical view

    //tTaskDataToDisplayInGUI = populationsMatch
    tTaskDataToDisplayInGUI = ed.taskDataWithRectangularHoles

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