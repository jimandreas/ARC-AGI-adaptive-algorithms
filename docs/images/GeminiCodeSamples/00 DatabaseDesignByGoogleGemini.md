# Database Design for ARC tasks

## Google Gemini prompt:

I need a database for the Tasks included in the @ARCPrize Kaggle competition.   
As you know, the training examples include 2 to 4 sets of "train" matricies - 
one each for the "input" and "iutput" of each training.   Note that the size of 
each matrix is independent and the matrices are represented by List<List<Int>>.   
There is also a "test" dataset consisting of one or more pairs of matricies also 
labeled "input" and "output" and the matricies are also represented by List<List<Int>>.

Please design a SQLlite schema that can hold the matrix data, 
and also record the row and column sizes for each matrix all keyed 
by the alphanumeric name of the task given.   Please also include a 
version number for the schema in case updates to the schema structure are needed.

## Google Gemini response (9 Nov 2024)

SQL

```SQL
CREATE TABLE Task (
  task_id TEXT PRIMARY KEY,  -- Alphanumeric name of the task (e.g., '00d62c1b')
  version INTEGER NOT NULL   -- Schema version number (e.g., 1)
);

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

CREATE TABLE TrainMatrix (
  task_id TEXT NOT NULL,
  example_id INTEGER NOT NULL,
  matrix_type TEXT NOT NULL CHECK (matrix_type IN ('input', 'output')),  -- 'input' or 'output'
  row INTEGER NOT NULL,
  col INTEGER NOT NULL,
  value INTEGER NOT NULL,
  FOREIGN KEY (task_id, example_id) REFERENCES TrainExample (task_id, example_id)
);

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

CREATE TABLE TestMatrix (
  task_id TEXT NOT NULL,
  example_id INTEGER NOT NULL,
  matrix_type TEXT NOT NULL CHECK (matrix_type IN ('input', 'output')),  -- 'input' or 'output'
  row INTEGER NOT NULL,
  col INTEGER NOT NULL,
  value INTEGER NOT NULL,
  FOREIGN KEY (task_id, example_id) REFERENCES TestExample (task_id, example_id)
);
```

Explanation:

Task table:

task_id: Stores the unique alphanumeric identifier for the task. This is the primary key.
version: Stores the schema version number. This allows you to update the schema in the future while maintaining compatibility with older data.
TrainExample table:
```
task_id: Foreign key referencing the Task table.
example_id: Identifies the specific training example within the task (e.g., 1, 2, 3).
input_rows, input_cols: Store the number of rows and columns in the input matrix.
output_rows, output_cols: Store the number of rows and columns in the output matrix.
PRIMARY KEY (task_id, example_id): Ensures that each training example is unique within a task.
TrainMatrix table:

task_id, example_id: Foreign keys referencing the TrainExample table.
matrix_type: Indicates whether the data is for the 'input' or 'output' matrix.
row, col: Store the row and column index of the cell in the matrix.
value: Stores the integer value of the cell.
```

TestExample and TestMatrix tables:

These tables have the same structure as TrainExample and TrainMatrix, respectively, but store data for the test examples.
Versioning:

The version column in the Task table allows you to make changes to the schema later. For example, 
if you need to add a new column or table, you can increment the version number. This way, your 
code can check the version number and handle different schema versions accordingly.

Example Data:

Let's say you have a task with task_id = '00d62c1b' and two training examples. You would insert 
data into the tables like this:

```SQL
-- Task table
INSERT INTO Task (task_id, version) VALUES ('00d62c1b', 1);

-- TrainExample table
INSERT INTO TrainExample (task_id, example_id, input_rows, input_cols, output_rows, output_cols) 
VALUES ('00d62c1b', 1, 3, 3, 3, 3);

INSERT INTO TrainExample (task_id, example_id, input_rows, input_cols, output_rows, output_cols) 
VALUES ('00d62c1b', 2, 4, 4, 4, 4);

-- TrainMatrix table (example for the first training example, input matrix)
INSERT INTO TrainMatrix (task_id, example_id, matrix_type, row, col, value) 
VALUES ('00d62c1b', 1, 'input', 0, 0, 1);

INSERT INTO TrainMatrix (task_id, example_id, matrix_type, row, col, value) 
VALUES ('00d62c1b', 1, 'input', 0, 1, 2);

-- ... and so on for all cells in all matrices
```

## Prompt: create database and read data via kotlin

Prompt: 

Data for one task is read from the Task file using the following command:

Json.decodeFromString<TaskCoordinateData>(file.readText())

and is read into the following data structure.  Assume the file data is a well formatted JSON entry - 
no need for error detection.

Please create the kotlin code to create the database according to your schema, read in the JSON data 
from one Task file, and then add the data in the Task file to the database.

```kotlin
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import android.database.sqlite.SQLiteDatabase  // Assuming you're using Android's SQLiteDatabase
import android.content.ContentValues

// ... (Your data classes for TaskCoordinateData, etc.) ...

@Serializable
data class TaskCoordinateData(
    val train: List<ExampleData>,
    val test: List<ExampleData>
)

@Serializable
data class ExampleData(
    val input: List<List<Int>>,
    val output: List<List<Int>>
)

fun createDatabaseAndAddTaskData(filePath: String, databasePath: String) {
    val database = SQLiteDatabase.openOrCreateDatabase(databasePath, null)

    // Create the tables (use the SQL schema from the previous response)
    database.execSQL("""
        CREATE TABLE IF NOT EXISTS Task (
          task_id TEXT PRIMARY KEY,
          version INTEGER NOT NULL
        );
        -- ... (rest of the SQL schema for TrainExample, TrainMatrix, etc.) ...
    """)

    // Read the JSON data from the file
    val file = File(filePath)
    val taskData = Json.decodeFromString<TaskCoordinateData>(file.readText())

    // Get the task ID from the file name (assuming the file name is the task ID)
    val taskId = file.nameWithoutExtension

    // Insert data into the Task table
    val taskValues = ContentValues().apply {
        put("task_id", taskId)
        put("version", 1) // Use the appropriate schema version number
    }
    database.insert("Task", null, taskValues)

    // Insert data into TrainExample and TrainMatrix tables
    for ((index, example) in taskData.train.withIndex()) {
        val exampleId = index + 1

        val trainExampleValues = ContentValues().apply {
            put("task_id", taskId)
            put("example_id", exampleId)
            put("input_rows", example.input.size)
            put("input_cols", example.input[0].size)
            put("output_rows", example.output.size)
            put("output_cols", example.output[0].size)
        }
        database.insert("TrainExample", null, trainExampleValues)

        insertMatrixData(database, taskId, exampleId, "input", example.input)
        insertMatrixData(database, taskId, exampleId, "output", example.output)
    }

    // Insert data into TestExample and TestMatrix tables (similar to training data)
    // ...

    database.close()
}

fun insertMatrixData(database: SQLiteDatabase, taskId: String, exampleId: Int, matrixType: String, matrix: List<List<Int>>) {
    for ((rowIndex, row) in matrix.withIndex()) {
        for ((colIndex, value) in row.withIndex()) {
            val matrixValues = ContentValues().apply {
                put("task_id", taskId)
                put("example_id", exampleId)
                put("matrix_type", matrixType)
                put("row", rowIndex)
                put("col", colIndex)
                put("value", value)
            }
            database.insert("TrainMatrix", null, matrixValues) // Or "TestMatrix" if it's test data
        }
    }
}

fun main() {
    val filePath = "path/to/your/task/file.json" // Replace with the actual file path
    val databasePath = "path/to/your/database.db" // Replace with the desired database path
    createDatabaseAndAddTaskData(filePath, databasePath)
}
```

Explanation:

createDatabaseAndAddTaskData function:

1. Opens or creates the SQLite database.
2. Creates the necessary tables using the SQL schema.
3. Reads the JSON data from the file and extracts the task ID.
4.  Inserts the task information into the Task table.
5.  Iterates through the training examples and inserts data into TrainExample and TrainMatrix tables using the insertMatrixData helper function.
(You'll need to add similar logic for inserting test data into TestExample and TestMatrix.)
6. Closes the database connection.

insertMatrixData function:

Takes the database, task ID, example ID, matrix type ('input' or 'output'), and the matrix data as input.
Iterates through the matrix and inserts each cell's value into the appropriate table (TrainMatrix or TestMatrix).
Remember to:

Replace "path/to/your/task/file.json" and "path/to/your/database.db" with the actual file and database paths.
Include the necessary kotlinx.serialization library in your project.
Add the required permissions for accessing storage if you're working with external storage on Android.
Adapt the code to handle potential errors and exceptions in a real-world application.

## Building a small Kotlin program to create the database

### Gemini prompt:

Please create a "build.gradle.kts" file (kotlin format) for Intellij IDEA 
for a small program that uses SQLlite running on a Windows 10 system.   
Thus the data will be read locally on the windows system and the 
database file will be created as part of an Intellij IDEA kotlin project.

### Google Gemini response:

```
plugins {
    kotlin("jvm") version "1.8.22" // Use the latest Kotlin version
    application
}

group = "your.group.id" // Replace with your group ID
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // SQLite JDBC driver
    implementation("org.xerial:sqlite-jdbc:3.40.1.0") // Use the latest version
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17) // Use Java 17 or higher
}

application {
    mainClass.set("your.main.class") // Replace with your main class
}
```

Explanation:

Plugins:

kotlin("jvm"): Applies the Kotlin JVM plugin for compiling Kotlin code.
application: Applies the application plugin to create an executable JAR.
Group and Version:

Replace "your.group.id" and "1.0-SNAPSHOT" with your project's group ID and version.
Repositories:

mavenCentral(): Specifies Maven Central as the repository to download dependencies from.
Dependencies:

testImplementation(kotlin("test")): Adds the Kotlin test library for writing unit tests.
implementation("org.xerial:sqlite-jdbc:3.40.1.0"): Adds the SQLite JDBC driver dependency to interact with the SQLite database.
Tasks:

useJUnitPlatform(): Configures the test task to use JUnit Platform for running tests.
Kotlin JVM Toolchain:

jvmToolchain(17): Specifies that the project should use Java 17 or higher.
Application:

mainClass.set("your.main.class"): Sets the main class for your application. Replace "your.main.class" with the fully qualified name of your main class (e.g., "com.example.myapp.MainApp").
To use this build.gradle.kts file:

Create a new Kotlin project in IntelliJ IDEA.
Replace the contents of the build.gradle.kts file with the code above.
Make sure you have the correct main class specified.
Refresh the Gradle project (You can usually do this by clicking the 
"Reload All Gradle Projects" button in the Gradle tool window).
This will set up your project to use Kotlin, download the necessary 
SQLite dependency, and allow you to build and run your application that 
interacts with an SQLite database.

### Prompt: Browsing the SQLlite file after creation

What chrome or firefox browser plugins are appropriate 
for opening a SQLlite database file for browsing?

### Gemini response

You're looking for tools to easily inspect and browse your SQLite databases right within your browser! Here are some popular Chrome and Firefox plugins that can help:

For both Chrome and Firefox:

DB Browser for SQLite (Highly Recommended):

This is a powerful and versatile open-source tool with a user-friendly interface.
It allows you to create, design, edit, and browse SQLite database files.
You can execute SQL queries, import/export data, and even plot graphs.

SQLite Viewer:

A simpler option for quickly viewing SQLite databases.
It allows you to open multiple databases, browse tables, and execute basic SQL queries.
<br>Chrome Web Store: https://chromewebstore.google.com/detail/sqlite-viewer/golagekponhmgfoofmlepfobdmhpajia
<br>Firefox Add-ons: https://addons.mozilla.org/en-US/firefox/addon/sqlite-viewer/

Important Considerations:

Security: Be cautious when using browser extensions to access sensitive data. 
Make sure you trust the extension and understand its permissions.

Performance: For very large databases, browser-based tools might not 
be as performant as dedicated desktop applications.

Features: Consider the specific features you need (e.g., SQL query editor, 
data import/export, visualization).

If you find that browser extensions are not sufficient for your needs, 
you might want to explore dedicated SQLite database browsers like DB Browser 
for SQLite (standalone version) or SQLiteStudio. These offer more advanced 
features and better performance for managing larger databases.



