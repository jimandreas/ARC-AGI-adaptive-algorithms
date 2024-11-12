# Gemini Graphics Code Part 3

## The Prompt

Please change the JFrame implementation so that it uses a layout manager to display the
input matrix on the left and the output matrix on the right.  It should leave room for 
an additional matrix which will be the "test" matrix to be implemented later.  
Remove the loop with the timer to advance to the next MatrixDataInputAndOutput. 
Please add a button on the lower right and implement the button so that, 
when it is clicked, the next MatrixDataInputAndOutput in the list is displayed.   
And please make the JFrame much larger initially so that it does not need to be 
resized to see the graphics.

## Google Gemini

```kotlin
import java.awt.*
import java.awt.event.*
import javax.swing.*

// ... (MatrixDataInputAndOutput data class) ...

fun main() {
    // Example training data (replace with your actual data)
    val train = listOf(
        MatrixDataInputAndOutput(
            input = listOf(
                listOf(0, 1, 1, 0),
                listOf(0, 1, 0, 0),
                listOf(0, 0, 2, 2),
                listOf(0, 0, 2, 0)
            ),
            output = listOf(
                listOf(0, 1, 1, 0),
                listOf(0, 1, 1, 0),
                listOf(0, 0, 2, 2),
                listOf(0, 0, 2, 2)
            )
        ),
        // ... more MatrixDataInputAndOutput objects ...
    )

    SwingUtilities.invokeLater {
        createAndShowGUI(train)
    }
}

fun createAndShowGUI(train: List<MatrixDataInputAndOutput>) {
    val frame = JFrame("ARC-AGI Matrix Visualization")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.preferredSize = Dimension(1200, 800) // Set initial size

    val contentPane = frame.contentPane
    contentPane.layout = BorderLayout()

    val matrixPanel = JPanel()
    matrixPanel.layout = GridLayout(1, 3) // 1 row, 3 columns (input, output, test)
    val inputPanel = JPanel()
    val outputPanel = JPanel()
    val testPanel = JPanel() // For the "test" matrix later
    matrixPanel.add(inputPanel)
    matrixPanel.add(outputPanel)
    matrixPanel.add(testPanel)
    contentPane.add(matrixPanel, BorderLayout.CENTER)

    val buttonPanel = JPanel()
    buttonPanel.layout = FlowLayout(FlowLayout.RIGHT)
    val nextButton = JButton("Next")
    buttonPanel.add(nextButton)
    contentPane.add(buttonPanel, BorderLayout.SOUTH)

    frame.pack()
    frame.isVisible = true

    var currentDataIndex = 0
    nextButton.addActionListener {
        displayMatrices(train[currentDataIndex], inputPanel, outputPanel)
        currentDataIndex = (currentDataIndex + 1) % train.size
    }

    // Display the first set of matrices initially
    displayMatrices(train[currentDataIndex], inputPanel, outputPanel)
}

fun displayMatrices(matrixData: MatrixDataInputAndOutput, inputPanel: JPanel, outputPanel: JPanel) {
    // ... (same as before) ...
}

fun createMatrixPanel(matrix: List<List<Int>>): JPanel {
    // ... (same as before) ...
}

// ... (createColoredBlock function remains the same) ...
```
Changes:

BorderLayout: The contentPane now uses BorderLayout to arrange the components.

GridLayout for matrices: A matrixPanel with GridLayout is used to hold the input, output, and (future) test matrices.

FlowLayout for button: A buttonPanel with FlowLayout is used to position the "Next" button on the right.
preferredSize: The frame.preferredSize is set to Dimension(1200, 800) to make the initial window larger.
"Next" button: A "Next" button is added, and its addActionListener is implemented to display the next set of matrices when clicked.

Initial display: The first set of matrices is displayed when the GUI is created.

Removed timer: The timer loop is removed, as the button now controls the matrix advancement.

This updated code provides a more structured layout with a button to control the display of matrices, and it sets a larger initial size for the window.
