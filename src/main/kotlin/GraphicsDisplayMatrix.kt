@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.*

class GraphicsDisplayMatrix {

    var curTaskIndex = 0

    /**
     * create a 4 row and 3 column set of panels.
     *    The first two columns will contine the Train input and output
     *    respectively.
     *    The top right on the last column will for now be a placeholder for
     *    the Test matrix.
     *    The bottom right will contain the next button to go to the next Task.
     */

    val inputPanelList: MutableList<JPanel> = mutableListOf()
    val outputPanelList: MutableList<JPanel> = mutableListOf()

    // the special panel list is for experiments comparing input to output
    val specialPanelList: MutableList<JPanel> = mutableListOf()
    lateinit var testPanel: JPanel
    lateinit var buttonPanel: JPanel
    lateinit var nameField: JTextField

    fun setupGraphics() {

        SwingUtilities.invokeAndWait {
            val frame = JFrame("ARC-AGI Matrix Visualization")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            //frame.size = Dimension(600, 800) // Set initial size
            frame.preferredSize = Dimension(700, 900) // Set initial size

            val contentPane = frame.contentPane
            contentPane.layout = GridLayout(4, 4)

            // create the components
            for (row in 0..3) {
                var p = JPanel()
//                p.layout = BorderLayout()
//                p.border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4)
                //p.background = Color.BLACK
                contentPane.add(p)
                inputPanelList.add(p)

                p = JPanel()
//                p.layout = BorderLayout()
//                p.border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4)
                //p.background = Color.BLACK
                contentPane.add(p)
                outputPanelList.add(p)

                p = JPanel()
//                p.layout = BorderLayout()
//                p.border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4)
                //p.background = Color.BLACK
                contentPane.add(p)
                specialPanelList.add(p)

                when (row) {
                    0 -> {
                        p = JPanel()
                        contentPane.add(p)
                        testPanel = p
                    }

                    1 -> {
                        p = JPanel()
                        contentPane.add(p)
                        buttonPanel = p
                    }

                    else -> {
                        p = JPanel()
                        p.background = Color.GREEN
                        contentPane.add(p) //placeholder in third column
                    }
                }
            }

            nameField = JTextField("NO NAME")
            buttonPanel.add(nameField)

            val nextButton = JButton("Next")
            buttonPanel.add(nextButton)
            //contentPane.add(buttonPanel, BorderLayout.SOUTH)


            frame.pack()
            frame.isVisible = true

            nextButton.addActionListener {
                curTaskIndex += 1
                displayMatrices()

                displaySpecialInformation()
            }
        }
    }

    fun displaySpecialInformation() {
        clearSpecialPanels()

        // get the Task info

        val trainTask = tTaskDataToTest[curTaskIndex].train

        // iterate through the examples

        val trainIter = trainTask.withIndex().iterator()
        while (trainIter.hasNext()) {
            val next = trainIter.next()
            val matrixData = next.value
            val index = next.index

            // TODO : handle 5 or more Examples better, punt for now
            if (index > 3) {
                return
            }

            // only do the difference function if the input and output
            //  row and column count match

            val inputList = matrixData.input
            val outputList = matrixData.output

            val rowCount = inputList.size
            val colCount = inputList[0].size

            if (rowCount != outputList.size || colCount != outputList[0].size) {
                continue
            }

            val theSpecialMatrix : MutableList<List<Int>> = mutableListOf()

            // create a difference matrix where -1 indicates differences between input and output
            for (i in 0 until rowCount) {
                val tempRow :MutableList<Int> = mutableListOf()
                for (j in 0 until colCount) {
                    if (inputList[i][j] != outputList[i][j]) {
                        tempRow.add(-1)
                    } else {
                        tempRow.add(inputList[i][j])
                    }
                }
                theSpecialMatrix.add(tempRow)
            }

            // A "special" Panel was created earlier - one for each task
            val specialPanel = specialPanelList[index]
            val specialMatrix = createMatrixPanel(theSpecialMatrix)
            specialPanel.add(specialMatrix)
            specialPanel.revalidate()
            specialPanel.repaint()
        }

    }

    fun displayMatrices() {
        clearAllPanels()

        val trainTask = tTaskDataToTest[curTaskIndex].train

        nameField.text = tTaskDataToTest[curTaskIndex].name

        val trainIter = trainTask.withIndex().iterator()
        while (trainIter.hasNext()) {
            val next = trainIter.next()
            val matrixData = next.value
            val index = next.index

            // TODO : handle 5 or more Examples better, punt for now
            if (index > 3) {
                return
            }

            val inputPanel = inputPanelList[index]
            val outputPanel = outputPanelList[index]

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

    fun createColoredBlock(cellValue: Int): JPanel {
        val block = JPanel()
        // block.size = Dimension(5,5) no effect
        block.background = when (cellValue) {
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
            -1 -> Color.decode("#FFFFFF") // white for special
            else -> Color.BLACK // Default color
        }
        val border = BorderFactory.createLineBorder(Color.LIGHT_GRAY)
        block.border = border
        return block
    }

    fun sampleGraphicsForTest() {

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
            )
        )
    }

    fun clearAllPanels() {

        for (i in 0..3) { // hardwired!  for four (zero based) Examples.
            val inputPanel = inputPanelList[i]
            val outputPanel = outputPanelList[i]

            // Remove previous matrices
            inputPanel.removeAll()
            inputPanel.repaint()
            outputPanel.removeAll()
            outputPanel.repaint()
        }
    }

    fun clearSpecialPanels() {
        for (i in 0..3) { // hardwired!  for four (zero based) Examples.
            val specialPanel = specialPanelList[i]

            // Remove previous matrices
            specialPanel.removeAll()
            specialPanel.repaint()
        }
    }
}
