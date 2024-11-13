@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package com.jimandreas

import kotlinx.serialization.Serializable

var listOfTaskData: MutableList<TaskCoordinateData> = mutableListOf()

@Serializable
data class TaskCoordinateData(
    val train: List<MatrixDataInputAndOutput>,
    val test: List<MatrixDataInputAndOutput>,   // note that there sometimes MORE THAN ONE entry in this list!!
    var name: String = "",
    var matrixSize: Int = 0
)

@Serializable
data class MatrixDataInputAndOutput(
    val input: List<List<Int>>,
    val output: List<List<Int>>
)

// Data class to represent the file information from the GitHub API
@Serializable
data class FileInfo(val name: String, val download_url: String)