@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package entities

import TaskCoordinateData
import experiments.ExperimentalDatasets

class AnalyzeTasks {

	lateinit var ed: ExperimentalDatasets

	fun setExperimentalDatasets(edIn: ExperimentalDatasets) {
		ed = edIn
	}

	val blockUtil = BlockUtilities()
	val entityUtilities = EntityUtilities()

	// results are accumulated in taskTrainingDataList

	fun analyzeTrainingData(td: TaskCoordinateData) {

		// first assemble the training input and output data into the taskTrainDataList
		for (i in 0 until td.train.size) {

			val mdata = td.train[i]
			val dataForOneExampleInput = DataForOneTrainExample()
			dataForOneExampleInput.matrix = mdata.input

			val dataForOneExampleOutput = DataForOneTrainExample()
			dataForOneExampleOutput.matrix = mdata.output


			val dio = DataInputOutput(
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
			taskTrainDataList.add(dio)
		}

		// now characterize each input and each output

		for (trainExample in taskTrainDataList) {
			analyzeTrainingInputOrOutput(trainExample.input)
			analyzeTrainingInputOrOutput(trainExample.output)
			trainExample.pointDifferenceSet = entityUtilities.findMatrixDifferences(
				trainExample.input.matrix, trainExample.output.matrix
			)
		}
	}

	/*
	 * for one training input or output
	 */
	fun analyzeTrainingInputOrOutput(oneTrainInstance: DataForOneTrainExample) {
		val matrix = oneTrainInstance.matrix
		val blocks = blockUtil.findConnectedBlockInMatrix(matrix)

		//pp.prettyPrintOneMatrixWithEntityDesignation(matrix, blocks)

		oneTrainInstance.blocks = blocks
		val bIter = blocks.iterator().withIndex()

		while (bIter.hasNext()) {
			val bData = bIter.next()
			val setOfPairs = bData.value
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