@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)


import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.sql.DriverManager

internal class CompareMatrixSizesTest {

    @Test
    @DisplayName("Compare sizes of matrices in the training tasks")
    fun compareSizes() {
        findToSmaller()
        findToBigger()
        findTheSameSize()
    }

    private fun findToSmaller() {
        val url = "jdbc:sqlite:$databasePath"
        val connection = DriverManager.getConnection(url)

        val query = """
        SELECT COUNT(*), GROUP_CONCAT(task_id)
        FROM TrainExample
        WHERE input_rows > output_rows AND input_cols > output_cols;
    """

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        if (resultSet.next()) {
            val count = resultSet.getInt(1)
            val taskIds = resultSet.getString(2)
            println("Number of examples: $count")
            println("Task IDs: $taskIds")
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
        WHERE input_rows < output_rows AND input_cols < output_cols;
    """

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        println("To BIGGER")
        if (resultSet.next()) {
            val count = resultSet.getInt(1)
            val taskIds = resultSet.getString(2)
            println("Number of examples: $count")
            println("Task IDs: $taskIds")
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
        WHERE input_rows = output_rows AND input_cols = output_cols;
    """

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        println("THE SAME SIZE")
        if (resultSet.next()) {
            val count = resultSet.getInt(1)
            val taskIds = resultSet.getString(2)
            println("Number of examples: $count")
            println("Task IDs: $taskIds")
        }



        resultSet.close()
        statement.close()
        connection.close()

    }
}