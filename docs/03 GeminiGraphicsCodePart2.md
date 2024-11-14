# Gemini Graphics Code Part 2

## The Prompt

Now please change the algorithm for displaying the MatrixDataInputAndOutput data to the following:

Display all matrices of MatrixDataInputAndOutput (input and output) as given in
val train: List<MatrixDataInputAndOutput>.   This can be two to four sets of
input and output matrices.   Once this is done, add a function to remove these
matrix representation and accept the next set of matrices represented by the
next in the train list.   This involves removing the GridLayouts created in
createMatrixPanel() and replacing them with the new ones.

This the graphics will cycle through displaying all of the Example input and
output matrices for all Tasks.

## Google Gemini 

```kotlin
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
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
frame.addWindowListener(object : WindowAdapter() {
override fun windowClosing(e: WindowEvent) {
println("Window closing")
System.exit(0)
}
})

    val contentPane = frame.contentPane
    contentPane.layout = GridLayout(1, 2) // 1 row, 2 columns for input and output

    val inputPanel = JPanel()
    val outputPanel = JPanel()

    contentPane.add(inputPanel)
    contentPane.add(outputPanel)

    frame.pack()
    frame.isVisible = true

    // Cycle through the training data
    var currentDataIndex = 0
    Timer(2000) { // Change matrices every 2 seconds (adjust as needed)
        displayMatrices(train[currentDataIndex], inputPanel, outputPanel)
        currentDataIndex = (currentDataIndex + 1) % train.size
    }.start()
}

fun displayMatrices(matrixData: MatrixDataInputAndOutput, inputPanel: JPanel, outputPanel: JPanel) {
// Remove previous matrices
inputPanel.removeAll()
outputPanel.removeAll()

    // Create and add new matrices
    val inputMatrix = createMatrixPanel(matrixData.input)
    val outputMatrix = createMatrixPanel(matrixData.output)
    inputPanel.add(inputMatrix)
    outputPanel.add(outputMatrix)

    // Revalidate and repaint the panels
    inputPanel.revalidate()
    inputPanel.repaint()
    outputPanel.revalidate()
    outputPanel.repaint()
}

fun createMatrixPanel(matrix: List<List<Int>>): JPanel {
val panel = JPanel()
val rows = matrix.size
val cols = matrix[0].size
panel.layout = GridLayout(rows, cols)

    for (row in matrix) {
        for (value in row) {
            val block = createColoredBlock(value)
            panel.add(block)
        }
    }

    return panel
}

// ... (createColoredBlock function remains the same) ...
```
Changes:

train list: The main() function now takes a train list of MatrixDataInputAndOutput objects.

createAndShowGUI():

Instead of directly displaying matrices, it initializes empty inputPanel and outputPanel.
A Timer is used to cycle through the train list and call displayMatrices() every 2 seconds (adjust the delay as needed).

displayMatrices():

This new function takes the current MatrixDataInputAndOutput, inputPanel, and outputPanel.
It removes any existing components from the panels using removeAll().
It creates new matrix panels using createMatrixPanel() and adds them to the respective panels.
It calls revalidate() and repaint() on the panels to refresh the display.
With these changes, the GUI will now cycle through and display all the input a
nd output matrices in the train list. You can adjust the timer delay to control 
how long each matrix is displayed.
