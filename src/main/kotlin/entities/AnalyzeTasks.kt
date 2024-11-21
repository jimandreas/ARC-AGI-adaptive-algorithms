@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package entities

import AbstractionsInInputAndOutput
import BlockInfo
import MatrixAbstractions
import TaskAbstractions
import TaskCoordinateData
import experiments.ExperimentalDatasets
import taskAbstractionsList

class AnalyzeTasks {

	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
	}

	val blockUtil = BlockUtilities()
	val entityUtilities = EntityUtilities()

	// results are accumulated in taskAbstractionsList

	fun analyzeTrainingData(td: TaskCoordinateData) {

		val abstractionsList: MutableList<AbstractionsInInputAndOutput> = mutableListOf()

		// assemble the training input and output data
		// into the MatrixAbstractions data structure

		for (i in 0 until td.train.size) {

			val mdata = td.train[i]
			val dataForOneExampleInput = MatrixAbstractions()
			dataForOneExampleInput.matrix = mdata.input

			val dataForOneExampleOutput = MatrixAbstractions()
			dataForOneExampleOutput.matrix = mdata.output


			val dio = AbstractionsInInputAndOutput(
				dataForOneExampleInput, dataForOneExampleOutput
			)

			val inputRowSize = dataForOneExampleInput.matrix.size
			val inputColSize = dataForOneExampleInput.matrix[0].size
			val outputRowSize = dataForOneExampleOutput.matrix.size
			val outputColSize = dataForOneExampleOutput.matrix[0].size
			if ((inputRowSize == outputRowSize)
				&& inputColSize == outputColSize
			) {
				dio.equalSizedMatrices = true
			}
			abstractionsList.add(dio)
		}

		// now characterize each input and each output

		for (trainExample in abstractionsList) {
			analyzeExampleInputOrOutput(trainExample.input)
			analyzeExampleInputOrOutput(trainExample.output)
			trainExample.pointDifferenceSet = entityUtilities.findMatrixDifferences(
				trainExample.input.matrix, trainExample.output.matrix
			)
		}

		// now associate the abstractions with the given Task

		val taskAbstractions = TaskAbstractions(td, abstractionsList)
		taskAbstractionsList.add(taskAbstractions)
	}

	/*
	 * for one example input or output
	 */
	fun analyzeExampleInputOrOutput(oneTrainInstance: MatrixAbstractions) {
		val matrix = oneTrainInstance.matrix
		blockUtil.findConnectedBlocksInMatrix(oneTrainInstance)
		val blocks = oneTrainInstance.blocks
		val bIter = blocks.iterator().withIndex()

		while (bIter.hasNext()) {
			val bData = bIter.next()
			val block = bData.value
			val setOfPairs = block.coordinates
			val validFlag = blockUtil.verifyRectangularBlock(setOfPairs)

			//println("valid block: $validFlag")

			val isHollow = blockUtil.isBlockHollow(matrix, setOfPairs)

			//println("hollow: $isHollow")

			val completionSet = completeRectangularBlock(setOfPairs)

			//println(completionSet)

			val bi = BlockInfo(
				coords = setOfPairs,
				rectangularBlockFlag = validFlag,
				hollowFlag = isHollow,
				missingCoordinates = completionSet
			)
			oneTrainInstance.blockInfoList.add(bi)
		}
	}

	fun completeRectangularBlock(blockCoordinates: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
		if (blockCoordinates.isEmpty()) return emptySet() // Nothing to complete

		val minRow = blockCoordinates.minOf { it.first }
		val maxRow = blockCoordinates.maxOf { it.first }
		val minCol = blockCoordinates.minOf { it.second }
		val maxCol = blockCoordinates.maxOf { it.second }

		val missingCoordinates = mutableSetOf<Pair<Int, Int>>()

		for (row in minRow..maxRow) {
			for (col in minCol..maxCol) {
				if (Pair(row, col) !in blockCoordinates) {
					missingCoordinates.add(Pair(row, col))
				}
			}
		}

		return missingCoordinates
	}
}