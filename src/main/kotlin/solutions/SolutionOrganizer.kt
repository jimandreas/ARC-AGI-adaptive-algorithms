@file:Suppress(
    "RedundantSuppression", "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions

import experiments.ExperimentalDatasets
import solutions.transformations.TransformationBidi

class SolutionOrganizer(val ed: ExperimentalDatasets) {

    fun trySolutions() {

        // solutions where the matrix is mirrored somehow
        val mms = MirrorMatrixSolution()
        mms.setExperimentalDatasets(ed)  // this makes it easier for unit tests
        mms.surveyTasksForMirroringSolutions()

        // solutions where cells are translated
        val cellTranslations = CellTranslationsAnalysis()
        cellTranslations.setExperimentalDatasets(ed)
        cellTranslations.surveyTranslations()

        val tbidi = TransformationBidi()
        tbidi.scanBidiTransformations()

    }


}