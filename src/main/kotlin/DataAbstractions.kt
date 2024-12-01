@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

/**
 * A "Block" is a collection of matrix entries with the same color (non-zero) value.
 * The block does not have to be rectangular.
 * The analysis looks to see if the "block" is:
 *   * rectangular
 *   * hollow
 *   * enumerate missing coordinates to make the block rectangular
 */
data class BlockInfo(
    val coords: Set<Pair<Int, Int>>,
    val rectangularBlockFlag: Boolean,
    val hollowFlag: Boolean,
    val missingCoordinates: Set<Pair<Int, Int>>
)

/**
 at this point this structure describes both training input and output
 data for ONE example.  The matrix defines the input or output.
 The blocks are sections in the matrix with the same color.
 The blockInfoList are the blocks after subsequent analysis.
*/

data class Block(val color: Int, val coordinates: Set<Pair<Int, Int>>)
data class Point(val color: Int, val coordinate: Pair<Int, Int>)

data class BoundingBox( val minRow: Int, val minCol: Int, val maxRow: Int, val maxCol: Int)
/**
 * this is the abstraction for one matrix (input or output)
 * for one Example.   There are more than one Example per Task.
 * It is also the abstraction for the input matrix for the
 * Test instance - there may be more than one!!
 *
 * The matrix is converted to Blocks and Points.
 */
class MatrixAbstractions {
    var matrix: List<List<Int>> = emptyList()

    var blocks: MutableList<Block> = mutableListOf()
    var points: MutableList<Point> = mutableListOf()

    var blockInfoList : MutableList<BlockInfo> = mutableListOf()
}

// the pair for one example - input and output
//  contains the Task data as an embedded data class
data class AbstractionsInInputAndOutput(
    val input: MatrixAbstractions,
    val output: MatrixAbstractions,

    var equalSizedMatrices: Boolean = false,
    // holds the coords where input does not equal output
    var pointDifferenceSet : Set<Pair<Int, Int>> = emptySet()
)

// maintain the association between the Task Data and the
//   abstraction analysis data
data class TaskAbstractions(
    val taskData: TaskCoordinateData,
    val abstractionsList: MutableList<AbstractionsInInputAndOutput>,
    val abstractionsInTestMatrices: MutableList<MatrixAbstractions>
)

val taskAbstractionsList: MutableList<TaskAbstractions> = mutableListOf()
