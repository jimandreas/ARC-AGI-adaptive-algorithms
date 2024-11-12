package com.jimandreas

import DatabaseOps
import GraphicsDisplayMatrix
import ReadTaskData
import databasePath

fun main() {
//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    ReadTaskData()
    val graphics = GraphicsDisplayMatrix()
    graphics.setupGraphics()
    graphics.displayMatrices(listOfTaskData[0].train)
//    val dops = DatabaseOps()
//    dops.createDatabaseAndAddAllTaskData(databasePath)
}