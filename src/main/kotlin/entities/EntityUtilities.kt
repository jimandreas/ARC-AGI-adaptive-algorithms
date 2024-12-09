@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package entities

import Block
import Point
import kotlin.collections.indices

/**
 * Please assume kotlin.  A matrix is given in the form of
 * integers in List<List<Int>>.   Therefore it is a two
 * dimensional matrix.  Assume an int with value of zero is
 * a background.  Please write a function that can trace
 * around entries with non-zero values to find an isolated
 * matrix “entity”.  Please create a set of (row, column)
 * pairs that list the coordinates of the isolated entity.
 * The entity should be surrounded by either (1) the edge of
 * the matrix, or (2) values of zero.  Thus this is an isolated
 * form in the matrix.
 *
 * 2nd generation.   That was good.  Now please modify the
 * algorithm so that it finds *all* isolated forms in the matrix.
 * Note that the interior cells of a form do not need to be searched.
 * But the search should continue until all cells *exterior* to the
 * forms should be searched.  It should return a List of Sets of Pairs.
 *
 */
fun findAllIsolatedThings(matrix: List<List<Int>>): List<Set<Pair<Int, Int>>> {
	val allEntities = mutableListOf<Set<Pair<Int, Int>>>()
	val numRows = matrix.size
	val numCols = matrix[0].size
	val visited = mutableSetOf<Pair<Int, Int>>() // Keep track of visited cells

	fun isBoundary(row: Int, col: Int): Boolean {
		return row == 0 || row == numRows - 1 || col == 0 || col == numCols - 1
	}

	fun exploreEntity(row: Int, col: Int): Set<Pair<Int, Int>> {
		val entityCoordinates = mutableSetOf<Pair<Int, Int>>()
		if (row in 0 until numRows && col in 0 until numCols && matrix[row][col] != 0 && Pair(
				row,
				col
			) !in visited
		) {
			entityCoordinates.add(Pair(row, col))
			visited.add(Pair(row, col))
			exploreEntity(row + 1, col).let { entityCoordinates.addAll(it) }
			exploreEntity(row - 1, col).let { entityCoordinates.addAll(it) }
			exploreEntity(row, col + 1).let { entityCoordinates.addAll(it) }
			exploreEntity(row, col - 1).let { entityCoordinates.addAll(it) }
		}
		return entityCoordinates
	}

	fun neighborsAreZero(matrix: List<List<Int>>, row: Int, col: Int): Boolean {
		val numRows2 = matrix.size
		val numCols2 = matrix[0].size
		val ggt1 = row - 1 < 0 || matrix[row - 1][col] == 0
		val ggt2 = row + 1 >= numRows2 || matrix[row + 1][col] == 0
		val ggt3 = col - 1 < 0 || matrix[row][col - 1] == 0
		val ggt4 = col + 1 >= numCols2 || matrix[row][col + 1] == 0
		val retVal = ggt1 || ggt2 || ggt3 || ggt4
		return retVal
	}

	for (row in 0 until numRows) {
		for (col in 0 until numCols) {
			if (matrix[row][col] != 0 && Pair(row, col) !in visited && (isBoundary(row, col) || neighborsAreZero(
					matrix,
					row,
					col
				))
			) {
				allEntities.add(exploreEntity(row, col))
			}
		}
	}
	return allEntities
}


/**
 * code generated by Google Gemini in response to this prompt:
 * Please assume kotlin.  A matrix is given in the form of integers
 * in List<List<Int>>.   Therefore it is a two dimensional matrix.
 * Assume an int with value of zero is a background.  Please write a
 * function that can trace around entries with non-zero values to find
 * an isolated matrix “entity”.  Please create a set of (row, column)
 * pairs that list the coordinates of the isolated entity.
 * The entity should be surrounded by either (1) the edge of the matrix,
 * or (2) values of zero.  Thus this is an isolated form in the matrix.
 */
fun findIsolatedThing(matrix: List<List<Int>>): Set<Pair<Int, Int>> {
	val entityCoordinates = mutableSetOf<Pair<Int, Int>>()
	val numRows = matrix.size
	val numCols = matrix[0].size

	fun isBoundary(row: Int, col: Int): Boolean {
		return row == 0 || row == numRows - 1 || col == 0 || col == numCols - 1
	}

	fun exploreEntity(row: Int, col: Int) {
		if (row in 0 until numRows && col in 0 until numCols && matrix[row][col] != 0 && Pair(
				row,
				col
			) !in entityCoordinates
		) {
			entityCoordinates.add(Pair(row, col))
			exploreEntity(row + 1, col)
			exploreEntity(row - 1, col)
			exploreEntity(row, col + 1)
			exploreEntity(row, col - 1)
		}
	}

	fun neighborsAreZero(matrix: List<List<Int>>, row: Int, col: Int): Boolean {
		val numRows2 = matrix.size
		val numCols2 = matrix[0].size
		val ggt1 = row - 1 < 0 || matrix[row - 1][col] == 0
		val ggt2 = row + 1 >= numRows2 || matrix[row + 1][col] == 0
		val ggt3 = col - 1 < 0 || matrix[row][col - 1] == 0
		val ggt4 = col + 1 >= numCols2 || matrix[row][col + 1] == 0
		val retVal = ggt1 || ggt2 || ggt3 || ggt4
		return retVal
	}

	for (row in 0 until numRows) {
		for (col in 0 until numCols) {
			if (matrix[row][col] != 0 && (isBoundary(row, col) || neighborsAreZero(matrix, row, col))) {
				exploreEntity(row, col)
				return entityCoordinates // Assuming only one isolated entity
			}
		}
	}

	return entityCoordinates // Return empty set if no entity is found
}

/**
 * Please assume kotlin. There are two matrices.
 * Each matrix is given in the form of integers in List<List<Int>>.
 * Therefore it is a two dimensional matrix. Assume an int with
 * value of zero is a background.  Please compare the two
 * matrices and accumulate a set of coordinate pairs where
 * the two matrices are different.   Return the set of coordinates.
 *
 * Added: returns empty list of the matrices are not the same size
 *
 * [Google Gemini emitted code follows]
 */
fun findMatrixDifferences(matrix1: List<List<Int>>, matrix2: List<List<Int>>): Set<Pair<Int, Int>> {
	val differentCoordinates = mutableSetOf<Pair<Int, Int>>()

	// Assuming both matrices have the same dimensions
	val numRows = matrix1.size
	val numCols = matrix1[0].size

	val numRows2 = matrix2.size
	val numCols2 = matrix2[0].size

	// return emptySet if the matrices are not the same size
	if ((numRows != numRows2 || numCols != numCols2)) return emptySet()

	for (row in 0 until numRows) {
		for (col in 0 until numCols) {
			if (matrix1[row][col] != matrix2[row][col]) {
				differentCoordinates.add(Pair(row, col))
			}
		}
	}

	return differentCoordinates
}

fun validateThingWithBlocksAndPoints(
	m: List<List<Int>>, coordinates: Set<Pair<Int, Int>>, bList: List<Block>, pList: List<Point>
): List<List<Int>> {

	val minRow = coordinates.minOf { it.first }
	val maxRow = coordinates.maxOf { it.first }
	val minCol = coordinates.minOf { it.second }
	val maxCol = coordinates.maxOf { it.second }

	val retList: MutableList<List<Int>> = mutableListOf()
	for (row in minRow..maxRow) {
		val rowList: MutableList<Int> = mutableListOf()
		for (col in minCol..maxCol) {
			if (!isPointInBlockList(row, col, bList)
				&& !isPointInPointList(row, col, pList)
			) {
				return emptyList()
			}
			rowList.add(m[row][col])
		}
		retList.add(rowList)
	}
	return retList
}

fun isPointInBlockList(row: Int, col: Int, bList: List<Block>): Boolean {
	for (b in bList) {
		if (b.coordinates.contains(Pair(row, col)))
			return true
	}
	return false
}

fun isPointInPointList(row: Int, col: Int, pList: List<Point>): Boolean {
	for (p in pList) {
		if (p.coordinate == Pair(row, col))
			return true
	}
	return false
}

/**
The Most Of

e is the "entity list - the blocks of stuff found int the matrix
m is the matrix

returned is the Pair of the color most found (after the
entity "background color" accumulated over all the
entities.  In other words the number two color.

 */

fun findTheMostOf(m: List<List<Int>>, entities: List<Set<Pair<Int, Int>>>): Pair<Int, List<List<Int>>> {

	val bu = BlockUtilities()
	// they all have to be rectangular
	for (ent in entities) {
		val result = bu.verifyRectangularBlock(ent)
		if (!result) {
			return Pair(0, emptyList())
		}
	}

	val findMajorityColor = rankInts(m, entities)
	if (findMajorityColor.isEmpty()) {
		return Pair(0, emptyList())
	}

	val majorityColor = findMajorityColor[0].first

	return Pair(majorityColor, m) // needs fixing!

}

/**
I have a matrix m of Ints and an entities List of Sets of Row and Column coordinates
that reference cells in the matrix of Ints.  In Kotlin:

m: List<List<Int>>, entities: List<Set<Pair<Int, Int>>>

Please return the ranked list of the Ints and their quantities based on the entities List.
 GROK code follows
 */
fun rankInts(
	m: List<List<Int>>,
	entities: List<Set<Pair<Int, Int>>>,
	excludingMode: Boolean = false,
	excludedColor: Int = 0)
 : List<Pair<Int, Int>> {

// Map to hold the count of each integer
	val countMap = mutableMapOf<Int, Int>()

// Iterate through each entity
	for (entity in entities) {
		for ((row, col) in entity) {
			// Check if the coordinate is within the matrix bounds
			if (row in m.indices && col in m[row].indices) {
				val number = m[row][col]
				if (excludingMode) {
					if (number == excludedColor) {
						continue
					}
				}
				countMap[number] = (countMap[number] ?: 0) + 1
			}
		}
	}

// Convert the map to a list of pairs, sorted by count in descending order
	val colorAndQuantity = countMap.toList().sortedByDescending { (_, count) -> count }

	return colorAndQuantity
}

/**
 convert a matrix of type List<List<Int>> to just a Set of row and column pairs
 for entity usage - for the above analysis functions
 */

fun matrixToCoordinateSet(m: List<List<Int>>): Set<Pair<Int, Int>> {
	val rowCount = m.size
	if (rowCount == 0) {
		return emptySet()
	}
	val colCount = m[0].size

	val s : MutableSet<Pair<Int, Int>> = mutableSetOf()
	for (row in 0 until rowCount) {
		for (col in 0 until colCount) {
			val p = Pair(row, col)
			s.add(p)
		}
	}
	return s
}

/**
 * convert a coordinate set into a sub-matrix and return it
 */

fun coordinateSetToMatrix(
	coordinates: Set<Pair<Int, Int>>, m: List<List<Int>>): List<List<Int>> {
	val minRow = coordinates.minOf { it.first }
	val maxRow = coordinates.maxOf { it.first }
	val minCol = coordinates.minOf { it.second }
	val maxCol = coordinates.maxOf { it.second }
	if (m.isEmpty()) {
		return emptyList()
	}
	if ((maxRow > m.size) || (maxCol > m[0].size)) {
		return emptyList()
	}

	val retList: MutableList<List<Int>> = mutableListOf()
	for (row in minRow..maxRow) {
		val rowList: MutableList<Int> = mutableListOf()
		for (col in minCol..maxCol) {
			rowList.add(m[row][col])
		}
		retList.add(rowList)
	}
	return retList

}