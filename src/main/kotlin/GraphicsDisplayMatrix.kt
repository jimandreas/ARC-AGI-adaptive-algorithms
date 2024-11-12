@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

import com.jimandreas.MatrixDataInputAndOutput
import com.jimandreas.listOfTaskData
import com.jimandreas.trainingNames
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

class GraphicsDisplayMatrix {

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
    lateinit var testPanel: JPanel
    lateinit var buttonPanel: JPanel

    fun setupGraphics() {

        SwingUtilities.invokeAndWait {
            val frame = JFrame("ARC-AGI Matrix Visualization")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            //frame.size = Dimension(600, 800) // Set initial size
            frame.preferredSize = Dimension(600, 800) // Set initial size


            val contentPane = frame.contentPane
            contentPane.layout = GridLayout(4, 3)

            // create the components
            for (row in 0..3) {
                var p = JPanel()
                //p.background = Color.RED
                contentPane.add(p)
                inputPanelList.add(p)

                p = JPanel()
                //p.background = Color.BLUE
                contentPane.add(p)
                outputPanelList.add(p)

                if (row == 0) {
                    p = JPanel()
                    contentPane.add(p)
                    testPanel = p
                } else if (row == 1) {
                    p = JPanel()
                    contentPane.add(p)
                    buttonPanel = p
                } else {
                    p = JPanel()
                    p.background = Color.GREEN
                    contentPane.add(p) //placeholder in third column
                }
            }
//            val matrixPanel = JPanel()
//            matrixPanel.layout = GridLayout(1, 3) // 1 row, 3 columns (input, output, test)
//            val inputPanel = JPanel()
//            val outputPanel = JPanel()
//            val testPanel = JPanel() // For the "test" matrix later
//            matrixPanel.add(inputPanel)
//            matrixPanel.add(outputPanel)
//            matrixPanel.add(testPanel)
//            contentPane.add(matrixPanel, BorderLayout.CENTER)

            //val buttonPanel = JPanel()
            //buttonPanel.layout = FlowLayout(FlowLayout.RIGHT)
            val nextButton = JButton("Next")
            buttonPanel.add(nextButton)
            //contentPane.add(buttonPanel, BorderLayout.SOUTH)

            frame.pack()
            frame.isVisible = true

//            var currentDataIndex = 0
//            nextButton.addActionListener {
//                displayMatrices(train[currentDataIndex], inputPanel, outputPanel)
//                currentDataIndex = (currentDataIndex + 1) % train.size
//            }
        }
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
//        for (task in listOfTaskData) {
//
//            val exampleIterator = task.train.listIterator().withIndex()
//            while (exampleIterator.hasNext()) {
//                val currentTask = exampleIterator.next()
//                val currentTaskIndex = currentTask.index
//                val currentTaskMatrix = currentTask.value
//                Timer(2000) { // Change matrices every 2 seconds (adjust as needed)
//                    displayMatrices(
//                        currentTaskMatrix, inputPanel, outputPanel
//                    )
//                }.start()
//            }
//        }
    }

    fun displayMatrices(train: List<MatrixDataInputAndOutput>) {

        val trainIter = train.withIndex().iterator()
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
            ),
// ... more MatrixDataInputAndOutput objects ...
        )

//        SwingUtilities.invokeLater {
//            createAndShowMatrix(matrixData)
//        }

//        SwingUtilities.invokeLater {
////            createAndShowGUI(train)
//            createAndShowGUI(listOfTaskData[0].train)
//        }
    }
}


/*
Notes:
adding a scrollable panel:
https://stackoverflow.com/a/18408836/7061237
JPanel mainPanel = new JPanel(); //This would be the base panel of your UI
JPanel p1=new JPanel();
JPanel p2=new JPanel();
JPanel p3=new JPanel();
JPanel p4=new JPanel();
JPanel newPanel = new JPanel();
newPanel.add(p1);
newPanel.add(p2);
newPanel.add(p3);
newPanel.add(p4);
JScrollPane pane = new JScrollPane(newPanel);
mainPanel.add(pane);
 */