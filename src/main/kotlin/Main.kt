import sumif.utils.SparseMatrix

fun main(args: Array<String>) {
    val mySparseMatrix = SparseMatrix(
        numRows = 5, numCols = 3, tZero = 0.0
    )
    println(mySparseMatrix)

    mySparseMatrix[0, 2] = 1.0
    println(mySparseMatrix)

    mySparseMatrix[4, 4] = 2.0  // Notice: it adds two additional columns!
    println(mySparseMatrix)

    mySparseMatrix[14] = 3.0 // We change the 14th (i.e. [4, 2]) element to 3 by the index notation
    println(mySparseMatrix)

    println("Element (6,6): ${mySparseMatrix[6, 6]}")    // Notice: the matrix is expanded with a [tZero] value to accommodate for the accessed element
    println(mySparseMatrix)
}