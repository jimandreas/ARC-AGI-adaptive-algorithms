package entities

class BlockCompletion {

    fun completeRectangularBlock(blockCoordinates: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        if (blockCoordinates.isEmpty()) return emptySet() // Nothing to complete

        val minRow = blockCoordinates.minOf { it.first }
        val maxRow = blockCoordinates.maxOf { it.first }
        val minCol = blockCoordinates.minOf { it.second }
        val maxCol = blockCoordinates.maxOf { it.second }

        val missingCoordinates = mutableSetOf<Pair<Int, Int>>()

        for (row in minRow..maxRow) {
            for (col in minCol..maxCol) {
                if (Pair(row, col) !in blockCoordinates) {
                    missingCoordinates.add(Pair(row, col))
                }
            }
        }

        return missingCoordinates
    }
}