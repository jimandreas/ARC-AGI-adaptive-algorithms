# Tasks Where Things Were Only Added
N.B.  I am omitting the "Gemini" part to the title - as you can safely assume that
Google Gemini is doing the coding part of this work.  My work is to formulate
the questions and try to understand the code and the "answers".

## The Prompt

To continue the analysis between input and output - 
please construct a function that looks at all examples in the 
Tasks and returns a list of Tasks where:  the input cells have 
not changed from input to output (please verify this) and thus 
ONLY values have been ADDED to the output matrices.  Return a list 
of these Tasks that match this criteria.

## Gemini code

```
import kotlinx.serialization.Serializable

// ... (Your data classes for TaskCoordinateData, MatrixDataInputAndOutput) ...

fun findTasksWithOnlyAdditions(tasks: List<TaskCoordinateData>): List<String> {
    val result = mutableListOf<String>()

    for (task in tasks) {
        var onlyAdditions = true

        for (example in task.train) {
            val inputValues = example.input.flatten().toSet()
            val outputValues = example.output.flatten().toSet()

            // Check if all input values are present in the output
            if (!outputValues.containsAll(inputValues)) {
                onlyAdditions = false
                break
            }

            // Check if any new values were added in the output
            if (outputValues.size <= inputValues.size) {
                onlyAdditions = false
                break
            }

            // Check if the input cells remain unchanged
            for (i in example.input.indices) {
                for (j in example.input[0].indices) {
                    val inputValue = example.input[i][j]
                    val outputValue = example.output[i][j]
                    if (inputValue != 0 && inputValue != outputValue) {
                        onlyAdditions = false
                        break
                    }
                }
                if (!onlyAdditions) break
            }
        }

        if (onlyAdditions) {
            result.add(task.name)
        }
    }

    return result
}

fun main() {
    // ... (Example task data) ...

    val tasksWithOnlyAdditions = findTasksWithOnlyAdditions(listOf(taskData, /* ... other tasks ... */))
    println("Tasks with only additions: $tasksWithOnlyAdditions")
}
```

Explanation:

findTasksWithOnlyAdditions(tasks):

* Takes a list of TaskCoordinateData as input.
* Iterates through each task in the list.
* For each task, it iterates through the train examples.
* For each example, it performs the following checks:
* Checks if all values from the input matrix are present in the output matrix.
* Checks if the output matrix has more values than the input matrix (indicating additions).
* Checks if any non-zero values in the input matrix have changed in the output matrix.
* If any of these checks fail, it sets the onlyAdditions flag to false and breaks the loop.
* If all checks pass for all examples in a task, it adds the task name to the result list.
* Finally, it returns the result list containing the names of tasks that meet the criteria.

This function will help you identify ARC tasks where the 
solution involves only adding new values to the output matrix without 
changing the existing values from the input matrix.