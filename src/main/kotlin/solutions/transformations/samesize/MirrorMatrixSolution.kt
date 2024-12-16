package solutions.transformations.samesize

import SolvedTasks
import TaskCoordinateData
import compareMatrices
import experiments.ExperimentalDatasets
import solutions.utilities.mirrorMatrixDownwardDiagonal
import solutions.utilities.mirrorMatrixHorizontally
import solutions.utilities.mirrorMatrixUpwardDiagonal
import solutions.utilities.mirrorMatrixVertically
import solvedTasks
import verboseFlag

class MirrorMatrixSolution {

	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
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

	// do a combo of Horizontal and then Vertical mirroring
	//   ref: 3c9b0459
	fun testHorizontalAndVerticalMirroringSolution(taskData: TaskCoordinateData): Boolean {
		// Check if mirroring solves the training examples
		for (example in taskData.train) {
			val mirroredHorizontalInput = mirrorMatrixHorizontally(example.input)
			val mirroredInput = mirrorMatrixVertically(mirroredHorizontalInput)
			if (!compareMatrices(mirroredInput, example.output)) {
				return false // Mirroring 2X doesn't solve this example
			}
		}

		// If mirroring solves all training examples, check the test examples
		for (example in taskData.test) {
			val mirroredHorizontalInput = mirrorMatrixHorizontally(example.input)
			val mirroredInput = mirrorMatrixVertically(mirroredHorizontalInput)
			if (!compareMatrices(mirroredInput, example.output)) {
				return false // Mirroring  2X doesn't solve this test example
			}
		}

		return true // Mirroring solves all examples
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
				if (verboseFlag) println("${task.name} Vertical mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"vertical mirroring"))
			}

			if (testHorizontalMirroringSolution(task)) {
				if (verboseFlag) println("${task.name} Horizontal mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"horizontal mirroring"))
			}

			if (testHorizontalAndVerticalMirroringSolution(task)) {
				if (verboseFlag) println("${task.name} Horizontal and Vertical mirroring solves this task!")
				solvedTasks.add(SolvedTasks(task, task.name,"horizontal AND vertical mirroring"))
			}

			if (testUpwardDiagonalMirroringSolution(task)) {
				if (verboseFlag) println("${task.name} Upward diagonal mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"upward diagonal mirroring"))
			}

			if (testDownwardDiagonalMirroringSolution(task)) {
				if (verboseFlag) println("${task.name} Downward diagonal mirroring solves this task!")
                solvedTasks.add(SolvedTasks(task, task.name,"downward diagonal mirroring"))
			}
		}

	}
}