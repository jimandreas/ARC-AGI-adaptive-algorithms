@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

import kotlinx.serialization.Serializable

var listOfTaskData: MutableList<TaskCoordinateData> = mutableListOf()

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

class ExperimentalDatasets(taskData: List<TaskCoordinateData>) {

    val originalTaskData = taskData
    // experimental analysis:
    // sort the Task data by the total cell count of the output matrices
    val taskDataSortedByOutputCellCount: List<TaskCoordinateData>
        = taskData.sortedBy { it.outputMatrixCellCount }
    // sort the Task data by (1) equivalence of input and output cell counts
    // and (2) then the output matrix cell count
    val taskDataSortedByEqualCellCount: List<TaskCoordinateData>
        = taskData.filter { it.inputMatrixCellCount == it.outputMatrixCellCount}
        .sortedBy { it.outputMatrixCellCount }

}