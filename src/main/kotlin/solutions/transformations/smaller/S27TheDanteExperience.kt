@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.clipBlocksToSubregion
import solutions.utilities.findSpanningLineWithBUMP
import solutions.utilities.translateBlockListBy

// example: S27 662c240a The Dante Experience (an L pattern)
//    see also 94f9d214 (?)

class S27TheDanteExperience : BidirectionalBaseClass() {
	override val name: String = "The Dante Experience"

	var checkedOutput = false
	var tiledVertical = false
	var tiledHorizontal = false
	var vBlocks = -1
	var hBlocks = -1

	override fun resetState() {
		checkedOutput = false
		tiledVertical = false
		tiledHorizontal = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "662c240a") {
			// // println("here now")
		}
		val inputRows = inputMatrix.size
		val inputCols = inputMatrix[0].size

		// examine the output block and record if it is some
		// quanta (sub-block) sized chunk of matrix of the input matrix
		if (!checkedOutput) {
			val outputRows = outputMatrix.size
			val outputCols = outputMatrix[0].size
			if (outputRows > inputRows || outputCols > inputCols) {
				return emptyList()
			}
			if (outputRows == inputRows && outputCols == inputCols) {
				return emptyList()
			}
			if (outputRows == 1 || outputCols == 1) {
				return emptyList() // no pathologic Tasks
			}
			if (inputCols == outputCols && inputRows % outputRows == 0) {
				vBlocks = inputRows / outputRows
				tiledVertical = true

			} else if (inputRows == outputRows && inputCols % outputCols == 0) {
				hBlocks = inputCols / outputCols
				tiledHorizontal = true
			}
			checkedOutput = true
		}

		if (!tiledVertical && !tiledHorizontal) {
			return emptyList()
		}

		if  (tiledVertical) {
			val rowCount = inputRows / vBlocks
			for (rowQ in 0 until vBlocks ) {
				val baseRow = rowQ * rowCount
				val endRow = (rowQ + 1) * rowCount - 1
				val base = Pair(baseRow, 0)
				val end = Pair(endRow, inputCols)

				val foundBlocks = clipBlocksToSubregion(
					inputMatrix,
					inputBlockList,
					base, end
				)

				val translatedBlocks = translateBlockListBy(foundBlocks, -baseRow, 0)
				val submatrix = inputMatrix.subList(baseRow, endRow+1).map {
						row -> row.subList(0, inputCols) }

				for (b in translatedBlocks) {
					val foundTheL = findSpanningLineWithBUMP(
						b.coordinates, rowCount, inputCols)
					if (foundTheL) {
						return submatrix
					}
				}
			}
		}
		if  (tiledHorizontal) {
			// TODO : complete horizontal tiling
			println("============================")
			println("COMPLETE HORIZONTAL TILING!!")
			println("============================")
		}

		return emptyList()

//		val tryColumn = findRepeatingColumnPattern(inputMatrix)
//		if (tryColumn.isNotEmpty()) {
//			return tryColumn
//		}
//		val tryRow = findRepeatingRowPattern(inputMatrix)
//		return tryRow
//
//		return emptyList()

	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}

	fun findLShapedBlocks(blocks: List<Block>, rowCount: Int, colCount: Int): List<Block> {
		val result = mutableListOf<Block>()

		for (block in blocks) {
			val rows = block.coordinates.map { it.first }
			val cols = block.coordinates.map { it.second }
			val minRow = rows.minOrNull() ?: continue
			val maxRow = rows.maxOrNull() ?: continue
			val minCol = cols.minOrNull() ?: continue
			val maxCol = cols.maxOrNull() ?: continue

			if ((maxRow - minRow + 1 == rowCount && maxCol - minCol < colCount) ||
				(maxCol - minCol + 1 == colCount && maxRow - minRow < rowCount)
			) {

				// Check for orthogonal point at ends using if-then statements
				for ((r, c) in block.coordinates) {
					if (r == minRow && c != minCol && c != maxCol) {
						result.add(block)
						break // Found an orthogonal point, move to the next block
					} else if (r == maxRow && c != minCol && c != maxCol) {
						result.add(block)
						break
					} else if (c == minCol && r != minRow && r != maxRow) {
						result.add(block)
						break
					} else if (c == maxCol && r != minRow && r != maxRow) {
						result.add(block)
						break
					}
				}
			}
		}

		return result
	}
}
