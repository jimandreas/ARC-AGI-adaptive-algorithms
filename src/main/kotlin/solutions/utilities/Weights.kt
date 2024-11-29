package solutions.utilities

import kotlin.math.abs

/**
I have a list of integers in kotlin of the form List<Int>.
I want to make a new list based on these integers where each
integer has a minimum weight of 1.  The total of the weights
must be equal to the integer totalWeight.  All weights must be integers.
Of course the weight must be calculated with respect to the
magnitude of the integer in the list.  Please provide a function
that does this calculation and returns a List<Int> with the weights.

 Gemini code follows:
*/

fun calculateWeights(values: List<Int>, totalWeight: Int): List<Int> {
	val numValues = values.size
	if (numValues == 0 || totalWeight < numValues) {
		throw IllegalArgumentException("Invalid input: list cannot be empty and totalWeight must be at least the size of the list.")
	}

	val baseWeight = 1
	var remainingWeight = totalWeight - numValues * baseWeight
	val weights = MutableList(numValues) { baseWeight }

	val sortedIndices = values.indices.sortedByDescending { values[it] }

	for (i in sortedIndices) {
		val extraWeight = minOf(remainingWeight, values[i] - 1)
		weights[i] += extraWeight
		remainingWeight -= extraWeight
	}

	return weights
}

/**
Good now take a kotlin List<List<Int>> and calculate the integer
totalWeight that has the smallest error for the values in List<List<Int>>.
Return just the integer from the function.  The integer
should allow all rows of the matrix to be allocated into
various "buckets" with the minimum error.

Good now take a kotlin List<List<Int>> and calculate the integer
totalWeight that has
the smallest error for the values in List<List<Int>>.
Return just the integer from the function.
The integer should allow all rows of the matrix to be allocated into
various "buckets" with the minimum error.   To clarify -
the weight should be only calculated on one row of the list,
but should be "compatible" with other rows of the lists.
That is- it is the maximum total weight for the array based on individual rows.
 */
fun findOptimalTotalWeight(matrix: List<List<Int>>): Int {
	var maxTotalWeight = 0

	for (row in matrix) {
		val rowSum = row.sum()
		val numValues = row.size
		var totalWeight = numValues // Start with the minimum possible totalWeight

		while (totalWeight < rowSum) {
			val weights = calculateWeights(row, totalWeight)
			val error = calculateError(row, weights)

			// Check if the error is within an acceptable threshold (e.g., 0.1)
			if (error < 0.1f) {
				break // Found a suitable totalWeight for this row
			}

			totalWeight++
		}

		maxTotalWeight = maxOf(maxTotalWeight, totalWeight)
	}

	return maxTotalWeight
}


fun calculateError(values: List<Int>, weights: List<Int>): Float {
	var totalError = 0.0f
	for (i in values.indices) {
		val expectedWeight = (values[i].toFloat() / values.sum()) * weights.sum()
		totalError += abs(expectedWeight - weights[i])
	}
	return totalError / values.size
}