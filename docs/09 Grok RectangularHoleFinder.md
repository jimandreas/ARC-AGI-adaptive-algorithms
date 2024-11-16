# Grok - Rectangular Hole Finder

# The Prompt
N.B.  Google Gemini didn't do so well with this algorihm.  Now I have asked Grok:

I have a matrix of integers.  A zero indicates background.  Please compose a "pseudo-code" that scans the matrix looking for some region in the matrix that has non-zero values.   Within this region please search for a rectangular enclosed region that is bordered by non-zero values.  Please list the coordinate (row and column) values for this enclosed region.

## The Code (from GROK)
```
function FindEnclosedRectangle(matrix):
# Dimensions of the matrix
rows = length of matrix
cols = length of matrix[0]

    # Function to check if a cell is valid (non-zero)
    isNonZero(cell) = cell != 0

    # Scan for regions
    for i from 0 to rows - 1:
        for j from 0 to cols - 1:
            if isNonZero(matrix[i][j]):
                # Here we assume we've found the start of a potential region
                startRegionRow = i
                startRegionCol = j
                
                # Define the boundaries of the region
                top = startRegionRow
                bottom = startRegionRow
                left = startRegionCol
                right = startRegionCol
                
                # Expand to find the full extent of the region
                while top > 0 and isNonZero(matrix[top-1][j]):
                    top -= 1
                while bottom < rows-1 and isNonZero(matrix[bottom+1][j]):
                    bottom += 1
                while left > 0 and isNonZero(matrix[i][left-1]):
                    left -= 1
                while right < cols-1 and isNonZero(matrix[i][right+1]):
                    right += 1
                
                # Now check for an enclosed rectangle within this region
                for r from top to bottom:
                    for c from left to right:
                        if isNonZero(matrix[r][c]):
                            # Check if this cell could be part of an enclosed rectangle
                            if r < bottom and c < right:
                                innerTop = r
                                innerBottom = r
                                innerLeft = c
                                innerRight = c
                                
                                # Expand to find inner rectangle
                                while innerTop < bottom and isNonZero(matrix[innerTop+1][c]):
                                    innerTop += 1
                                while innerLeft < right and isNonZero(matrix[r][innerLeft+1]):
                                    innerLeft += 1
                                while innerBottom > r and isNonZero(matrix[innerBottom-1][c]):
                                    innerBottom -= 1
                                while innerRight > c and isNonZero(matrix[r][innerRight-1]):
                                    innerRight -= 1
                                
                                # Verify if it's an enclosed rectangle
                                if (innerTop > top or innerBottom < bottom or 
                                    innerLeft > left or innerRight < right):
                                    # This condition means we found an enclosed rectangle
                                    return (innerTop, innerLeft, innerBottom, innerRight)
    return "No enclosed rectangle found"
```

Explanation:

    Region Identification: The algorithm first scans the matrix for any non-zero value, indicating the start of a region. It then expands outwards to find the complete boundary of this region.
    
    Enclosed Rectangle Search: Within this region, it looks for a smaller rectangle where all four sides touch the outer boundary of the region, but the inside is either zero or part of another region.
    
    Coordinate Return: If an enclosed rectangle is found, it returns the coordinates of the top-left and bottom-right corners of this rectangle. If no such rectangle exists, it informs that no enclosed rectangle was found.


This pseudo-code assumes that the matrix contains integers and that zeros are considered as background or empty space. Adjustments might be needed based on specific matrix implementations or if there are more complex rules for what constitutes an "enclosed" region.

## Prompt - revise to Kotlin

Super!   Can you please convert this pseudo-code to the "Kotlin" language?

## GROK reponse
N.B. I asked Grok to code this in kotlin and use matrix: List<List<Int>> as the matrix datastructure.
```
fun findEnclosedRectangle(matrix: List<List<Int>>): String {
    val rows = matrix.size
    val cols = matrix[0].size

    fun isNonZero(cell: Int) = cell != 0

    // Scan for regions
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (isNonZero(matrix[i][j])) {
                var startRegionRow = i
                var startRegionCol = j
                var top = startRegionRow
                var bottom = startRegionRow
                var left = startRegionCol
                var right = startRegionCol
                
                // Expand to find the full extent of the region
                while (top > 0 && isNonZero(matrix[top-1][j])) top--
                while (bottom < rows - 1 && isNonZero(matrix[bottom+1][j])) bottom++
                while (left > 0 && isNonZero(matrix[i][left-1])) left--
                while (right < cols - 1 && isNonZero(matrix[i][right+1])) right++
                
                // Now check for an enclosed rectangle within this region
                for (r in top..bottom) {
                    for (c in left..right) {
                        if (isNonZero(matrix[r][c])) {
                            // Check if this cell could be part of an enclosed rectangle
                            if (r < bottom && c < right) {
                                var innerTop = r
                                var innerBottom = r
                                var innerLeft = c
                                var innerRight = c
                                
                                // Expand to find inner rectangle
                                while (innerTop < bottom && isNonZero(matrix[innerTop+1][c])) innerTop++
                                while (innerLeft < right && isNonZero(matrix[r][innerLeft+1])) innerLeft++
                                while (innerBottom > r && isNonZero(matrix[innerBottom-1][c])) innerBottom--
                                while (innerRight > c && isNonZero(matrix[r][innerRight-1])) innerRight--
                                
                                // Verify if it's an enclosed rectangle
                                if (innerTop > top || innerBottom < bottom || 
                                    innerLeft > left || innerRight < right) {
                                    // This condition means we found an enclosed rectangle
                                    return "Enclosed rectangle found at: ($innerTop, $innerLeft) to ($innerBottom, $innerRight)"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return "No enclosed rectangle found"
}
```

Notes:

List Usage: The function now uses List<List<Int>> for matrix, 
which means you'll access elements like matrix[i][j].
    
Initialization: When creating the matrix, you would do something 
like List(rows) { List(cols) { 0 } }, assuming you're initializing 
with zeros or another default value. However, in the function, we 
treat the matrix as already populated.
    
Performance: Using List might introduce a small overhead due to the 
lack of direct indexing as in Array, but for most practical purposes, 
this difference is negligible. Remember that List in Kotlin is immutable 
by default, but you can use MutableList if you need to modify the matrix after creation.