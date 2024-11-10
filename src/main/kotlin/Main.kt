package com.jimandreas

import databasePath

fun main() {
//    val databasePath = "C:/a/j/kotlinIdea/kotlin/ARC-AGI-trainingDatabase"
//    val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

    val dops = DatabaseOps()
    dops.createDatabaseAndAddAllTaskData(databasePath)
}