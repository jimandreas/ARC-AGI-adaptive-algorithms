package com.jimandreas

import GraphicsDisplayMatrix
import readTaskData

fun main() {
//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    readTaskData()

    // experimental analysis:
    // sort the Task data by the total cell count of the output matrices

    val tempList =  listOfTaskData.sortedBy { it.matrixSize }
    listOfTaskData.clear()
    listOfTaskData = tempList.toMutableList()

    val graphics = GraphicsDisplayMatrix()
    graphics.setupGraphics()
    graphics.displayMatrices()
//    val dops = DatabaseOps()
//    dops.createDatabaseAndAddAllTaskData(databasePath)
}