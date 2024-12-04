@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.translateBlocksToOrigin

// example: S27 662c240a The Dante Experience (an L pattern)
//

class S27TheDanteExperience : BidirectionalBaseClass() {
	override val name: String = "**** The Dante Experience"

	var checkedOutput = false

	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {

		if (taskName == "662c240a") {
			println("here now")
			for (b in inputBlockList) {
				val translatedBlocks = translateBlocksToOrigin(listOf(b))
				val foo = findLShapedBlocks(translatedBlocks, 3, 3)
				println(foo)
			}
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
