@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "RedundantIf"
)

package solutions.transformationsBidirectional

import SolutionMatrix
import SolvedTasks
import solutions.utilities.prettyPrintMatrixDiff
import solvedTasks
import taskAbstractionsList

class TransformationBidi {

	val bidiTransformList: List<BidirectionalBaseClass> = listOf(
		TestTrans(),
		BidiFullRowColoring(),
		BidiBasicTranslations(),
		BidiColumnColoringMapping(),
		BidiConnectPairsOfPoints(),
		BidiChangeBlockColoring(),
		BidiChangeBlockColoringBasedOnPoint(),
		BidiSplitBlocksVertically(),
		BidiPatternMatching(),
		BidiColorByMajority(),
		BidiFillPointsDownward(),
		// start of Tasks where the output is smaller
		SmallerHollowBlock(),
		SmallerBlockEnclosingPoint(),
		SmallerCountPointsInBlocksMaxWins()
	)

	val winners : MutableList<BidirectionalBaseClass> = mutableListOf()

	/**
	 * Scan the tasks but look at both the input and output data
	 * for the Examples.  The various transformations save
	 * state data relative to the transformation to use
	 * to solve the test cases.
	 */
	fun scanBidiTransformations() {

		val theList = taskAbstractionsList // for debugging visibility
		for (atask in theList) {
			val taskName = atask.taskData.name

			if (taskName == "d9fac9be") {
				println("We have $taskName") // hollow block enclosing point
			}

			val numExamples = atask.abstractionsList.size
			val originalTaskData = atask.taskData

			for (t in bidiTransformList) {
				t.resetState()
				t.taskName = taskName
				var success = true
				for (j in 0 until numExamples) {

					val originalMatrixInputAndOutput = originalTaskData.train[j]

					val abstraction = atask.abstractionsList[j]
					val inputBlocks = abstraction.input.blocks
					val inputPoints = abstraction.input.points
					val inputMatrix = originalMatrixInputAndOutput.input
					t.setInput(inputBlocks, inputPoints, inputMatrix)

					val outputBlocks = abstraction.output.blocks
					val outputPoints = abstraction.output.points
					val outputMatrix = originalMatrixInputAndOutput.output
					t.setOutput(outputBlocks, outputPoints, outputMatrix)

					val resultMatrix = t.testTransform()
					if (resultMatrix != originalMatrixInputAndOutput.output) {
						success = false
						break
					}
				}
				if (success) {
					// success ! all subsequent transformations worked on this task
					// println("TxBIDI ${t.name} - WORKED - continuing")

					// re-create the test "key" matrix and compare to the real thing
					//   Do this for all test matrix input and output pairs

					val solutionMatrices = mutableListOf<SolutionMatrix>()

					for (j in 0 until originalTaskData.test.size) {

						val originalTestMatrixInputAndOutput3 = originalTaskData.test[j]
						val abstraction3 = atask.abstractionsInTestMatrices[j]
						val inputBlocks3 = abstraction3.blocks
						val inputPoints3 = abstraction3.points
						val inputMatrix = originalTestMatrixInputAndOutput3.input
						t.setInput(inputBlocks3, inputPoints3, inputMatrix)


						// note - this is the test "key" -
						val outputMatrix3 = originalTestMatrixInputAndOutput3.output

						val resultMatrix3 = t.returnTestOutput()
						if (resultMatrix3 != originalTestMatrixInputAndOutput3.output) {
							success = false

							if ((taskName == "ce9e57f2") && (t.name == "BIDI split blocks vertically" )) {
								prettyPrintMatrixDiff(resultMatrix3, originalTestMatrixInputAndOutput3.output)
							}

							break
						}
						// record the solution matrix
						solutionMatrices.add(SolutionMatrix(resultMatrix3))
					}
					if (success) {
						println("TxBIDI ${t.name} for $taskName - VERIFIED!!")

						val solved = SolvedTasks(
							atask.taskData,
							taskName,
							t.name,
							solutionMatrices
						)
						solvedTasks.add(solved)
						winners.add(t)
						break
					}
				}
				// continue looping through the transformations
			}
		}

		// see which transforms are the slackers

		val losers = bidiTransformList.filter { !winners.contains(it)}
		println("==============")
		println("   losers     ")
		println("==============")
		for (l in losers) {
			val name = l.name
			println(name)
		}
	}
}



