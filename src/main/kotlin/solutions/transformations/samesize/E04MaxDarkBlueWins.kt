@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.samesize

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.findNonZeroRegionFromPoint
import solutions.utilities.relocateToOrigin
import kotlin.collections.sortedByDescending

// example: ae4f1146 max dark blue wins
//   complete this

class E04MaxDarkBlueWins : BidirectionalBaseClass() {
	override val name: String = "max dark blue wins"

	var checkedOutput = false
	var winnerColor = 0

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {



		// redo the block abstraction allowing diagonals
		//  looking for the biggest block in input and output
		//    and check that they match
		val nIn = MatrixAbstractions()
		val bu = BlockUtilities()
		nIn.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			nIn,
			scanDiagonals = true,
			requireSameColor = true
		)

		// and the output matrix
		val nOut = MatrixAbstractions()
		nOut.matrix = outputMatrix
		bu.findConnectedBlocksInMatrix(
			nOut,
			scanDiagonals = true,
			requireSameColor = true
		)

		if (!checkedOutput) {
			if (nOut.blocks.isEmpty()) {
				return emptyList()
			}

			val winnerOutputBlockList = nOut.blocks.sortedByDescending { it.coordinates.size }
			if (winnerOutputBlockList.isEmpty()) {
				return emptyList()
			}

			winnerColor = winnerOutputBlockList[0].color

			val winnerInputBlockList = nIn.blocks.filter{
				it.color == winnerColor }.sortedByDescending { it.coordinates.size }
			if (winnerInputBlockList.isEmpty()) {
				return emptyList()
			}

			val coordsOut = relocateToOrigin(winnerOutputBlockList[0]).coordinates
			val coordsIn = relocateToOrigin(winnerInputBlockList[0]).coordinates
			if (coordsOut != coordsIn) {
				return emptyList()
			}


			checkedOutput = true
		}

		val winnerInputBlockList = nIn.blocks.filter{
			it.color == winnerColor }.sortedByDescending { it.coordinates.size }
		if (winnerInputBlockList.isEmpty()) {
			return emptyList()
		}
		val coordinates = winnerInputBlockList[0].coordinates
		val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first }
		val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second }
		val retList = findNonZeroRegionFromPoint(
			inputMatrix,
			minRow,
			minCol)

		return retList

	}

	/**
	map the colors of the input blocks according to the
	accumulated color mapping and then create the output
	matrix for the test and return it.
	 */
	override fun returnTestOutput(): List<List<Int>> {

		// redo the block abstraction allowing diagonals

		val nIn = MatrixAbstractions()
		val bu = BlockUtilities()
		nIn.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			nIn,
			scanDiagonals = true,
			requireSameColor = true
		)

		val winnerInputBlockList = nIn.blocks.filter{
			it.color == winnerColor }.sortedByDescending { it.coordinates.size }
		if (winnerInputBlockList.isEmpty()) {
			return emptyList()
		}
		val coordinates = winnerInputBlockList[0].coordinates
		val minRow = coordinates.minOf { it.first }
		val maxRow = coordinates.maxOf { it.first }
		val minCol = coordinates.minOf { it.second }
		val maxCol = coordinates.maxOf { it.second }
		val retList = findNonZeroRegionFromPoint(
			inputMatrix,
			minRow,
			minCol)

		return retList
	}


}