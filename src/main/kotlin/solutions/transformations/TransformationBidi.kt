@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "RedundantIf"
)

package solutions.transformations

import SolutionMatrix
import SolvedTasks
import solutions.transformations.bigger.B01ExtendMatrixWithMirror
import solutions.transformations.partition.P01PatternMatching
import solutions.transformations.partition.P02CommonPoints
import solutions.transformations.samesize.*
import solutions.transformations.smaller.*
import solvedTasks
import taskAbstractionsList
import unSolvedTasks
import wrongAnswerTasks

class TransformationBidi {

	val bidiTransformList: List<BidirectionalBaseClass> = listOf(
		TestTrans(),
		BidiFullRowColoring(),
		BidiBasicSpatialTranslations(),
		BidiColumnColoringMapping(),
		BidiConnectPairsOfPoints(),
		BidiChangeBlockColoring(),
		BidiChangeBlockColoringBasedOnPoint(),
		BidiSplitBlocksVertically(),
		BidiPatternMatching(),
		BidiColorByMajority(),
		BidiFillPointsDownward(),
		// other transforms
		RotateSquareBlock(),
		// start of Tasks where the output is smaller
		SmallerHollowBlock(),
		SmallerBlockEnclosingPoint(),
		SmallerCountPointsInBlocksMaxWins(),
		SmallerColorCountSummary(),
		S10ColorBandRepresentation(),
		SmallerSimpleColorCount(),
		BiggestBlock(),
		S06ReturnTopCorner(),
		SmallerBottomThreeColorBlocks(),
		SmallerCountBlocksOfGivenColor(),
		ScanMatrixWithThreeByThreeViewport(),
		RegionRepresentationWithNoise(),
		S11SmallerRegionRepresentationByRelativeWidth(),
		S12CollageWIthGreyCenterBlock(),
		S13SmallestEnclosingRectangle(),
		S14FindUniqueBlock(),
		S15FindSymmetricBlock(),
		S16FindTheBigBlock(),
		S17TheMostWins(),
		S18AlienPointWins(),
		S19FindSmallestRearrangement(),
		S20BlockTaggedByAlienPoint(),
		S21QuantizeAndSummarize(),
		S22BlocksToPointsTiling(),
		S23TwoBlocksCompleteARectangle(),
		S24PointsToSerpentine(),
		S25RepeatedMatrixPatterns(),
		S26CountBlocks(),
		S27TheDanteExperience(),
		E01PointToRowColorMapping(),
		E02MajorityBlockWins(),
		S28QuantizeBlocks(),
		S29RightLeftSymmetric(),
		P01PatternMatching(),
		P02CommonPoints(),
		S30BlockConnection(),
		S31FindUniqueBlockColorDontCare(),
		S32SmallestBlockWins(),
		E03ColorSwap(),
		E04MaxDarkBlueWins(),
		S33NumColorsMapping(),
		S34BlockCompositing(),
		S35LeftRightCompositing(),
		S36TopBottomCompositing(),
		S37TopBottomCompositingWithInversion(),
		S38FindBlockPlusInversion(),
		S39EntityWithTheMostOf(),
		S40DuplicateBlockHorizontally(),
		B01ExtendMatrixWithMirror(),
		E05ColorPointsInOutput(),
		E06CenterBlockUsingPoints(),
		S41BiggestBlocksReturnedAsStripe(),
		E07TranslateAndChangeColor(),
		S42CleverMergingOfPoints(),
		E08ReturnCenterColumn(),
		E09PickWInningColor(),
		E10InputPointToOutputConstellation(),
		S43BlockDirectedLinesAssembly(),
		S44MapInputColorToOutputBlock(),
		E11TraceEdgeOfMatrix(),
		E12Spiral()

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

							// add a record indicating a wrong answer
							val solved = SolvedTasks(
								atask.taskData,
								taskName,
								t.name,
								solutionMatrices
							)
							wrongAnswerTasks.add(solved)

							break
						}
						// record the solution matrix
						solutionMatrices.add(SolutionMatrix(resultMatrix3))
					}
					if (success) {
//						println("TxBIDI ${t.name} for $taskName - VERIFIED!!")

						val solved = SolvedTasks(
							atask.taskData,
							taskName,
							t.name,
							solutionMatrices
						)
						solvedTasks.add(solved)
						winners.add(t)
						break
					} else {
						val solved = SolvedTasks(
							atask.taskData,
							taskName,
							t.name,
							solutionMatrices
						)
						unSolvedTasks.add(solved)
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



