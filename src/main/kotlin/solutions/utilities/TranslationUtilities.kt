package solutions.utilities


/*

DIRECTION CONSISTENCY CHECK -

Grok prompts:
I have a list of groupedTranslations: Map<Int, Map<Pair<Int, Int>, Int>>
where the first Map<Int is for a value in an array, the Pair<Int,Int>
represents in which row and column direction this value has moved
in the array, and the last ,Int is the number of times it has moved.
Please create a function that determines from the Pair<Int,Int>
if the movement is uniformly in one direction, that is, the
signs of the Int,Int are consistent through the List of these values.

Good now make the function iterate over a list of the "groupedTranslations"
parameter and return true only if the entire list is consistent.

That fails because it doesn't compare the first entry in the list to the
second to see if the directions are the same.  Only that the directions
are consistent inside one map.  Please revise the loop so that it compares
the directions not only with each other in the map but also from one entry
in the list to the next.
 */

/**
 * Explanation:
 *
 *     extractDirection: This function simplifies the pair into a direction by
 *     determining if it's positive, negative, or zero for both coordinates.
 *     isConsistentDirection: This checks if two directions (extracted from movements)
 *     are the same.
 *     The main loop does the following:
 *         It sets a reference direction from the first movement encountered.
 *         For each map in the list, it checks:
 *             If the movements within the map are consistent with each other.
 *             If the first movement of each map is consistent with the reference
 *             direction set from the first map in the list. If any inconsistency
 *             is found, it immediately returns false.
 *     This ensures consistency not only within each map but across all maps in the list.
 */
fun isEntireMapConsistent(groupedTranslationsMap: Map<Int, Map<Pair<Int, Int>, Int>>): Boolean {
	if (groupedTranslationsMap.isEmpty()) return true

	fun extractDirection(pair: Pair<Int, Int>): Pair<Int, Int> {
		val rowSign = when {
			pair.first > 0 -> 1
			pair.first < 0 -> -1
			else -> 0
		}
		val colSign = when {
			pair.second > 0 -> 1
			pair.second < 0 -> -1
			else -> 0
		}
		return Pair(rowSign, colSign)
	}

	fun isConsistentDirection(firstPair: Pair<Int, Int>, secondPair: Pair<Int, Int>): Boolean {
		val firstDirection = extractDirection(firstPair)
		val secondDirection = extractDirection(secondPair)
		return firstDirection == secondDirection
	}

	var referenceDirection: Pair<Int, Int>? = null
	for (movements in groupedTranslationsMap.values) {
		val firstMovement = movements.keys.firstOrNull() ?: continue
		if (referenceDirection == null) {
			referenceDirection = extractDirection(firstMovement)
		} else {
			if (!isConsistentDirection(referenceDirection, firstMovement)) return false
		}
		if (!movements.keys.all { isConsistentDirection(firstMovement, it) }) return false
	}

	return true
}