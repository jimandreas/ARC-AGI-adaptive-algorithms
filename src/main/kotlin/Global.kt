@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName",
    "MayBeConstant"
)

val databasePath = "C:\\a\\j\\kotlinIdea\\ARC-AGI-database-analysis\\DB"

val verboseFlag = false

// global utility functions
fun compareMatrices(matrix1: List<List<Int>>, matrix2: List<List<Int>>): Boolean {
    if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
        return false // Matrices have different dimensions
    }
    for (i in matrix1.indices) {
        for (j in matrix1[0].indices) {
            if (matrix1[i][j] != matrix2[i][j]) {
                return false // Cells don't match
            }
        }
    }
    return true // Matrices are identical
}