@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "RedundantSuppression", "RedundantSuppression", "RedundantSuppression",
    "RedundantSuppression", "RedundantSuppression", "RedundantSuppression", "RedundantSuppression",
    "RedundantSuppression", "RedundantSuppression", "RedundantSuppression", "RedundantSuppression"
)

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.sql.DriverManager

internal class CompareMatrixSizesTest {

    var bigger : MutableList<String> = mutableListOf()
    var smaller : MutableList<String> = mutableListOf()
    var sameSize : MutableList<String> = mutableListOf()

    @Test
    @Disabled
    @DisplayName("Compare sizes of matrices in the training tasks")
    fun compareSizes() {
        findToSmaller()
        findToBigger()
        findTheSameSize()

        val biggerAndSame = bigger.intersect(sameSize.toSet())
        val smallerAndSame = smaller.intersect(sameSize.toSet())
        println("biger and samesize task: $biggerAndSame, smaller and same task: $smallerAndSame")
    }

    private fun findToSmaller() {
        val url = "jdbc:sqlite:$databasePath"
        val connection = DriverManager.getConnection(url)

        val query = """
        SELECT COUNT(*), GROUP_CONCAT(task_id)
        FROM TrainExample
        WHERE input_rows * input_cols > output_rows * output_cols;
    """

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        if (resultSet.next()) {
            val count = resultSet.getInt(1)
            val taskIds = resultSet.getString(2)
            println("Number of examples: $count")
            println("Task IDs: $taskIds")

            smaller = taskIds.split(",").distinct().toMutableList()
        }



        resultSet.close()
        statement.close()
        connection.close()

    }

    private fun findToBigger() {
        val url = "jdbc:sqlite:$databasePath"
        val connection = DriverManager.getConnection(url)

        val query = """
        SELECT COUNT(*), GROUP_CONCAT(task_id)
        FROM TrainExample
        WHERE input_rows * input_cols < output_rows * output_cols;
    """

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        println("To BIGGER")
        if (resultSet.next()) {
            val count = resultSet.getInt(1)
            val taskIds = resultSet.getString(2)
            println("Number of examples: $count")
            println("Task IDs: $taskIds")

            bigger = taskIds.split(",").distinct().toMutableList()
        }



        resultSet.close()
        statement.close()
        connection.close()

    }

    private fun findTheSameSize() {
        val url = "jdbc:sqlite:$databasePath"
        val connection = DriverManager.getConnection(url)

        val query = """
        SELECT COUNT(*), GROUP_CONCAT(task_id)
        FROM TrainExample
        WHERE input_rows * input_cols = output_rows * output_cols;
    """

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        println("THE SAME SIZE")
        if (resultSet.next()) {
            val count = resultSet.getInt(1)
            val taskIds = resultSet.getString(2)
            println("Number of examples: $count")
            println("Task IDs: $taskIds")

            sameSize = taskIds.split(",").distinct().toMutableList()
        }

        val smallerSize = smaller.size
        val biggerSize = bigger.size
        val sameSizeSize = sameSize.size
        val total = smallerSize+biggerSize+sameSizeSize

        println("smaller size $smallerSize bigger $biggerSize same size $sameSizeSize total $total")

        resultSet.close()
        statement.close()
        connection.close()

    }
}