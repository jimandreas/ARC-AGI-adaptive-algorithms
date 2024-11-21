@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

package entities

import Block
import MatrixAbstractions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import utils.splitStringIntoPairs
import kotlin.math.exp
import kotlin.test.assertEquals

internal class BlockUtilitiesTest {

    lateinit var blockUtil: BlockUtilities

    @BeforeEach
    fun setUp() {
        blockUtil = BlockUtilities()
    }

    @Test
    @DisplayName("basic block test")
    fun basicBlockTest() {
        val matrix = listOf(
            listOf(0, 1, 1, 0, 0),
            listOf(0, 1, 1, 0, 0),
            listOf(0, 0, 0, 2, 2),
            listOf(0, 0, 2, 2, 0),
            listOf(0, 0, 0, 0, 0)
        )

        val oneTrainInstance = MatrixAbstractions()
        oneTrainInstance.matrix = matrix
        blockUtil.findConnectedBlocksInMatrix(oneTrainInstance)
//        for (coordinates in rectangularBlocks) {
//            println("Coordinates: $coordinates")
//        }

        val rectangularBlocks = oneTrainInstance.blocks

        val expectedString1 = "(0, 1), (0, 2), (1, 1), (1, 2)"
        val expectedString2 = "(2, 3), (2, 4), (3, 2), (3, 3)"
        val expectedPairList1 = splitStringIntoPairs(expectedString1)
        val expectedPairList2 = splitStringIntoPairs(expectedString2)
        val expectedList: MutableList<Block> = mutableListOf()
        expectedList.add(Block(color = 1, coordinates = expectedPairList1))
        expectedList.add(Block(color = 2, coordinates = expectedPairList2))

        assertEquals(rectangularBlocks, expectedList)
    }

    @Test
    @DisplayName("Test rectangular block checker function")
    fun testRectangularBlockChecker() {
        // string1 is rectangular, and string 2 is not.
        val blockToTestString1 = "(0, 1), (0, 2), (1, 1), (1, 2)"
        val blockToTestString2 = "(2, 3), (2, 4), (3, 2), (3, 3)"
        val blockToTestPairList1 = splitStringIntoPairs(blockToTestString1)
        val blockToTestPairList2 = splitStringIntoPairs(blockToTestString2)

        val result1 = blockUtil.verifyRectangularBlock(blockToTestPairList1)
        assert(result1)

        val result2 = blockUtil.verifyRectangularBlock(blockToTestPairList2)
        assert(!result2)

    }





}