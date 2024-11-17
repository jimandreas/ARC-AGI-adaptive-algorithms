package entities

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

class DataForOneTrainExample {
    var matrix: List<List<Int>> = emptyList()
    var blocks: List<Set<Pair<Int, Int>>> = emptyList()
    var blockInfoList : MutableList<BlockInfo> = mutableListOf()

}

// the pair for one example - input and output
data class DataInputOutput(
    val input: DataForOneTrainExample,
    val output: DataForOneTrainExample,

    var equalSizedMatrices: Boolean = false,
    // holds the coords where input does not equal output
    var pointDifferenceSet : Set<Pair<Int, Int>> = emptySet()
)

val taskTrainDataList : MutableList<DataInputOutput> = mutableListOf()