@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

import experiments.CellTranslationsAnalysis
import experiments.ExperimentalDatasets
import experiments.MirrorMatrixSolution

fun main() {
//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    readTaskData()

    val dataSets = ExperimentalDatasets(listOfTaskData)

    // an experiment to survey for mirror solutions
    val mirrorSolutionSurvey = MirrorMatrixSolution()
    mirrorSolutionSurvey.surveyTasksForMirroringSolutions()

    // hack in a test of the equal dataset
    //listOfTaskData = dataSets.taskDataSortedByEqualCellCount.toMutableList()

    // hack - display only Tasks where input and ouput "populations" match
    listOfTaskData = dataSets.taskDataWhereElementsAreIdentical.toMutableList()
    println("${listOfTaskData.size}: number of input and ouput populations match")

    // NOW do the translations analysis!!

    // an experiment to sort for identical translations
    val identicalTranslations = CellTranslationsAnalysis()
    identicalTranslations.surveyTasksForIdenticalTranslations()

    // hack the hack - scan for tasks where things are only added
    val temp = dataSets.taskDataWhereThereAreOnlyAdditions(listOfTaskData)
    println("${temp.size} - number of Tasks where things are only added")
    listOfTaskData = temp.toMutableList()

    val graphics = GraphicsDisplayMatrix()
    graphics.setupGraphics()
    graphics.displayMatrices()

//    val dops = DatabaseOps()
//    dops.createDatabaseAndAddAllTaskData(databasePath)
}