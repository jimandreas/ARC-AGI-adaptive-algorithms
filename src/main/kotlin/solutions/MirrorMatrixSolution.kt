@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions

import SolvedTasks
import TaskCoordinateData
import compareMatrices
import experiments.ExperimentalDatasets
import solvedTasks

class MirrorMatrixSolution {

	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
	}

	fun mirrorMatrixVertically(matrix: List<List<Int>>): List<List<Int>> {
		return matrix.reversed()
	}

	fun mirrorMatrixHorizontally(matrix: List<List<Int>>): List<List<Int>> {
		return matrix.map { row -> row.reversed() }
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

	fun mirrorMatrixDownwardDiagonal(matrix: List<List<Int>>): List<List<Int>> {
		val size = matrix.size // Assuming it's a square matrix
		val mirroredMatrix = MutableList(size) { MutableList(size) { 0 } }

		for (i in 0 until size) {
			for (j in 0 until size) {
				mirroredMatrix[j][i] = matrix[i][j]
			}
		}

		return mirroredMatrix
	}

	fun mirrorMatrixUpwardDiagonal(matrix: List<List<Int>>): List<List<Int>> {
		val size = matrix.size // Assuming it's a square matrix
		val mirroredMatrix = MutableList(size) { MutableList(size) { 0 } }

		try {
			for (i in 0 until size) {
				for (j in 0 until size) {
					mirroredMatrix[size - 1 - j][size - 1 - i] = matrix[i][j]
				}
			}
		} catch (e: Exception) {
			println("oopsie!!")
		}

		return mirroredMatrix
	}

	fun testUpwardDiagonalMirroringSolution(taskData: TaskCoordinateData): Boolean {
		// Check if mirroring solves the training examples
		for (example in taskData.train) {
			val mirroredInput = mirrorMatrixUpwardDiagonal(example.input)
			if (!compareMatrices(mirroredInput, example.output)) {
				return false // Mirroring doesn't solve this example
			}
		}

		// If mirroring solves all training examples, check the test examples
		for (example in taskData.test) {
			val mirroredInput = mirrorMatrixUpwardDiagonal(example.input)
			if (!compareMatrices(mirroredInput, example.output)) {
				return false // Mirroring doesn't solve this test example
			}
		}

		return true // Mirroring solves all examples
	}

	fun testDownwardDiagonalMirroringSolution(taskData: TaskCoordinateData): Boolean {
		// Check if mirroring solves the training examples
		for (example in taskData.train) {
			val mirroredInput = mirrorMatrixDownwardDiagonal(example.input)
			if (!compareMatrices(mirroredInput, example.output)) {
				return false // Mirroring doesn't solve this example
			}
		}

		// If mirroring solves all training examples, check the test examples
		for (example in taskData.test) {
			val mirroredInput = mirrorMatrixDownwardDiagonal(example.input)
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
				//println("${task.name} Vertical mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"vertical mirroring"))
			}

			if (testHorizontalMirroringSolution(task)) {
				//println("${task.name} Horizontal mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"horizontal mirroring"))
			}

			if (testUpwardDiagonalMirroringSolution(task)) {
				//println("${task.name} Upward diagonal mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"upward diagonal mirroring"))
			}

			if (testDownwardDiagonalMirroringSolution(task)) {
				//println("${task.name} Downward diagonal mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"downward diagonal mirroring"))
			}
		}

	}
}