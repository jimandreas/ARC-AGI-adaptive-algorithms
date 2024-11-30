@file:Suppress(
	"RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions.transformations.smaller

import MatrixAbstractions
import entities.BlockUtilities
import solutions.transformations.BidirectionalBaseClass

// example: 137eaa0f collage with grey center block

class S12CollageWIthGreyCenterBlock : BidirectionalBaseClass() {
	override val name: String = "**** collage with grey center block"

	val greyPoint = 5 // HARDWIRED!!

	var checkedOutput = false
	override fun resetState() {
		checkedOutput = false
	}

	override fun testTransform(): List<List<Int>> {
		// output matrix must be 3x3
		if (!checkedOutput) {
			if (outputMatrix.size != 3 && outputMatrix[0].size != 3)
				return emptyList()
			checkedOutput = true
		}

		val n = MatrixAbstractions()
		val bu = BlockUtilities()
		n.matrix = inputMatrix
		bu.findConnectedBlocksInMatrix(
			n,
			scanDiagonals = true,
			requireSameColor = false
		)

		val theList: MutableList<List<List<Int>>> = mutableListOf()
		for (b in n.blocks) {
			val newMatrix = bu.remapMatrix(inputMatrix, b.coordinates, greyPoint)
			theList.add(newMatrix)
		}

		val retMatrix = bu.mergeMatrices(theList)
		return retMatrix
	}


	override fun returnTestOutput(): List<List<Int>> {
		return testTransform()  // test is identical
	}
}
