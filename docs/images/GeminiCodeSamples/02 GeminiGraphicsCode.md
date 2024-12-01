# Gemini graphics code

## The Prompt

A matrix for ARC-AGI is represented as a List<List<Int>>.  
The visual representation of the matrix are colored blocks -
which vary in size according to the row and column count - 
the larger the count the smaller the block.   Please create a kotlin 
program that takes the Input and Output matrices as shown below and 
displays them as colored blocks on a Windows 10 window.  Do not use 
Android functionality.  We can work on an Android form of this later.  
The input matrix should be shown on the left and the corresponding 
output matrix should be shown on the right.

Matrix definitions - there can be two to four of these as input:

data class MatrixDataInputAndOutput(
val input: List<List<Int>>,
val output: List<List<Int>>
)

The colors to use for the blocks are as follows - sorry but this is Python:

.symbol_0 {
background-color: #000;
}
.symbol_1 {
background-color: #0074D9; /* blue */
}
.symbol_2 {
background-color: #FF4136; /* red */
}
.symbol_3 {
background-color: #2ECC40; /* green */
}
.symbol_4 {
background-color: #FFDC00; /* yellow */
}
.symbol_5 {
background-color: #AAAAAA; /* grey */
}
.symbol_6 {
background-color: #F012BE; /* fuschia */
}
.symbol_7 {
background-color: #FF851B; /* orange */
}
.symbol_8 {
background-color: #7FDBFF; /* teal */
}
.symbol_9 {
background-color: #870C25; /* brown */
}

## Gemini's reponse:

```kotlin
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

data class MatrixDataInputAndOutput(
    val input: List<List<Int>>,
    val output: List<List<Int>>
)

fun main() {
    // Example matrices (replace with your actual data)
    val matrixData = MatrixDataInputAndOutput(
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
    )

    SwingUtilities.invokeLater {
        createAndShowGUI(matrixData)
    }
}

fun createAndShowGUI(matrixData: MatrixDataInputAndOutput) {
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

    val inputPanel = createMatrixPanel(matrixData.input)
    val outputPanel = createMatrixPanel(matrixData.output)

    contentPane.add(inputPanel)
    contentPane.add(outputPanel)

    frame.pack()
    frame.isVisible = true
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

fun createColoredBlock(value: Int): JPanel {
    val block = JPanel()
    block.background = when (value) {
        0 -> Color.decode("#000")
        1 -> Color.decode("#0074D9") // blue
        2 -> Color.decode("#FF4136") // red
        3 -> Color.decode("#2ECC40") // green
        4 -> Color.decode("#FFDC00") // yellow
        5 -> Color.decode("#AAAAAA") // grey
        6 -> Color.decode("#F012BE") // fuschia
        7 -> Color.decode("#FF851B") // orange
        8 -> Color.decode("#7FDBFF") // teal
        9 -> Color.decode("#870C25") // brown
        else -> Color.BLACK // Default color
    }
    return block
}
```

Explanation:

MatrixDataInputAndOutput: This data class represents the input and output matrices.
<br>main():
<br>Creates an example MatrixDataInputAndOutput object (replace with your actual data).
Calls SwingUtilities.invokeLater to run the GUI creation on the Event Dispatch Thread (EDT).
<br>createAndShowGUI():
<br>Creates the main frame and sets its properties.
Uses GridLayout to arrange the input and output matrices side by side.
Calls createMatrixPanel() to create panels for each matrix.
Adds the panels to the frame and makes it visible.
<br>createMatrixPanel():
<br>Creates a JPanel to hold the colored blocks of the matrix.
Uses GridLayout to arrange the blocks in a grid.
Iterates through the matrix and creates a colored block for each value using createColoredBlock().
Adds the blocks to the panel.
<br>createColoredBlock():
<br>Creates a JPanel and sets its background color based on the input value using a when expression.
To run this code:

Make sure you have the necessary Java JDK installed.

Save the code as a Kotlin file (e.g., MatrixVisualization.kt).

Compile and run the code from your IDE or using the command line:

Bash:
```bash
kotlinc MatrixVisualization.kt -include-runtime -d MatrixVisualization.jar
java -jar MatrixVisualization.jar
```

This will open a window displaying the input and output matrices as colored blocks. 
You can replace the example matrix data with your actual data to visualize different ARC-AGI tasks.

## Acknowledgement / Reference

Gemini supplied this link as reference:  https://github.com/hellonico/origami-kotlin-samples