@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

import com.jimandreas.MatrixDataInputAndOutput
import com.jimandreas.listOfTaskData
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*


class GraphicsDisplayMatrix {


    fun sampleGraphics() {

        // Example matrices (replace with your actual data)
        val matrixData = MatrixDataInputAndOutput(
            input = listOf(
                listOf(0, 1, 2, 3),
                listOf(4, 5, 6, 7),
                listOf(8, 9, 0, 0),
                listOf(0, 0, 2, 0)
            ),
            output = listOf(
                listOf(0, 1, 1, 0),
                listOf(0, 1, 1, 0),
                listOf(0, 0, 2, 2),
                listOf(0, 0, 2, 2)
            )
        )

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

//        SwingUtilities.invokeLater {
//            createAndShowMatrix(matrixData)
//        }

        SwingUtilities.invokeLater {
//            createAndShowGUI(train)
            createAndShowGUI(listOfTaskData[0].train)
        }
    }



    fun createAndShowGUI(train: List<MatrixDataInputAndOutput>) {
        val frame = JFrame("ARC-AGI Matrix Visualization")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.preferredSize = Dimension(1000, 600) // Set initial size

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

    fun createAndShowGUIOLD() {
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

        //frame.repaint(0, 0, 500, 500 )

        // Cycle through the training data
        for (task in listOfTaskData) {

            val exampleIterator = task.train.listIterator().withIndex()
            while (exampleIterator.hasNext()) {
                val currentTask = exampleIterator.next()
                val currentTaskIndex = currentTask.index
                val currentTaskMatrix = currentTask.value
                Timer(2000) { // Change matrices every 2 seconds (adjust as needed)
                    displayMatrices(
                        currentTaskMatrix, inputPanel, outputPanel)
                }.start()
            }
        }
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

    /* from version 1
    fun createAndShowMatrix(matrixData: MatrixDataInputAndOutput) {

        // Get the default GraphicsConfiguration
        val gc = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration
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

     */

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


    // experimental debugging
    fun doit() {

        SwingUtilities.invokeLater {
            val frame = JFrame()
            val panel = JPanel()
            panel.border = BorderFactory.createEmptyBorder(
                30, //top
                30, //bottom
                10, //left
                30
            ) // right

            frame.add(panel)
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.pack()
            frame.isVisible = true
        }
    }
}


