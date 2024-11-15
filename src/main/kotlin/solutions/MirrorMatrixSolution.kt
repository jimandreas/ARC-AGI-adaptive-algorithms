@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions

import TaskCoordinateData
import experiments.ExperimentalDatasets
import tTaskDataToTest

class MirrorMatrixSolution(val ed: ExperimentalDatasets) {

    fun mirrorMatrixVertically(matrix: List<List<Int>>): List<List<Int>> {
        return matrix.reversed()
    }

    fun mirrorMatrixHorizontally(matrix: List<List<Int>>): List<List<Int>> {
        return matrix.map { row -> row.reversed() }
    }

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

    fun testVerticalMirroringSolution(taskData: TaskCoordinateData): Boolean {
        // Check if mirroring solves the training examples
        for (example in taskData.train) {
            val mirroredInput = mirrorMatrixVertically(example.input)
            if (!compareMatrices(mirroredInput, example.output)) {
                return false // Mirroring doesn't solve this example
            }
        }

        // If mirroring solves all training examples, check the test examples
        for (example in taskData.test) {
            val mirroredInput = mirrorMatrixVertically(example.input)
            if (!compareMatrices(mirroredInput, example.output)) {
                return false // Mirroring doesn't solve this test example
            }
        }

        return true // Mirroring solves all examples
    }

    fun testHorizontalMirroringSolution(taskData: TaskCoordinateData): Boolean {
        // Check if mirroring solves the training examples
        for (example in taskData.train) {
            val mirroredInput = mirrorMatrixHorizontally(example.input)
            if (!compareMatrices(mirroredInput, example.output)) {
                return false // Mirroring doesn't solve this example
            }
        }

        // If mirroring solves all training examples, check the test examples
        for (example in taskData.test) {
            val mirroredInput = mirrorMatrixHorizontally(example.input)
            if (!compareMatrices(mirroredInput, example.output)) {
                return false // Mirroring doesn't solve this test example
            }
        }

        return true // Mirroring solves all examples
    }

    fun surveyTasksForMirroringSolutions() {

        val taskDataList = ed.taskSquareMatrix

        for (task in taskDataList) {
            if (testVerticalMirroringSolution(task)) {
                println("${task.name} Vertical mirroring solves this task!")
            }

            if (testHorizontalMirroringSolution(task)) {
                println("${task.name} Horizontal mirroring solves this task!")
            }
        }

    }
}