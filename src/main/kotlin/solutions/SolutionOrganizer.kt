@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty",
    "SameParameterValue", "UnnecessaryVariable", "LocalVariableName", "PropertyName"
)

package solutions

import experiments.ExperimentalDatasets

class SolutionOrganizer(val ed: ExperimentalDatasets) {

    fun trySolutions() {
        val mms = MirrorMatrixSolution(ed)
        mms.surveyTasksForMirroringSolutions()
    }


}