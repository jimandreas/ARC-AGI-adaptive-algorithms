@file:Suppress(
	"UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
	"ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
	"SameParameterValue", "UnnecessaryVariable"
)

package experiments

import taskAbstractionsList

class BlockCompletionSurvey {

	/**
	 * Solution proposal - for fill out block problem:
	 *    if the missing cells accumulated in the input
	 *    match the differences noted between the input and output
	 *    then the solution is likely to fill in the rectangle.
	 */

	/*
	Looking for the following two tasks:
	60b61512.json
	6d75e8bb.json

	thse are cool but are FILL problems not block completion problem:
	868de0fa.json
	c0f76784.json
	e8593010.json

	 */
	fun scanForBlockCompletionSolution() {

		var colorOfMissingPoint = 0

		for (task in taskAbstractionsList) {
			for (trainExample in task.abstractionsList) {

				if (!trainExample.equalSizedMatrices) {
					return
				}
				val inputInstance = trainExample.input
				//val outputInstance = trainExample.output

				val missingPoints: MutableSet<Pair<Int, Int>> = mutableSetOf()


				for (bi in inputInstance.blockInfoList) {
					missingPoints.addAll(bi.missingCoordinates)
				}

				if (compareSetsOfPairs(missingPoints, trainExample.pointDifferenceSet)) {


					// remember output color from training set
					val coodPair = missingPoints.random()
					val row = coodPair.first
					val col = coodPair.second
					colorOfMissingPoint = trainExample.output.matrix[row][col]
				} else {
					return
				}
			}

			/*
		OK all missing points (to fill out rectangles) match the
		difference between input and output in the examples.
		Try the "missing points" solution!
		 */

			println("60b61512.json 6d75e8bb.json match on block extensions, remember color $colorOfMissingPoint")
		}
	}

	/**
	 * Please assume kotlin. There are two lists of sets of Pairs of Ints.
	 * Please compare the two lists and return true of both lists contain
	 * the same data.   The sets of Pairs should be all matched against each other.
	 * Only return true if a match is found across each of the two lists.
	 * And return false if they differ.  Please handle the case where one set
	 * is or both sets are an emptySet.
	 */

	/*
GG notes: Explanation:

Empty List Handling: Similar to the previous response,
this handles cases where one or both lists are empty.
Size Check: If the lists have different sizes, they cannot be equal.
Sorting: The lists are sorted using sortedBy { it.hashCode() }.
This ensures that the order of the sets within the lists doesn't
affect the comparison, as long as the sets themselves have the same elements.
Set Comparison: The code iterates through the sorted lists
and compares the corresponding sets using the compareSetsOfPairs
function (from the previous response). If any pair of sets is different,
the function immediately returns false.
Return True: If all sets match, the function returns true,
indicating that the lists are equal.
	 */
	fun compareListsOfSets(
		list1: List<Set<Pair<Int, Int>>>,
		list2: List<Set<Pair<Int, Int>>>
	): Boolean {
		// If both lists are empty, they are considered equal
		if (list1.isEmpty() && list2.isEmpty()) return true

		// If one list is empty and the other is not, they are not equal
		if (list1.isEmpty() || list2.isEmpty()) return false

		// If the lists have different sizes, they cannot be equal
		if (list1.size != list2.size) return false

		// Sort the lists to ensure consistent comparison order
		val sortedList1 = list1.sortedBy { it.hashCode() }
		val sortedList2 = list2.sortedBy { it.hashCode() }

		// Compare each set in the lists
		for (i in sortedList1.indices) {
			if (!compareSetsOfPairs(sortedList1[i], sortedList2[i])) {
				return false // If any pair of sets is different, return false
			}
		}

		return true // All sets matched, so the lists are equal
	}

	/**
	 * Please assume kotlin. There are two sets of Pairs of Ints.
	 * Please compare the two sets and return true of both sets
	 * contain the same Pairs of Ints.  And return false if they differ.
	 * Please handle the case where one set is or both sets are an emptySet.
	 * -GG
	 */

	private fun compareSetsOfPairs(set1: Set<Pair<Int, Int>>, set2: Set<Pair<Int, Int>>): Boolean {
		if (set1.isEmpty() && set2.isEmpty()) return true
		if (set1.isEmpty() || set2.isEmpty()) return false
		if (set1.size != set2.size) return false
		return set1.all { set2.contains(it) }
	}

}