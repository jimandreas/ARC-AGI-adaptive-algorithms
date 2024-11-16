@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

import kotlinx.serialization.Serializable

// the focus of the current test
var tTaskDataToDisplayInGUI: MutableList<TaskCoordinateData> = mutableListOf()

var tAllTaskData: MutableList<TaskCoordinateData> = mutableListOf()



@Serializable
data class TaskCoordinateData(
    val train: List<MatrixDataInputAndOutput>,
    val test: List<MatrixDataInputAndOutput>,   // note that there sometimes MORE THAN ONE entry in this list!!
    var name: String = "",
    var inputMatrixCellCount: Int = 0,
    var outputMatrixCellCount: Int = 0
)

@Serializable
data class MatrixDataInputAndOutput(
    val input: List<List<Int>>,
    val output: List<List<Int>>
)

// Data class to represent the file information from the GitHub API
@Serializable
data class FileInfo(val name: String, val download_url: String)

data class SolvedTasks(
    val task: TaskCoordinateData,
    val taskname: String,
    val solvedBy: String
)
var solvedTasks: MutableList<SolvedTasks> = mutableListOf()

