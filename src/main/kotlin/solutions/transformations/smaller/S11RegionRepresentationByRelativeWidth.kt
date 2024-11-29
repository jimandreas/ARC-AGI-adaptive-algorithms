@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import Block
import solutions.transformations.BidirectionalBaseClass
import solutions.utilities.convertBlocksToMatrix

// example: 90c28cc7 region representation by width
//   for a point free set of blocks,
// sort blocks to horizontal bands


class SmallerRegionRepresentationByRelativeWidth : BidirectionalBaseClass() {
	override val name: String = "**** region representation by width"


	override fun resetState() {
	}

	override fun testTransform(): List<List<Int>> {
		if (inputPointList.isNotEmpty()) {
			return emptyList()
		}

		val sortedList = sortToHorizontalBandsOfBlocks(inputBlockList)

		val orderedBlockList = convertBackToBlockList(sortedList)
		val retArray = convertBlocksToMatrix(orderedBlockList)
		return retArray
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}

// "header" for one block - the block specific "SortedBlocks" hang of this
// data class in a list
data class RowOfBlocksHeader(
	val baseRow: Int,
	val maxRow: Int,
	val overallRowWidth: Int,
	val blockList: MutableList<SortedBlocks>
)

// an individual block in a row
data class SortedBlocks(
	val minRow: Int,
	val minCol: Int,
	val maxRow: Int,
	val maxCol: Int,
	val block: Block
)

/*
accumulate the rows of blocks
 */

fun sortToHorizontalBandsOfBlocks(inputBlockList: List<Block>): List<RowOfBlocksHeader> {
	var theList: MutableList<RowOfBlocksHeader> = mutableListOf()

	for (b in inputBlockList) {
		val updatedList = addBlockToTheList(b, theList)
		theList = updatedList
	}
	return theList
}

// add blocks one at a time in theList
private fun addBlockToTheList(b: Block, theList: MutableList<RowOfBlocksHeader>)
		: MutableList<RowOfBlocksHeader> {

	val blockCoordinates = b.coordinates
	val minRow = blockCoordinates.minOf { it.first }
	val maxRow = blockCoordinates.maxOf { it.first }
	val minCol = blockCoordinates.minOf { it.second }
	val maxCol = blockCoordinates.maxOf { it.second }
	val s = SortedBlocks(
		minRow = minRow,
		maxRow = maxRow,
		minCol = minCol,
		maxCol = maxCol,
		block = b
	)
	if (theList.isEmpty()) {  // first addition
		val currentRoB = RowOfBlocksHeader(
			baseRow = minRow,
			maxRow = maxRow,
			overallRowWidth = maxCol - minCol,
			blockList = mutableListOf(s)
		)
		theList.add(currentRoB)
		return theList
	}

	// not the first entry. Figure out where to insert the block

	val biter = theList.iterator().withIndex()
	while (biter.hasNext()) {
		val n = biter.next()
		val h = n.value
		val index = n.index

		// if this block is on an existing block list, then add it to the list somewhere
		if (minRow == h.baseRow) {
			return (insertBlockInRow(s, index, theList))
		} else if (minRow < h.baseRow) {  // a new row before this row
			// insert a block header
			val currentRoB = RowOfBlocksHeader(
				baseRow = minRow,
				maxRow = maxRow,
				overallRowWidth = maxCol - minCol,
				blockList = mutableListOf(s)
			)
			theList.add(0, currentRoB) // insert at start of the list
			return theList
		}
	}
	// didn't find the row.  Presumably this block is after the last existing row.
	// insert a block header
	val currentRoB = RowOfBlocksHeader(
		baseRow = minRow,
		maxRow = maxRow,
		overallRowWidth = maxCol - minCol,
		blockList = mutableListOf(s)
	)
	theList.add(currentRoB) // insert at start of the list
	return theList
}

// insert a blocks one in the current row
private fun insertBlockInRow(s: SortedBlocks, index: Int, theList: MutableList<RowOfBlocksHeader>)
		: MutableList<RowOfBlocksHeader> {

	val currentRoB = theList[index].blockList

	var index = -1
	var lastMinCol = 0
	var lastMaxCol = 0
	val iter = currentRoB.iterator().withIndex()
	while (iter.hasNext()) {
		val n = iter.next()
		val current = n.value
		index = n.index

		// see if this block needs to be inserted
		if (s.minCol == current.maxCol + 1) {
			// yes insert after this block
			currentRoB.add(index+1, s)
			return theList
		}
		if (s.maxCol < current.minCol) {
			// yes insert before
			currentRoB.add(index, s)
			return theList
		}
	}
	currentRoB.add(s)
	return theList
}

fun convertBackToBlockList(headerList: List<RowOfBlocksHeader>): List<List<Block>> {
	val outputBlockList: MutableList<List<Block>> = mutableListOf()

	for (i in 0 until headerList.size) {
		val h = headerList[i].blockList
		val blist = h.map { it.block }
		outputBlockList.add(blist)
	}
	return outputBlockList
}
