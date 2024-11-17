@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package entities

import TaskCoordinateData

import experiments.*

class AnalyzeTasks {

	val blockUtil = BlockUtilities()
	val blockCompletion = BlockCompletion()
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

		// save the test and the filename

		// first assemble the matrice data
		// and output data into the taskTrainDataList
		// NOTE that the "output" here is the answer key!!

		for (i in 0 until td.test.size) {

			val testMatrixData = td.test[i]
			val dataForOneExampleInput = DataForOneTrainExample()
			dataForOneExampleInput.matrix = testMatrixData.input

			val dataForOneExampleOutput = DataForOneTrainExample()
			dataForOneExampleOutput.matrix = testMatrixData.output


			val dio = NameAndTest(
				dataForOneExampleInput,
				dataForOneExampleOutput,
				td.name
			)

			taskTestDataList.add(dio)
		}

		// now characterize each test input (the output must be generated and compare to the ANSWER matrix!

		for (trainExample in taskTestDataList) {
			analyzeTrainingInputOrOutput(trainExample.input)
		}

	}

	/*
	 * for one training input or output
	 */
	fun analyzeTrainingInputOrOutput(oneTrainInstance: DataForOneTrainExample) {
		val matrix = oneTrainInstance.matrix
		val blocks = blockUtil.findRectangularBlocks(matrix)

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

			val completionSet = blockCompletion.completeRectangularBlock(setOfPairs)

			//println(completionSet)

			val bi = BlockInfo(
				coords = setOfPairs,
				rectangularBlockFlag = validFlag,
				hollowFlag = isHollow,
				missingCoordinates = completionSet
			)
			oneTrainInstance.blockInfoList.add(bi)

			//pp.prettyPrintBlockInfo(bi)
		}
	}
}