package sumif.utils

/**
 * Represents a 2D zero-based sparse matrix that can be accessed both by coordinates and ordinal index;
 * The ordinal index works by rows and then by columns e.g. a matrix 3 x 3 will have indices:
 *   0 1 2
 * -------
 * 0|0 3 6
 * 1|1 4 7
 * 2|2 5 8
 *
 * @param T the type of elements
 * @property tZero the default value for an empty cell (e.g. 0.0 for Double)
 * @constructor creates a sparse matrix with the last element filled with [tZero]
 *
 * @param numRows number of rows of the matrix
 * @param numCols number of columns of the matrix
 */
class SparseMatrix<T>(
    numRows: Int = 1,
    numCols: Int = 1,
    val tZero: T? = null,
) {
    val mainMatrix: MutableMap<Pair<Int, Int>, T?> by lazy {
        mutableMapOf((numRows - 1 to numCols - 1) to tZero)
    }

    private fun indexToCoord(idx: Int): Pair<Int, Int> = idx % getNumColumns() to idx / getNumRows()

    private fun coordToIdx(coord: Pair<Int, Int>): Int = coord.first + getNumColumns() * coord.second

    private fun isOutsideBounds(coord: Pair<Int, Int>): Boolean =
        coord.first > getNumRows() || coord.second > getNumColumns()

    /**
     * Sets the specified element to the specified value. If the element is outside the current bounds, the matrix is
     * extended accordingly
     *
     * @param coord coordinates
     * @param value value to fill with
     */
    operator fun set(coord: Pair<Int, Int>, value: T?) = mainMatrix.set(coord, value)

    /**
     * Sets the specified element to the specified value. If the element is outside the current bounds, the matrix is
     * extended accordingly
     *
     * @param x
     * @param y
     * @param value
     */
    operator fun set(x: Int, y: Int, value: T?) = set(x to y, value)

    /**
     * Sets the specified element to the specified value. If the element is outside the current bounds, the matrix is
     * extended accordingly
     *
     * @param idx
     * @param value
     */
    operator fun set(idx: Int, value: T?) = set(indexToCoord(idx), value)

    /**
     * Get the specified element to the specified value. If the element is outside the current bounds, the matrix is
     * extended accordingly with a [tZero] value
     *
     * @param coord
     * @return
     */
    operator fun get(coord: Pair<Int, Int>): T? = mainMatrix.getOrElse(coord) { tZero }
        .also { // If it is outside the matrix bounds an element is added
            if (isOutsideBounds(coord)) {
                set(coord, tZero)
            }
        }

    /**
     * Get the specified element to the specified value. If the element is outside the current bounds, the matrix is
     * extended accordingly with a [tZero] value
     *
     * @param x
     * @param y
     * @return
     */
    operator fun get(x: Int, y: Int): T? = get(x to y)

    /**
     * Get the specified element to the specified value. If the element is outside the current bounds, the matrix is
     * extended accordingly with a [tZero] value
     *
     * @param idx
     * @return
     */
    operator fun get(idx: Int): T? = get(indexToCoord(idx))

    fun getNumColumns() = (mainMatrix.keys.maxOfOrNull { it.second } ?: 0) + 1
    fun getNumRows() = (mainMatrix.keys.maxOfOrNull { it.first } ?: 0) + 1

    override fun toString(): String = "Sparse Matrix (${getNumRows()} rows and ${getNumColumns()} columns)".plus(
        " with elements (default ${tZero}):\n".plus(mainMatrix.toSortedMap(
            compareBy { coordToIdx(it) }  // Sorts by ordinal index
        ).map { "${it.key} = ${it.value}\n" })
    ).replace("[", "")
        .replace("]", "")
        .replace("\n, ", "\n")
        .trim()
}