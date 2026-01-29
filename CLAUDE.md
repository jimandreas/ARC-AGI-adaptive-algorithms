# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin-based algorithmic solver for the ARC-AGI (Abstraction and Reasoning Corpus) challenge. Uses pure algorithmic approaches (no ML/neural networks) with 70+ specialized transformation strategies to solve abstract reasoning puzzles. Currently solves 88/400 training tasks (22%) and 2/100 evaluation tasks.

## Build & Test Commands

```bash
./gradlew build        # Build the project
./gradlew run          # Run main entry point (Main.kt)
./gradlew test         # Run all tests (JUnit 5)
```

Requires JVM toolchain targeting Java 20. Uses Kotlin 2.0.20.

## Architecture

### Data Pipeline

1. **Load** — `readTaskData()` deserializes ARC JSON tasks (400 training + 100 evaluation) via KotlinX Serialization
2. **Analyze** — `AnalyzeTasks.analyzeTrainingData()` converts raw matrices into abstractions: connected `Block`s (flood-fill), `Point`s, `BlockInfo` (rectangular/hollow properties), `BoundingBox`
3. **Categorize** — `ExperimentalDatasets` partitions tasks by matrix size relationship (same/bigger/smaller)
4. **Solve** — `TransformationBidi.scanBidiTransformations()` sequentially tries all registered strategies
5. **Visualize** — `GraphicsDisplayMatrix` provides Swing GUI for debugging

### Key Abstractions (DataAbstractions.kt)

- `MatrixAbstractions` — wraps a matrix with its derived blocks, points, and block info
- `AbstractionsInInputAndOutput` — pairs input/output abstractions for a training example
- `TaskAbstractions` — full task with all training examples and test matrices analyzed

### Transformation Strategy Pattern

All solvers extend `BidirectionalBaseClass` (in `solutions/transformations/`) and implement `testTransform()` + `returnTestOutput()`. Strategies are registered in `TransformationBidi.kt` and organized by output size:

- `samesize/` — input and output dimensions match (mirroring, color mapping, translations)
- `smaller/` — output smaller than input (extraction, selection, quantization). Named `S##Description`
- `bigger/` — output larger than input (extension with mirroring)
- `partition/` — partition-based analysis

### Global State

Results accumulate in global mutable lists: `solvedTasks`, `wrongAnswerTasks`, `unSolvedTasks`. Task data lives in `tAllTaskData` and `taskAbstractionsList`.

### Utilities

- `BlockUtilities` — flood-fill connected component detection (with optional diagonal scanning)
- `MatrixUtilities` — mirror, rotate, submatrix extraction
- `PointUtilities` — coordinate operations
- `TranslationUtilities` — spatial translation operations
- `Spiral` — spiral iteration patterns

## Adding a New Solver

1. Create a class extending `BidirectionalBaseClass` in the appropriate subdirectory (`samesize/`, `smaller/`, `bigger/`)
2. Implement `testTransform()` to validate against training examples and `returnTestOutput()` to produce test output
3. Register it in `TransformationBidi.kt`

## Task Data

Training tasks are listed by ID in `TaskNames.kt` with paths pointing to `src/main/resources/ARC/data/training/`. Evaluation tasks are in `src/main/resources/ARC/data/evaluation/`.
