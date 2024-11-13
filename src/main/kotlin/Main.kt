package com.jimandreas

import ExperimentalDatasets
import GraphicsDisplayMatrix
import listOfTaskData
import readTaskData

fun main() {
//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    readTaskData()

    val dataSets = ExperimentalDatasets(listOfTaskData)

    // hack in a test of the equal dataset
    listOfTaskData = dataSets.taskDataSortedByEqualCellCount.toMutableList()

    val graphics = GraphicsDisplayMatrix()
    graphics.setupGraphics()
    graphics.displayMatrices()
//    val dops = DatabaseOps()
//    dops.createDatabaseAndAddAllTaskData(databasePath)
}