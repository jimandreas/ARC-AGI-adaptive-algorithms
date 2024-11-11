@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.GridLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities


class GraphicsDisplayMatrix {

    data class MatrixDataInputAndOutput(
        val input: List<List<Int>>,
        val output: List<List<Int>>
    )

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

        SwingUtilities.invokeLater {
            createAndShowGUI(matrixData)
        }
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

    fun createAndShowGUI(matrixData: MatrixDataInputAndOutput) {

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
}