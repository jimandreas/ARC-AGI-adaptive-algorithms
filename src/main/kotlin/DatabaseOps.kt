@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable"
)

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

// ... (Your data classes for TaskCoordinateData, etc.) ...

/**
Explanation:

GitHub API: The code now uses the GitHub API to get a list of files in the "data/training" directory. It fetches the JSON response and parses it to extract the download URLs for each JSON file.
FileInfo data class: A simple data class is defined to represent the file information received from the GitHub API.
downloadAndParseTaskData function: This function takes the download URL of a task file, downloads the JSON data, and parses it into a TaskCoordinateData object.
Iteration and Data Insertion: The code iterates through the list of files, downloads and parses each JSON file, and then calls the addTaskDataToDatabase function (which remains the same as before) to insert the data into the database.
Error Handling: Basic error handling is added to catch potential IOException during network requests.
Json { ignoreUnknownKeys = true }: This is added to handle potential extra keys in the JSON response from the GitHub API.

 */
class DatabaseOps {

    fun createDatabaseAndAddAllTaskData(databasePath: String) {
        val url = "jdbc:sqlite:$databasePath"
        val connection = DriverManager.getConnection(url)

// ... inside createDatabaseAndAddAllTaskData function ...

// Create the tables
        try {
            // connection.createStatement().execute(
            val sqlCommandList = listOf(
                """
CREATE TABLE Task (
  task_id TEXT PRIMARY KEY,  -- Alphanumeric name of the task (e.g., '00d62c1b')
  version INTEGER NOT NULL   -- Schema version number (e.g., 1)
);
""", """
CREATE TABLE TrainExample (
  task_id TEXT NOT NULL,
  example_id INTEGER NOT NULL,  -- Example number within the task (1, 2, 3, ...)
  input_rows INTEGER NOT NULL,
  input_cols INTEGER NOT NULL,
  output_rows INTEGER NOT NULL,
  output_cols INTEGER NOT NULL,
  FOREIGN KEY (task_id) REFERENCES Task (task_id),
  PRIMARY KEY (task_id, example_id)
);
""", """
CREATE TABLE TrainMatrix (
  task_id TEXT NOT NULL,
  example_id INTEGER NOT NULL,
  matrix_type TEXT NOT NULL CHECK (matrix_type IN ('input', 'output')),  -- 'input' or 'output'
  row INTEGER NOT NULL,
  col INTEGER NOT NULL,
  value INTEGER NOT NULL,
  FOREIGN KEY (task_id, example_id) REFERENCES TrainExample (task_id, example_id)
);
""", """
CREATE TABLE TestExample (
  task_id TEXT NOT NULL,
  example_id INTEGER NOT NULL,  -- Example number within the task (1, 2, 3, ...)
  input_rows INTEGER NOT NULL,
  input_cols INTEGER NOT NULL,
  output_rows INTEGER NOT NULL,
  output_cols INTEGER NOT NULL,
  FOREIGN KEY (task_id) REFERENCES Task (task_id),
  PRIMARY KEY (task_id, example_id)
);
""", """
CREATE TABLE TestMatrix (
  task_id TEXT NOT NULL,
  example_id INTEGER NOT NULL,
  matrix_type TEXT NOT NULL CHECK (matrix_type IN ('input', 'output')),  -- 'input' or 'output'
  row INTEGER NOT NULL,
  col INTEGER NOT NULL,
  value INTEGER NOT NULL,
  FOREIGN KEY (task_id, example_id) REFERENCES TestExample (task_id, example_id)
);
"""
            )

            for (sql in sqlCommandList) {
                connection.createStatement().execute(sql)
            }
            println("All tables created successfully.")

        } catch (e: SQLException) {
            println("Error creating tables: ${e.message}")
        }


        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.github.com/repos/fchollet/ARC-AGI/contents/data/training")
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val json = Json { ignoreUnknownKeys = true }

                //println(response.body!!.string())

                val fileInfoList = json.decodeFromString<List<FileInfo>>(response.body!!.string())

                for (fileInfo in fileInfoList) {
                    if (fileInfo.name.endsWith(".json")) {
                        val taskUrl = fileInfo.download_url
                        val taskData = downloadAndParseTaskData(taskUrl)
                        addTaskDataToDatabase(connection, taskData, fileInfo.name.removeSuffix(".json"))
                    }
                }
            } else {
                println("Error: ${response.code} - ${response.message}")
            }
        } catch (e: IOException) {
            println("Error: ${e.message}")
        } finally {
            connection.close()
        }
    }

    fun addTaskDataToDatabase(connection: Connection, taskData: TaskCoordinateData, taskId: String) {
        println(taskId)
        // Insert data into the Task table
        val taskStmt = connection.prepareStatement("INSERT INTO Task (task_id, version) VALUES (?, ?)")
        taskStmt.setString(1, taskId)
        taskStmt.setInt(2, 1) // Use the appropriate schema version number
        taskStmt.executeUpdate()

        // Insert data into TrainExample and TrainMatrix tables
        for ((index, example) in taskData.train.withIndex()) {
            val exampleId = index + 1

            val trainExampleStmt = connection.prepareStatement(
                "INSERT INTO TrainExample (task_id, example_id, input_rows, input_cols, output_rows, output_cols) VALUES (?, ?, ?, ?, ?, ?)"
            )
            trainExampleStmt.setString(1, taskId)
            trainExampleStmt.setInt(2, exampleId)
            trainExampleStmt.setInt(3, example.input.size)
            trainExampleStmt.setInt(4, example.input[0].size)
            trainExampleStmt.setInt(5, example.output.size)
            trainExampleStmt.setInt(6, example.output[0].size)
            trainExampleStmt.executeUpdate()

            insertMatrixData(connection, taskId, exampleId, "input", example.input)
            insertMatrixData(connection, taskId, exampleId, "output", example.output)
        }

        // Insert data into TestExample and TestMatrix tables (similar to training data)
        // ...
    }

    fun insertMatrixData(
        connection: Connection,
        taskId: String,
        exampleId: Int,
        matrixType: String,
        matrix: List<List<Int>>
    ) {
        val stmt = connection.prepareStatement(
            "INSERT INTO TrainMatrix (task_id, example_id, matrix_type, row, col, value) " +
                    "VALUES (?, ?, ?, ?, ?, ?)"
        ) // Or "TestMatrix" if it's test data

        for ((rowIndex, row) in matrix.withIndex()) {
            for ((colIndex, value) in row.withIndex()) {
                stmt.setString(1, taskId)
                stmt.setInt(2, exampleId)
                stmt.setString(3, matrixType)
                stmt.setInt(4, rowIndex)
                stmt.setInt(5, colIndex)
                stmt.setInt(6, value)
                stmt.executeUpdate()
            }
        }
    }


    fun downloadAndParseTaskData(taskUrl: String): TaskCoordinateData {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(taskUrl)
            .build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val jsonString = response.body!!.string()
            return Json.decodeFromString<TaskCoordinateData>(jsonString)
        } else {
            throw IOException("Failed to download task data: ${response.code} - ${response.message}")
        }
    }


}